package com.netcracker.edu.varabey.junit.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.junit.service.CatalogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryDAOTest {

    @Autowired
    private CatalogService service;

    @Test
    public void testCreateAndReadOneCategoryWithoutOffer() {
        Category category = new Category();

        category.setName("testCreateAndReadOneCategoryWithoutOffer cat1");

        category = service.createCategory(category);

        Category createdCategory = service.findCategory(category.getId());

        assertNotNull(createdCategory);
        assertEquals(category.toString(), createdCategory.toString());
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category();

        category.setName("testUpdateCategory cat1");

        category = service.createCategory(category);

        category.setName("testUpdateCategory new cat1");

        service.updateCategory(category);

        Category category1 = service.findCategory(category.getId());

        assertNotNull(category1);
        assertEquals(category.toString(), category1.toString());
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category();

        category.setName("testDeleteCategory cat1");

        category = service.createCategory(category);

        service.deleteCategory(category.getId());

        Category category1 = service.findCategory(category.getId());
        assertNull(category1);
    }

    @Test
    public void deleteCategoryCascadeDeletesOffersTest() {
        List<Offer> offers = service.findAllOffers();

        Offer offer = new Offer();

        Category category = new Category();
        category.setName("deleteCategoryCascadeDeletesOffersTest cat1");

        offer.setName("of1");
        offer.setCategory(category);
        offer.setPrice(new Price(100D));
        offer.getTags().add(new Tag("tagg1"));
        offer.getTags().add(new Tag("tagg2"));

        offer = service.createOffer(offer);
        category = offer.getCategory();

        offer = new Offer();
        offer.setName("of2");
        offer.setCategory(category);
        offer.setPrice(new Price(1000D));
        offer.getTags().add(new Tag("tagg3"));

        offer = service.createOffer(offer);

        service.deleteCategory(category.getId());
        assertNull(service.findCategory(category.getId()));
        assertNull(service.findOffer(offer.getId()));

        assertEquals(offers.toString(), service.findAllOffers().toString());
    }
}
