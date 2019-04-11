package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class OfferDaoImpl implements OfferDao {
    private EntityManager entityManager = PostgreSQLDatabaseManager.getInstance().getEntityManager();

    /**
     * Creates offer into the database.
     * Of tags and category, tha are not new should not have eny updates
     * as it may produce some unwanted bugs.
     *
     * @param offer NEW (TRANSIENT) offer entity. id should not be set.
     *              Yes, it will work with set Id, but for such cases you should
     *              opt for update() method.
     * @return detached from the persistent context offer,
     *      so that you can freely modify it.
     * @throws org.hibernate.PersistentObjectException if offer was in detached state
     */
    @Override
    public Offer create(Offer offer) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        /* if entity exists, I manually rollback transaction. Otherwise it breaks the dao */
        try {
            /* use merge instead of persist for the following reason :
             * I detach everything when I leave DAO methods, so any
             * category or tag with set Id passed into this method will be
             * detached, which causes exception on .persist.
             */
            offer = entityManager.merge(offer);
        } catch (Exception e) {
            tx.rollback();
            entityManager.detach(offer);
            /* just passing exception through, I only wanted to rollback in case of emergency */
            throw e;
        }

        tx.commit();

        /* I use Application scoped EntityManager (a.k.a. Persistent context)
         * so by default, offer remains 'managed' even after commit.
         * But I don't want to care about changes made to it by any other methods,
         * until update() is called. Therefore I manually detach it. */
        entityManager.detach(offer);
        return offer;
    }

    @Override
    public Offer read(Long id) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Offer offer = entityManager.find(Offer.class, id);

        /* fetch offer with tags */
        if (offer != null) {
            entityManager.refresh(offer);
        }
        tx.commit();

        entityManager.detach(offer);
        return offer;
    }

    @Override
    public List<Offer> readAll() {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        List<Offer> offers = entityManager
                .createQuery("SELECT offer From Offer offer", Offer.class)
                .getResultList();

        /* fetch read offers with tags */
        offers.forEach( o -> { if(o!=null) entityManager.refresh(o); });
        tx.commit();

        offers.forEach( o -> { if(o!=null) entityManager.detach(o); });
        return offers;
    }

    /**
     * Merge the state of the given offer (managed or detached) into the database.
     * Throws IllegalArgumentException if you try to create new Offer via update.
     * Allows you to update:
     *  -price (value only)
     *  -remove tags
     *  -add tags (you can create new tag,
     *          but it's id should be null when you pass it to this method)
     *  -change category (you can create new one, -||-)
     *
     * You should NOT change names of existing tags nor categories.
     * All properties of updated entity are expected to be valid at this point.
     *
     * @param offer  offer instance with updated fields
     * @return detached updated offer
     * @throws IllegalArgumentException if instance's id isn't present in database
     */
    @Override
    public Offer update(Offer offer) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        /* check, that offer is stored in database.
         * If not, abort update */
        Query q = entityManager.createQuery("SELECT count(o.id) FROM Offer o where o.id = ?1");
        q.setParameter(1, offer.getId());
        long persistedOfferCount = (Long) q.getSingleResult();
        if (persistedOfferCount == 0) {
            tx.rollback();
            throw new IllegalArgumentException("unable to update offer, that is not stored in database");
        }

        /* what's the problem? <code>entityManager.merge(offer)</code>
         * does persist all new tags and category in database correctly.
         * But I, for some reason, can't retrieve ids of newly created objects
         * in offer object.
         *
         * For the fact that id in reference of the mapped entity in my DAO design
         * should always be valid, such behaviour is unacceptable.
         *
         * But!, if I create them manually, using persist (just like in create method)
         * everything works correctly. So, I'm totally fine with persisting manually.
         */
        offer.getTags().stream()
                .filter(t -> t.getId() == null)
                .forEach(t -> entityManager.persist(t));

        if (offer.getCategory().getId() == null) {
            entityManager.persist(offer.getCategory());
        }

        offer = entityManager.merge(offer);

        tx.commit();

        entityManager.detach(offer);
        return offer;
    }

    @Override
    public void delete(Long id) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Offer offer = entityManager.getReference(Offer.class, id);
        entityManager.remove(offer);
        tx.commit();
    }
}
