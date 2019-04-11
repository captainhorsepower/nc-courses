package com.netcracker.edu.varabey.junit.dao;

import com.netcracker.edu.varabey.dao.CategoryDao;
import com.netcracker.edu.varabey.dao.CategoryDaoImpl;
import com.netcracker.edu.varabey.dao.OfferDao;
import com.netcracker.edu.varabey.dao.OfferDaoImpl;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryDaoTest {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private OfferDao offerDao = new OfferDaoImpl();

    @Test
    public void testCreateAndReadOneCategoryWithoutOffer() {
        Category category = new Category();

        category.setName("testCreateAndReadOneCategoryWithoutOffer cat1");

        category = categoryDao.create(category);

        Category createdCategory = categoryDao.read(category.getId());

        assertNotNull(createdCategory);
        assertEquals(category.toString(), createdCategory.toString());
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category();

        category.setName("testUpdateCategory cat1");

        category = categoryDao.create(category);

        category.setName("testUpdateCategory new cat1");

        categoryDao.update(category);

        Category category1 = categoryDao.read(category.getId());

        assertNotNull(category1);
        assertEquals(category.toString(), category1.toString());
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category();

        category.setName("testDeleteCategory cat1");

        category = categoryDao.create(category);

        categoryDao.delete(category.getId());

        Category category1 = categoryDao.read(category.getId());
        assertNull(category1);
    }

    @Test
    public void deleteCategoryCascadeDeletesOffersTest() {
        List<Offer> offers = offerDao.readAll();

        Offer offer = new Offer();

        Category category = new Category();
        category.setName("deleteCategoryCascadeDeletesOffersTest cat1");

        offer.setName("of1");
        offer.setCategory(category);
        offer.setPrice(new Price(100D));
        offer.getTags().add(new Tag("tagg1"));
        offer.getTags().add(new Tag("tagg2"));

        offer = offerDao.create(offer);
        category = offer.getCategory();

        offer = new Offer();
        offer.setName("of2");
        offer.setCategory(category);
        offer.setPrice(new Price(1000D));
        offer.getTags().add(new Tag("tagg3"));

        offer = offerDao.create(offer);

        categoryDao.delete(category.getId());
        assertNull(categoryDao.read(category.getId()));

        assertEquals(offers.toString(), offerDao.readAll().toString());
    }
}
