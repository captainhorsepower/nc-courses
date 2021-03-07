package com.netcracker.edu.varabey.catalog.data.dao;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;
import com.netcracker.edu.varabey.catalog.data.entity.Tag;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.OfferException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public Offer save(Offer offer) {

        /* use merge() instead of persist(), because offer
         * might share tags and categories with other methods,
         * so persist would throw exceptions because of detached
         * category/tags. */
        return em.merge(offer);
    }

    @Override
    public Offer findById(Long id) {
        return em.find(Offer.class, id);
    }

    @Override
    public List<Offer> findAll() {
        return em.createNamedQuery("Offer.findAll", Offer.class).getResultList();
    }

    @Override
    public Offer update(Offer offer) {
        return em.merge(offer);
    }

    @Override
    public void deleteById(Long id) {
        try {
            Offer offer = em.getReference(Offer.class, id);
            em.remove(offer);
        } catch (EmptyResultDataAccessException e) {
            throw new OfferException("Offer with id=" + id + " was not found. Unable to delete.");
        }
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
