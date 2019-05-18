package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * Service class for offer. Operates with transaction-scoped entity manager.
 */
@Repository
public class DefaultOfferDAO implements OfferDAO {
    @PersistenceContext
    private EntityManager em;

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
    public Offer save(Offer offer) {

        /* use merge() instead of persist(), because offer
         * might share tags and categories with other methods,
         * so persist would throw exceptions because of detached
         * category/tags. */
        offer = em.merge(offer);
        return offer;
    }

    /**
     * retrieve offer from the underlying database.
     * @param id of an offer
     * @return found from the database offer
     */
    @Override
    public Offer findById(Long id) {
        return em.find(Offer.class, id);
    }

    /**
     * retrieve all offers from the catalog.
     * @return List of offers in the catalog.
     */
    @Override
    public List<Offer> findAll() {
        return em
                .createQuery("SELECT offer From Offer offer", Offer.class)
                .getResultList();
    }

    /**
     * Merge the state of the given offer into the catalog database.
     * Allows you to update:
     *  -price (value only)
     *  -remove tags
     *  -add tags (you can create new tags,
     *          but then it's id should be null when you pass it to this method)
     *  -change category (you can create new one, -||-)
     *
     * You should NOT change names of existing tags nor categories.
     *
     * @param offer  offer instance with updated fields
     * @return updated offer
     */
    @Override
    public Offer update(Offer offer) {
        offer = em.merge(offer);
        return offer;
    }

    /**
     * permanently remove offer from the catalog, category and offers.
     * @param id of an offer to be removed.
     */
    @Override
    public void deleteById(Long id) {
        Offer offer = em.getReference(Offer.class, id);
        em.remove(offer);
    }

    @Override
    public List<Offer> findAllByCategory(Category category) {
        TypedQuery<Offer> q = em.createQuery(
                "SELECT o FROM Offer o "
                + " WHERE o.category.name = :category_name"
                , Offer.class
        );

        q.setParameter("category_name", category.getName());

        return q.getResultList();
    }

    /**
     * finds all offers that suit given tags-filter.
     * Found offers will contain ALL the tags specified,
     * so the more tags, the less offers
     *
     * tags are not checked in database, they are distinguished by name only.
     * null tags and tags with null names are ignored.
     *
     * @param tags filter
     * @return narrowed down list of offers.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Offer> findAllWithTags(Collection<Tag> tags) {
        StringBuilder qlBuilder = new StringBuilder();
        qlBuilder.append(" SELECT o FROM Offer o ");
        qlBuilder.append(" JOIN Tag t ON o MEMBER OF t.offers ");
        qlBuilder.append(" WHERE t.name IN (");

        long tagCount = tags.stream()
                .filter( t -> t != null && t.getName() != null)
                .map(Tag::getName)
                .peek( name -> qlBuilder.append("'").append(name).append("'").append(", "))
                .count();

        /* remove last ", " */
        qlBuilder.setLength(qlBuilder.length() - 2);
        qlBuilder
                .append(")")
                .append(" GROUP BY o.id HAVING COUNT(DISTINCT t.name) = ")
                .append(tagCount);

        Query q = em.createQuery(
                qlBuilder.toString()
        );

        return (List<Offer>) q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Offer> findAllWithPriceInRange(Double lowerBound, Double upperBound) {
        Query q = em.createQuery(
                "SELECT o FROM Offer o "
                        + " WHERE o.price.value BETWEEN :lower AND :upper"
        );

        q.setParameter("lower", lowerBound);
        q.setParameter("upper", upperBound);

        return (List<Offer>) q.getResultList();
    }
}
