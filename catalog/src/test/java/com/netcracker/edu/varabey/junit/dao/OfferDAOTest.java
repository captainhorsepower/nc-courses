package com.netcracker.edu.varabey.junit.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.OfferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferDAOTest {

    @Autowired
    private OfferService offerService;

    @Test
    public void findOffersByCategoryTest() {
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        Offer offer3 = new Offer();

        Category cat = new Category("findOffersByCategoryTest category1");

        offer1.setName("offer1");
        offer1.setPrice(new Price(10D));
        offer1.setCategory(cat);
        offer1 = offerService.create(offer1);

        cat = offer1.getCategory();

        offer2.setName("offer2");
        offer2.setPrice(new Price(20D));
        offer2.setCategory(cat);
        offer2 = offerService.create(offer2);

        offer3.setName("offer2");
        offer3.setPrice(new Price(30D));
        offer3.setCategory(new Category("findOffersByCategoryTest category2"));
        offer3 = offerService.create(offer3);

        List<Offer> catOffers = offerService.findAllOffersByCategory(cat);
        assertEquals(2, catOffers.size());
        assertEquals(1, offerService.findAllOffersByCategory(offer3.getCategory()).size());

        // clean-up
        offerService.delete(offer1.getId());
        offerService.delete(offer2.getId());
        offerService.delete(offer3.getId());
    }

    @Test
    public void findAllWithPriceInRangeTest() {
        Offer offer1 = new Offer();
        Offer offer2 = new Offer();
        Offer offer3 = new Offer();

        Category cat = new Category("findOffersByPriceRangeTest category1");

        offer1.setName("offer1");
        offer1.setPrice(new Price(55D));
        offer1.setCategory(cat);
        offer1 = offerService.create(offer1);

        cat = offer1.getCategory();

        offer2.setName("offer2");
        offer2.setPrice(new Price(100D));
        offer2.setCategory(cat);
        offer2 = offerService.create(offer2);

        offer3.setName("offer2");
        offer3.setPrice(new Price(120D));
        offer3.setCategory(new Category("findOffersByPriceRangeTest category2"));
        offer3 = offerService.create(offer3);

        assertEquals(1, offerService.findAllWithPriceInRange(10., 60.).size());
        assertEquals(3, offerService.findAllWithPriceInRange(10., 120.).size());
        assertEquals(2, offerService.findAllWithPriceInRange(60., 200.).size());
        assertEquals(0, offerService.findAllWithPriceInRange(60., 70.).size());
        assertEquals(0, offerService.findAllWithPriceInRange(130., 40.).size());

        // clean-up
        offerService.delete(offer1.getId());
        offerService.delete(offer2.getId());
        offerService.delete(offer3.getId());
    }

//    @Test
//    public void findAllWithTagsTest() {
//        Offer offer1 = new Offer();
//        Offer offer2 = new Offer();
//        Offer offer3 = new Offer();
//
//        Category cat = new Category("findAllWithTagsTest category1");
//        cat = offerService.save(cat);
//
//        Tag t1 = new Tag("findAllWithTagsTest t1");
//        Tag t2 = new Tag("findAllWithTagsTest t2");
//        Tag t3 = new Tag("findAllWithTagsTest t3");
//        Tag t4 = new Tag("findAllWithTagsTest t4");
//
//        t1 = offerService.create(t1);
//        t2 = offerService.create(t2);
//        t3 = offerService.create(t3);
//        t4 = offerService.create(t4);
//
//
//        /* t1, t2 */
//        offer1.setName("offer1");
//        offer1.setPrice(new Price(10D));
//        offer1.setCategory(cat);
//        offer1.addTag(t1);
//        offer1.addTag(t2);
//        offer1 = offerService.create(offer1);
//
//        /* t1, t3, t4 */
//        offer2.setName("offer2");
//        offer2.setPrice(new Price(20D));
//        offer2.setCategory(cat);
//        offer2.addTag(t1);
//        offer2.addTag(t3);
//        offer2.addTag(t4);
//        offer2 = offerService.create(offer2);
//
//        /* t3, t4 */
//        offer3.setName("offer3");
//        offer3.setPrice(new Price(30D));
//        offer3.setCategory(cat);
//        offer3.addTag(t3);
//        offer3.addTag(t4);
//        offer3 = offerService.create(offer3);
//
//        List<Offer> offers;
//
//        /* t1 */
//        offers = offerService.findAllOffersWithTags(Collections.singletonList(t1));
//        assertEquals(2, offers.size());
//
//        /* t1, t2 */
//        offers = offerService.findAllOffersWithTags(asList(t1, t2));
//        assertEquals(1, offers.size());
//
//        /* t2 */
//        offers = offerService.findAllOffersWithTags(Collections.singletonList(t2));
//        assertEquals(1, offers.size());
//
//        /* t1, t3, t4 */
//        offers = offerService.findAllOffersWithTags(asList(t1, t3, t4));
//        assertEquals(1, offers.size());
//
//        /* t3 */
//        offers = offerService.findAllOffersWithTags(Collections.singletonList(t3));
//        assertEquals(2, offers.size());
//
//        /* t3, t4 */
//        offers = offerService.findAllOffersWithTags(asList(t3, t4));
//        assertEquals(2, offers.size());
//
//        /* t1, t2, t3, t4 */
//        offers = offerService.findAllOffersWithTags(asList(t1, t2, t3, t4));
//        assertEquals(0, offers.size());
//
//        // clean-up
//        offerService.delete(offer1.getId());
//        offerService.delete(offer2.getId());
//        offerService.delete(offer3.getId());
//    }

    @Test
    public void createAndReadOneOfferTest() {

        Offer offer = new Offer();
        offer.setName("kitty");
        offer.setPrice(new Price(110D));
        offer.setCategory(new Category("createAndReadOneOfferTest category1"));
        offer.getTags().add(new Tag("createAndReadOneOfferTest tag1"));
        offer.getTags().add(new Tag("createAndReadOneOfferTest tag2"));

        offer = offerService.create(offer);

        Offer readOffer = offerService.findById(offer.getId());

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

        offer1 = offerService.create(offer1);
        offer2 = offerService.create(offer2);

        Offer readOffer1 = offerService.findById(offer1.getId());
        Offer readOffer2 = offerService.findById(offer2.getId());

        assertNotNull(readOffer1);
        assertNotNull(readOffer2);
        assertEquals(offer1.toString(), readOffer1.toString());
        assertEquals(offer2.toString(), readOffer2.toString());
    }

    @Test
    public void updateTagSetTest() {
        Offer offer = new Offer();
        offer.setName("tags set update test1");
        offer.setCategory(new Category("updateTagSetTest category"));
        offer.setPrice(new Price(1D));

        Tag t1 = new Tag("updateTagSetTest t1");
        Tag t2 = new Tag("updateTagSetTest t2");
        Tag t3 = new Tag("updateTagSetTest t3");

        offer.getTags().add(t1);
        offer.getTags().add(t2);

        offer = offerService.create(offer);

        assertEquals(2, offerService.findById(offer.getId()).getTags().size());

        offer.getTags().remove(t1);
        offer.getTags().add(t3);

        offerService.update(offer);

        assertEquals(2, offerService.findById(offer.getId()).getTags().size());
    }

    @Test
    public void updateCategoryTest() {
        Offer offer = new Offer();
        offer.setPrice(new Price(1D));
        offer.setName("category update test1");
        offer.setCategory(new Category("updateCategoryTest category1"));

        offer = offerService.create(offer);

        offer.setCategory(new Category("updateCategoryTest category2"));

        offerService.update(offer);

        assertEquals(offer.getCategory().getName(), offerService.findById(offer.getId()).getCategory().getName());
    }

//    @Test
//    public void deleteOfferDeletesReferenceInCategoryTest() {
//        Offer offer = new Offer();
//        offer.setName("offer with category");
//        offer.setPrice(new Price(110D));
//        offer.setCategory(new Category("deleteOfferDeletesReferenceInCategoryTest deleted items"));
//
//        offer = offerService.create(offer);
//
//        Category c = offer.getCategory();
//
//        /* while offer exists, it should be contained in category */
//        c = offerService.findById(c.getId());
//        assertEquals(1, c.getOffers().size());
//
//        offerService.delete(offer.getId());
//
//        /* after offer is deleted, category should be empty */
//        c = offerService.findById(c.getId());
//        assertEquals(c.getOffers().size(), 0);
//    }
//
//    @Test
//    public void deleteOfferDeletesReferenceInTagsTest() {
//        Offer offer = new Offer();
//        offer.setName("offer with category");
//        offer.setPrice(new Price(110D));
//        offer.setCategory(new Category("deleteOfferDeletesReferenceInTagsTest some category1"));
//
//        Tag tag1 = new Tag("deleteOfferDeletesReferenceInTagsTest 1");
//        Tag tag2 = new Tag("deleteOfferDeletesReferenceInTagsTest 2");
//        Tag tag3 = new Tag("deleteOfferDeletesReferenceInTagsTest 3");
//        Tag tag4 = new Tag("deleteOfferDeletesReferenceInTagsTest 4");
//
//        offer.getTags().add(tag1);
//        offer.getTags().add(tag2);
//        offer.getTags().add(tag3);
//
//        offer = offerService.create(offer);
//
//        for (Tag t : offer.getTags()) {
//            if      (t.getName().equals(tag1.getName())) tag1 = t;
//            else if (t.getName().equals(tag2.getName())) tag2 = t;
//            else                                         tag3 = t;
//        }
//
//        Offer offer2 = new Offer();
//        offer2.setName("another offer with category");
//        offer2.setPrice(new Price(330D));
//        offer2.setCategory(new Category("deleteOfferDeletesReferenceInTagsTest some category2"));
//        offer2.getTags().add(tag1);
//        offer2.getTags().add(tag2);
//        offer2.getTags().add(tag4);
//
//        offer2 = offerService.create(offer2);
//
//        Tag finalTag4 = tag4;
//        tag4 = offer2.getTags().stream()
//                .filter( t -> t.getName().equals(finalTag4.getName()))
//                .findAny()
//                .orElse(new Tag());
//
//        offerService.delete(offer.getId());
//
//        tag1 = offerService.findById(tag1.getId());
//        tag2 = offerService.findById(tag2.getId());
//        tag3 = offerService.findById(tag3.getId());
//        tag4 = offerService.findById(tag4.getId());
//
//        assertEquals(1, tag1.getOffers().size());
//        assertEquals(1, tag2.getOffers().size());
//        assertEquals(0, tag3.getOffers().size());
//        assertEquals(1, tag4.getOffers().size());
//    }
}
