//package com.netcracker.edu.varabey.junit.dao;
//
//import com.netcracker.edu.varabey.entity.Category;
//import com.netcracker.edu.varabey.entity.Offer;
//import com.netcracker.edu.varabey.entity.Price;
//import com.netcracker.edu.varabey.entity.Tag;
//import com.netcracker.edu.varabey.service.CatalogService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TagDaoTests {
//
//    @Autowired
//    private CatalogService service;
//
//    @Test
//    public void testCreateAndReadOneTag() {
//        Tag tag = new Tag();
//        tag.setName("testCreateAndReadOneTag tag1");
//        tag = service.create(tag);
//
//        Tag tag1 = service.findById(tag.getId());
//        assertNotNull(tag1);
//        assertEquals(tag.toString(), tag1.toString());
//    }
//
//    @Test
//    public void testUpdateTag() {
//        Tag tag = new Tag();
//        tag.setName("testUpdateTag tag1");
//        tag = service.create(tag);
//
//        tag.setName("testUpdateTag new tag1");
//
//        tag = service.update(tag);
//
//        Tag tag1 = service.findById(tag.getId());
//        assertNotNull(tag1);
//        assertEquals(tag.toString(), tag1.toString());
//    }
//
//    @Test
//    public void updateTagNameUpdatesTagNameInOffer() {
//        Tag tag = new Tag();
//        tag.setName("updateTagNameUpdatesTagNameInOffer tag1");
//        tag = service.create(tag);
//
//
//        Offer offer = new Offer();
//        offer.setName("of1");
//        offer.setPrice(new Price(100.));
//        offer.setCategory(new Category("updateTagNameUpdatesTagNameInOffer c1"));
//        offer.addTag(tag);
//
//        offer = service.create(offer);
//
//        tag.setName("updateTagNameUpdatesTagNameInOffer new tag1");
//        tag = service.update(tag);
//
//        offer = service.findById(offer.getId());
//        assertNotNull(offer);
//        assertEquals(service.findById(tag.getId()).getName(), offer.getTags().iterator().next().getName());
//    }
//
//    @Test
//    public void testDeleteTag() {
//        Tag tag = new Tag();
//        tag.setName("testDeleteTag tag1");
//
//        tag = service.create(tag);
//
//        service.deleteTag(tag.getId());
//
//        Tag tag1 = service.findById(tag.getId());
//
//        assertNull(tag1);
//    }
//
//    @Test
//    public void deleteTagDeletesReferenceInOffers() {
//
//        Offer offer = new Offer();
//        offer.setName("of1");
//        offer.setCategory(new Category("deleteTagDeletesReferenceInOffers cat1"));
//        offer.setPrice(new Price(10D));
//
//        Tag tag = new Tag("deleteTagDeletesReferenceInOffers tag1");
//        tag = service.create(tag);
//        offer.addTag(tag);
//
//        tag = new Tag("deleteTagDeletesReferenceInOffers tag2");
//        tag = service.create(tag);
//        offer.addTag(tag);
//
//        offer = service.create(offer);
//
//        offer.removeTag(tag);
//
//
//        /* deleteById tag2 */
//        service.deleteTag(tag.getId());
//
//        Tag tag1 = service.findById(tag.getId());
//        assertNull(tag1);
//
//        Offer readOffer = service.findById(offer.getId());
//
//        assertNotNull(readOffer);
//        assertEquals(offer.getTags().size(), readOffer.getTags().size());
//    }
//}
