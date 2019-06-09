package com.netcracker.edu.varabey.catalog.data.service;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.data.entity.Offer;

import java.util.List;

public interface OfferService {

    /**
     * Saves an offer.
     * @param offer - must be transient and not null
     * @return saved offer
     */
    Offer create(Offer offer);

    /**
     * Finds an offer by id.
     * @param id - must not be null
     * @return found offer or null
     */
    Offer findById(Long id);

    /**
     * @return list with all existing offers
     */
    List<Offer> findAll();

    Offer addTags(Long id, List<String> tagNames);

    Offer removeTags(Long id, List<String> tagNames);

    Offer changeCategory(Long id, Category category);

    Offer updateNameAndPrice(Offer offer);

    /**
     * Deletes an offer by id
     * @param id - must not be null
     * @throws IllegalArgumentException if id is null
     */
    void delete(Long id);

    /**
     * Finds all offers with given category
     * @param categoryName - must not be null
     * @return all found offers as List
     * @throws IllegalArgumentException if category is null
     */
    List<Offer> findAllOffersByCategory(String categoryName);

    /**
     * Finds all offers, that have all given tags
     * @param tagNames as filter
     * @return found offers
     * @throws IllegalArgumentException if tags is null or empty
     */
    List<Offer> findAllOffersWithTags(List<String> tagNames);

    /**
     * Finds all offers with price in range given range
     * @param left inclusive
     * @param right inclusive
     * @return found offers
     * @throws IllegalArgumentException if left || right are invalid
     */
    List<Offer> findAllWithPriceInRange(Double left, Double right);
}
