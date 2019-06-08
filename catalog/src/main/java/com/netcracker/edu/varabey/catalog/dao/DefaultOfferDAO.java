package com.netcracker.edu.varabey.catalog.dao;

import com.netcracker.edu.varabey.catalog.entity.Category;
import com.netcracker.edu.varabey.catalog.entity.Offer;
import com.netcracker.edu.varabey.catalog.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data-access-object layer for offers.
 */
@Repository
public class DefaultOfferDAO implements OfferDAO {
    @PersistenceContext
    private EntityManager em;

    /**
     * Create an offer in the database.
     * If tags and category inside offer, have set Ids, it might introduce unexpected
     * side effects like tag.name update, etc. Be careful.
     *
     * @param offer NEW (TRANSIENT) offer entity. Offer.id should be null.
     * @return created offer.
     */
    @Override
    public Offer save(Offer offer) {

        /* use merge() instead of persist(), because offer
         * might share tags and categories with other methods,
         * so persist would throw exceptions because of detached
         * category/tags. */
        return em.merge(offer);
    }

    /**
     * Retrieve offer from the database.
     * @param id unique offer id
     * @return offer with given id
     */
    @Override
    public Offer findById(Long id) {
        return em.find(Offer.class, id);
    }

    /**
     * Retrieve all offers from the catalog database.
     * @return List of all offers in the catalog.
     */
    @Override
    public List<Offer> findAll() {
        return em.createNamedQuery("Offer.findAll", Offer.class).getResultList();
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
        return em.merge(offer);
    }

    /**
     * Completely removes offer from the Catalog database.
     * @param id unique id of target offer.
     */
    @Override
    public void deleteById(Long id) {
        Offer offer = em.getReference(Offer.class, id);
        em.remove(offer);
    }

    /**
     * Finds all offers having given category
     * @param category query param
     * @return list of all offers with given category.
     */
    @Override
    public List<Offer> findAllByCategory(Category category) {
        return em.createNamedQuery("Offer.findAllByCategory", Offer.class)
                .setParameter("categoryName", category.getName())
                .getResultList();
    }

    /**
     * Finds all offers having all tags.
     * @param tags query list param.
     * @return list of offers having all given tags.
     */
    @Override
    public List<Offer> findAllWithTags(Collection<Tag> tags) {
        return em.createNamedQuery("Offer.findAllHavingTags", Offer.class)
                .setParameter("tagNameList", tags.stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList()))
                .setParameter("tagCount", (long) tags.size())
                .getResultList();
    }

    /**
     * Finds all offers with price in range.
     * @param lowerBound inclusive
     * @param upperBound inclusive
     * @return list of all offers with price within given range.
     */
    @Override
    public List<Offer> findAllWithPriceInRange(Double lowerBound, Double upperBound) {
        return em.createNamedQuery("Offer.findAllWithPriceInRange", Offer.class)
                .setParameter("lowerBound", lowerBound)
                .setParameter("upperBound", upperBound)
                .getResultList();
    }
}
