package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for offer. Operates with transaction-scoped entity manager.
 */
public class OfferDaoImpl implements OfferDao {
    private EntityManagerFactory emf = PostgreSQLDatabaseEntityManagerFactory.getInstance();

    /**
     * Creates offer into the database.
     * tags and category inside offer, should be NEW entities (no set id)
     * or they should NOT introduce any updates, as they will be merged into database
     *
     * @param offer NEW (TRANSIENT) offer entity. id should not be set.
     *              Yes, it will work with set Id, but for such cases you should
     *              opt for update() instead.
     * @return offer with everything set.
     */
    @Override
    public Offer create(Offer offer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* use merge() instead of persist(), because offer
         * might share tags and categories with other methods,
         * so persist would throw exception because of detached
         * category/tag.
         */
        offer = em.merge(offer);
        em.getTransaction().commit();
        em.close();
        return offer;
    }

    /**
     * retrieve offer from the underlying database.
     * @param id of an offer
     * @return read from the database offer
     */
    @Override
    public Offer read(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Offer offer = em.find(Offer.class, id);
        em.getTransaction().commit();
        em.close();
        return offer;
    }

    /**
     * retrieve all offers from the catalog.
     * @return List of offers in the catalog.
     */
    @Override
    public List<Offer> readAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Offer> offers = em
                .createQuery("SELECT offer From Offer offer", Offer.class)
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return offers;
    }

    /**
     * Merge the state of the given offer into the catalog database.
     * Throws IllegalArgumentException if you try to create new Offer via update.
     * Allows you to update:
     *  -price (value only)
     *  -remove tags
     *  -add tags (you can create new tag,
     *          but then it's id should be null when you pass it to this method)
     *  -change category (you can create new one, -||-)
     *
     * You should NOT change names of existing tags nor categories.
     *
     * @param offer  offer instance with updated fields
     * @return updated offer
     * @throws IllegalArgumentException if instance's id isn't present in database
     */
    @Override
    public Offer update(Offer offer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* check, that offer is stored in database.
         * If not, abort update */
        Query q = em.createQuery("SELECT count(o.id) FROM Offer o where o.id = ?1");
        q.setParameter(1, offer.getId());
        long persistedOfferCount = (Long) q.getSingleResult();
        if (persistedOfferCount == 0) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("unable to update offer, that is not stored in database");
        }

        offer = em.merge(offer);
        em.getTransaction().commit();
        em.close();
        return offer;
    }

    /**
     * permanently remove offer from the catalog, category and offers.
     * @param id of an offer to be removed.
     */
    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Offer offer = em.getReference(Offer.class, id);
        em.remove(offer);
        em.getTransaction().commit();
        em.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Offer> findAllByCategory(Category category) {
        List<Offer> offers = new ArrayList<>();
        if (category == null || category.getName() == null) {
            return offers;
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery(
                "SELECT o FROM Offer o "
                + " WHERE o.category.name = :category_name"
        );

        q.setParameter("category_name", category.getName());

        offers = (List<Offer>) q.getResultList();

        em.getTransaction().commit();
        em.close();
        return offers;
    }
}
