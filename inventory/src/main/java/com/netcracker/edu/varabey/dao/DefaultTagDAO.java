package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Service class for Tags. Allows for creating, updating and removing tags from
 * an underlying database. Incapsulated EntityManager's scope is transactional
 * for simplicity and security. So performance might suffer just a little
 * tiny bit. If performance is a bottleneck for you, try another implementation.
 */
@Repository
public class DefaultTagDAO implements TagDAO {
    @PersistenceContext
    private EntityManager em;

    /**
     * creates a tag in the underlying database.
     * Tag should have null id and offers should be empty.
     * Otherwise, expect issues with data.
     *
     * @param tag with set unique name and nothing else
     * @return given tag with initialized id.
     */
    @Override
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }

    /**
     * Same rules as for <code>save(Tag tag)</code>.
     *
     * @param tags with set names and nothing else.
     * @return given collection of tags with initialized ids
     */
    @Override
    public Collection<Tag> saveAll(Collection<Tag> tags) {
        tags.forEach(em::persist);
        return tags;
    }

    /**
     * retrieves a tag from the database by id.
     * @param id tag id
     * @return tag with everything set.
     */
    @Override
    public Tag find(Long id) {
        return em.find(Tag.class, id);
    }

    /**
     * updates given tag's state in the database.
     * Use this to change tag name only.
     * You should NOT add any offers to the tag via this method
     *
     * @param tag with updates
     * @return updated tag
     * @throws IllegalArgumentException if given tag isn't present in the database.
     */
    @Override
    public Tag update(Tag tag) {
        Query q = em.createQuery("SELECT count(t.id) FROM Tag t where t.id = ?1");
        q.setParameter(1, tag.getId());
        long persistedTagCount = (Long) q.getSingleResult();
        if (persistedTagCount == 0) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("unable to update tag, that is not stored in database");
        }
        return em.merge(tag);
    }

    /**
     * removes a tag from the database.
     *
     * @param id of the tag to be removed.
     * @throws javax.persistence.EntityNotFoundException if there is no tag
     *      with given id in the database.
     */
    @Override
    public void delete(Long id) {
        Tag tag = em.getReference(Tag.class, id);
        em.remove(tag);
        /* this relation has to be removed by hand, because
         * offer it the owner of the owner ship (which means it removes
         * rows from relation table automatically), while tag is NOT the owner
         */
        tag.getItems().forEach( i -> i.getTags().remove(tag));
    }
}
