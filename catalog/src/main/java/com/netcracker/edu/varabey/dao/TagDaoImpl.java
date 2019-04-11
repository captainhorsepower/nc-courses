package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Offer;
import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Set;

public class TagDaoImpl implements TagDao {
    private EntityManager em = PostgreSQLDatabaseManager.getInstance().getEntityManager();

    @Override
    public Tag create(Tag tag) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.persist(tag);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();

        em.detach(tag);
        return tag;
    }

    @Override
    public Collection<Tag> createAll(Collection<Tag> tags) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            for (Tag t : tags) {
                em.persist(t);
            }
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();
        tags.forEach( t -> em.detach(t) );
        return tags;
    }

    @Override
    public Tag read(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Tag tag = em.find(Tag.class, id);
        if (tag != null) em.refresh(tag);
        tx.commit();
        if (tag != null) em.detach(tag);
        return tag;
    }


    @Override
    public Tag update(Tag tag) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("SELECT count(t.id) FROM Tag t where t.id = ?1");
        q.setParameter(1, tag.getId());
        long persistedTagCount = (Long) q.getSingleResult();
        if (persistedTagCount == 0) {
            tx.rollback();
            throw new IllegalArgumentException("unable to update tag, that is not stored in database");
        }
        em.merge(tag);
        tx.commit();

        em.detach(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Tag tag = em.getReference(Tag.class, id);
        em.remove(tag);
        tx.commit();
    }
}
