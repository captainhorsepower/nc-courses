package com.netcracker.edu.varabey.junit.dao;

import com.netcracker.edu.varabey.dao.OfferDAO;
import com.netcracker.edu.varabey.dao.DefaultOfferDAO;
import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.dao.DefaultTagDAO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Price;
import com.netcracker.edu.varabey.entity.Tag;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagDAOTest {
    private TagDAO tagDao = new DefaultTagDAO();
    private OfferDAO offerDao = new DefaultOfferDAO();


    @Test
    public void testCreateAndReadOneTag() {
        Tag tag = new Tag();
        tag.setName("testCreateAndReadOneTag tag1");
        tag = tagDao.create(tag);

        Tag tag1 = tagDao.read(tag.getId());
        assertNotNull(tag1);
        assertEquals(tag.toString(), tag1.toString());
    }

    @Test
    public void testUpdateTag() {
        Tag tag = new Tag();
        tag.setName("testUpdateTag tag1");
        tag = tagDao.create(tag);

        tag.setName("testUpdateTag new tag1");

        tag = tagDao.update(tag);

        Tag tag1 = tagDao.read(tag.getId());
        assertNotNull(tag1);
        assertEquals(tag.toString(), tag1.toString());
    }

    @Test
    public void updateTagNameUpdatesTagNameInOffer() {
        Tag tag = new Tag();
        tag.setName("updateTagNameUpdatesTagNameInOffer tag1");
        tag = tagDao.create(tag);


        Offer offer = new Offer();
        offer.setName("of1");
        offer.setPrice(new Price(100.));
        offer.setCategory(new Category("updateTagNameUpdatesTagNameInOffer c1"));
        offer.addTag(tag);

        offer = offerDao.create(offer);

        tag.setName("updateTagNameUpdatesTagNameInOffer new tag1");
        tag = tagDao.update(tag);

        offer = offerDao.read(offer.getId());
        assertNotNull(offer);
        assertEquals(tagDao.read(tag.getId()).getName(), offer.getTags().iterator().next().getName());
    }

    @Test
    public void testDeleteTag() {
        Tag tag = new Tag();
        tag.setName("testDeleteTag tag1");

        tag = tagDao.create(tag);

        tagDao.delete(tag.getId());

        Tag tag1 = tagDao.read(tag.getId());

        assertNull(tag1);
    }

    @Test
    public void deleteTagDeletesReferenceInOffers() {

        Offer offer = new Offer();
        offer.setName("of1");
        offer.setCategory(new Category("deleteTagDeletesReferenceInOffers cat1"));
        offer.setPrice(new Price(10D));

        Tag tag = new Tag("deleteTagDeletesReferenceInOffers tag1");
        tag = service.createTag(tag);
        offer.addTag(tag);

        tag = new Tag("deleteTagDeletesReferenceInOffers tag2");
        tag = service.createTag(tag);
        offer.addTag(tag);

        offer = offerDao.create(offer);

        offer.removeTag(tag);


        /* delete tag2 */
        tagDao.delete(tag.getId());

        Tag tag1 = tagDao.read(tag.getId());
        assertNull(tag1);

        Offer readOffer = offerDao.read(offer.getId());

        assertNotNull(readOffer);
        assertEquals(offer.getTags().size(), readOffer.getTags().size());
    }
}
