package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

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
     * creates a tags in the underlying database.
     * Tag should have null id and offers should be empty.
     * Otherwise, expect issues with data.
     *
     * @param tag with set unique name and nothing else
     * @return given tags with initialized id.
     */
    @Override
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }

    /**
     * Same rules as for <code>save(Tag tags)</code>.
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
     * retrieves the tags from the database by id.
     * @param id - must not be null
     * @return tags with everything set.
     */
    @Override
    public Tag findById(Long id) {
        return em.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        TypedQuery<Tag> q = em.createQuery(
                "SELECT t FROM Tag t where t.name = :name",
                Tag.class
        );
        q.setParameter("name", name);

        List<Tag> list = q.setMaxResults(1).getResultList();
        return list.stream().findFirst().orElse(null);
    }

    /**
     * removes a tags from the database.
     *
     * @param id of the tags to be removed.
     * @throws javax.persistence.EntityNotFoundException if there is no tags
     *      with given id in the database.
     */
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
