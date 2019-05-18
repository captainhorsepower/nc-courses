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
//        Tag tags = new Tag();
//        tags.setName("testCreateAndReadOneTag tag1");
//        tags = service.create(tags);
//
//        Tag tag1 = service.findById(tags.getId());
//        assertNotNull(tag1);
//        assertEquals(tags.toString(), tag1.toString());
//    }
//
//    @Test
//    public void testUpdateTag() {
//        Tag tags = new Tag();
//        tags.setName("testUpdateTag tag1");
//        tags = service.create(tags);
//
//        tags.setName("testUpdateTag new tag1");
//
//        tags = service.update(tags);
//
//        Tag tag1 = service.findById(tags.getId());
//        assertNotNull(tag1);
//        assertEquals(tags.toString(), tag1.toString());
//    }
//
//    @Test
//    public void updateTagNameUpdatesTagNameInOffer() {
//        Tag tags = new Tag();
//        tags.setName("updateTagNameUpdatesTagNameInOffer tag1");
//        tags = service.create(tags);
//
//
//        Offer offer = new Offer();
//        offer.setName("of1");
//        offer.setPrice(new Price(100.));
//        offer.setCategory(new Category("updateTagNameUpdatesTagNameInOffer c1"));
//        offer.addTag(tags);
//
//        offer = service.create(offer);
//
//        tags.setName("updateTagNameUpdatesTagNameInOffer new tag1");
//        tags = service.update(tags);
//
//        offer = service.findById(offer.getId());
//        assertNotNull(offer);
//        assertEquals(service.findById(tags.getId()).getName(), offer.getTags().iterator().next().getName());
//    }
//
//    @Test
//    public void testDeleteTag() {
//        Tag tags = new Tag();
//        tags.setName("testDeleteTag tag1");
//
//        tags = service.create(tags);
//
//        service.deleteTag(tags.getId());
//
//        Tag tag1 = service.findById(tags.getId());
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
//        Tag tags = new Tag("deleteTagDeletesReferenceInOffers tag1");
//        tags = service.create(tags);
//        offer.addTag(tags);
//
//        tags = new Tag("deleteTagDeletesReferenceInOffers tag2");
//        tags = service.create(tags);
//        offer.addTag(tags);
//
//        offer = service.create(offer);
//
//        offer.removeTag(tags);
//
//
//        /* deleteById tag2 */
//        service.deleteTag(tags.getId());
//
//        Tag tag1 = service.findById(tags.getId());
//        assertNull(tag1);
//
//        Offer readOffer = service.findById(offer.getId());
//
//        assertNotNull(readOffer);
//        assertEquals(offer.getTags().size(), readOffer.getTags().size());
//    }
//}
