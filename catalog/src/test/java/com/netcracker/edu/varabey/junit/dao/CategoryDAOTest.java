//package com.netcracker.edu.varabey.junit.dao;
//
//import Category;
//import Offer;
//import Price;
//import Tag;
//import com.netcracker.edu.varabey.service.CatalogService;
//import CategoryService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.spring.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CategoryDAOTest {
//
//    @Autowired
//    private CategoryService service;
//
//    @Test
//    public void testCreateAndReadOneCategoryWithoutOffer() {
//        Category category = new Category();
//
//        category.setName("testCreateAndReadOneCategoryWithoutOffer cat1");
//
//        category = service.save(category);
//
//        Category createdCategory = service.findById(category.getId());
//
//        assertNotNull(createdCategory);
//        assertEquals(category.toString(), createdCategory.toString());
//    }
//
//    @Test
//    public void testUpdateCategory() {
//        Category category = new Category();
//
//        category.setName("testUpdateCategory cat1");
//
//        category = service.save(category);
//
//        category.setName("testUpdateCategory new cat1");
//
//        service.update(category);
//
//        Category category1 = service.findById(category.getId());
//
//        assertNotNull(category1);
//        assertEquals(category.toString(), category1.toString());
//    }
//
//    @Test
//    public void testDeleteCategory() {
//        Category category = new Category();
//
//        category.setName("testDeleteCategory cat1");
//
//        category = service.save(category);
//
//        service.delete(category.getId());
//
//        Category category1 = service.findById(category.getId());
//        assertNull(category1);
//    }
//
//    @Test
//    public void deleteCategoryCascadeDeletesOffersTest() {
//        List<Offer> offers = service.findAll();
//
//        Offer offer = new Offer();
//
//        Category category = new Category();
//        category.setName("deleteCategoryCascadeDeletesOffersTest cat1");
//
//        offer.setName("of1");
//        offer.setCategory(category);
//        offer.setPrice(new Price(100D));
//        offer.getTags().add(new Tag("tagg1"));
//        offer.getTags().add(new Tag("tagg2"));
//
//        offer = service.create(offer);
//        category = offer.getCategory();
//
//        offer = new Offer();
//        offer.setName("of2");
//        offer.setCategory(category);
//        offer.setPrice(new Price(1000D));
//        offer.getTags().add(new Tag("tagg3"));
//
//        offer = service.create(offer);
//
//        service.delete(category.getId());
//        assertNull(service.findById(category.getId()));
//        assertNull(service.findById(offer.getId()));
//
//        assertEquals(offers.toString(), service.findAll().toString());
//    }
//}
