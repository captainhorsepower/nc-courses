package com.netcracker.edu.varabey.junit.dao;

import com.netcracker.edu.varabey.dao.*;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;
import org.junit.Test;

import static org.junit.Assert.*;

public class OfferDaoTest {

    private OfferDao offerDao = new OfferDaoImpl();

    @Test
    public void createAndReadOneOfferTest() {

        Offer offer = new Offer();
        offer.setName("kitty");
        offer.setPrice(new Price(110D));
        offer.setCategory(new Category("createAndReadOneOfferTest category1"));
        offer.getTags().add(new Tag("createAndReadOneOfferTest tag1"));
        offer.getTags().add(new Tag("createAndReadOneOfferTest tag2"));

        offer = offerDao.create(offer);

        Offer readOffer = offerDao.read(offer.getId());

        assertNotNull(readOffer);
        assertEquals(offer.toString(), readOffer.toString());
    }

    @Test
    public void createAndReadTwoOffersTest() {

        Offer offer1 = new Offer();
        Offer offer2 = new Offer();

        offer1.setName("offer1");
        offer1.setPrice(new Price(10D));
        offer1.setCategory(new Category("createAndReadTwoOffersTest category1"));
        offer1.getTags().add(new Tag("createAndReadTwoOffersTest tag1"));

        offer2.setName("offer2");
        offer2.setPrice(new Price(20D));
        offer2.setCategory(new Category("createAndReadTwoOffersTest category2"));
        offer2.getTags().add(new Tag("createAndReadTwoOffersTest tag2"));

        offer1 = offerDao.create(offer1);
        offer2 = offerDao.create(offer2);

        Offer readOffer1 = offerDao.read(offer1.getId());
        Offer readOffer2 = offerDao.read(offer2.getId());

        assertNotNull(readOffer1);
        assertNotNull(readOffer2);
        assertEquals(offer1.toString(), readOffer1.toString());
        assertEquals(offer2.toString(), readOffer2.toString());
    }

    @Test
    public void updateTagSetTest() {
        Offer offer = new Offer();
        offer.setName("tag set update test1");
        offer.setCategory(new Category("updateTagSetTest category"));
        offer.setPrice(new Price(1D));

        Tag t1 = new Tag("updateTagSetTest t1");
        Tag t2 = new Tag("updateTagSetTest t2");
        Tag t3 = new Tag("updateTagSetTest t3");

        offer.getTags().add(t1);
        offer.getTags().add(t2);

        offer = offerDao.create(offer);

        assertEquals(2, offerDao.read(offer.getId()).getTags().size());

        offer.getTags().remove(t1);
        offer.getTags().add(t3);

        offerDao.update(offer);

        assertEquals(2, offerDao.read(offer.getId()).getTags().size());
    }

    @Test
    public void updateCategoryTest() {
        Offer offer = new Offer();
        offer.setPrice(new Price(1D));
        offer.setName("category update test1");
        offer.setCategory(new Category("updateCategoryTest category1"));

        offer = offerDao.create(offer);

        offer.setCategory(new Category("updateCategoryTest category2"));

        offerDao.update(offer);

        assertEquals(offer.getCategory().getName(), offerDao.read(offer.getId()).getCategory().getName());
    }

    @Test
    public void updateDoesntCreateNewOffer() {
        Offer offer = new Offer();
        offer.setName("beforeUpdate");
        offer.setCategory(new Category("updateDoesntCreateNewOffer utils"));
        offer.setPrice(new Price(1D));

        offer = offerDao.create(offer);

        Offer fakeOffer = new Offer();
        fakeOffer.setName("new offer");
        fakeOffer.setCategory(offer.getCategory());
        fakeOffer.setPrice(offer.getPrice());

        try {
            offerDao.update(fakeOffer);
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
            return;
        }

        fakeOffer = new Offer();
        fakeOffer.setName("new offer2");
        fakeOffer.setCategory(offer.getCategory());
        fakeOffer.setPrice(offer.getPrice());
        fakeOffer.setId(Long.MAX_VALUE / 2);

        try {
            offerDao.update(fakeOffer);
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
            return;
        }

        fail();
    }

    @Test
    public void deleteOfferDeletesReferenceInCategoryTest() {
        Offer offer = new Offer();
        offer.setName("offer with category");
        offer.setPrice(new Price(110D));
        offer.setCategory(new Category("deleteOfferDeletesReferenceInCategoryTest deleted items"));

        offer = offerDao.create(offer);

        Category c = offer.getCategory();

        CategoryDao categoryDao = new CategoryDaoImpl();

        /* while offer exists, it should be contained in category */
        c = categoryDao.read(c.getId());
        assertEquals(1, c.getOffers().size());

        offerDao.delete(offer.getId());

        /* after offer is deleted, category should be empty */
        c = categoryDao.read(c.getId());
        assertEquals(c.getOffers().size(), 0);
    }

    @Test
    public void deleteOfferDeletesReferenceInTagsTest() {
        Offer offer = new Offer();
        offer.setName("offer with category");
        offer.setPrice(new Price(110D));
        offer.setCategory(new Category("deleteOfferDeletesReferenceInTagsTest some category1"));

        Tag tag1 = new Tag("deleteOfferDeletesReferenceInTagsTest 1");
        Tag tag2 = new Tag("deleteOfferDeletesReferenceInTagsTest 2");
        Tag tag3 = new Tag("deleteOfferDeletesReferenceInTagsTest 3");
        Tag tag4 = new Tag("deleteOfferDeletesReferenceInTagsTest 4");

        offer.getTags().add(tag1);
        offer.getTags().add(tag2);
        offer.getTags().add(tag3);

        offer = offerDao.create(offer);

        for (Tag t : offer.getTags()) {
            if      (t.getName().equals(tag1.getName())) tag1 = t;
            else if (t.getName().equals(tag2.getName())) tag2 = t;
            else                                         tag3 = t;
        }

        Offer offer2 = new Offer();
        offer2.setName("another offer with category");
        offer2.setPrice(new Price(330D));
        offer2.setCategory(new Category("deleteOfferDeletesReferenceInTagsTest some category2"));
        offer2.getTags().add(tag1);
        offer2.getTags().add(tag2);
        offer2.getTags().add(tag4);

        offer2 = offerDao.create(offer2);

        Tag finalTag4 = tag4;
        tag4 = offer2.getTags().stream()
                .filter( t -> t.getName().equals(finalTag4.getName()))
                .findAny()
                .get();

        offerDao.delete(offer.getId());

        TagDao tagDao = new TagDaoImpl();
        tag1 = tagDao.read(tag1.getId());
        tag2 = tagDao.read(tag2.getId());
        tag3 = tagDao.read(tag3.getId());
        tag4 = tagDao.read(tag4.getId());

        assertEquals(1, tag1.getOffers().size());
        assertEquals(1, tag2.getOffers().size());
        assertEquals(0, tag3.getOffers().size());
        assertEquals(1, tag4.getOffers().size());
    }
}