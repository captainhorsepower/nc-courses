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
        offer = em.merge(offer);
        return offer;
    }

    @Override
    public void deleteById(Long id) {
        Offer offer = em.getReference(Offer.class, id);
        em.remove(offer);
    }

    @Override
    public List<Offer> findAllByCategory(Category category) {
        return em.createNamedQuery("Offer.findAllByCategory", Offer.class)
                .setParameter("categoryName", category.getName())
                .getResultList();
    }

    @Override
    public List<Offer> findAllWithTags(Collection<Tag> tags) {
        return em.createNamedQuery("Offer.findAllHavingTags", Offer.class)
                .setParameter("tagNameList", tags.stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList()))
                .setParameter("tagCount", (long) tags.size())
                .getResultList();
    }

    @Override
    public List<Offer> findAllWithPriceInRange(Double lowerBound, Double upperBound) {
        return em.createNamedQuery("Offer.findAllWithPriceInRange", Offer.class)
                .setParameter("lowerBound", lowerBound)
                .setParameter("upperBound", upperBound)
                .getResultList();
    }
}
