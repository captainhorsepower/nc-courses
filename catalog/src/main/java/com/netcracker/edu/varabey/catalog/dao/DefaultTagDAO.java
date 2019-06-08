package com.netcracker.edu.varabey.catalog.dao;

import com.netcracker.edu.varabey.catalog.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * DAO class for Tags. Allows for creating, updating and removing tags from the Catalog database.*/
@Repository
public class DefaultTagDAO implements TagDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }

    @Override
    public Collection<Tag> saveAll(Collection<Tag> tags) {
        tags.forEach(em::persist);
        return tags;
    }

    @Override
    public Tag findById(Long id) {
        return em.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        return em.createNamedQuery("Tag.findByName", Tag.class)
                .setParameter("name", name)
                .setMaxResults(1).getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        Tag tag = em.getReference(Tag.class, id);
        em.remove(tag);

        /* this relation has to be removed by hand, because
         * offer it the owner of the owner ship (which means it removes
         * rows from relation table automatically), while tags is NOT the owner
         */
        tag.getOffers().forEach( o -> o.getTags().remove(tag));
    }
}
