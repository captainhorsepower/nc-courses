package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;

public class CategoryDaoImpl implements CategoryDao {
    private EntityManager em = PostgreSQLDatabaseManager.getInstance().getEntityManager();

    @Override
    public Category create(Category category) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /* ensure transaction gets closed */
        try {
            em.persist(category);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();

        em.detach(category);
        return category;
    }

    @Override
    public Collection<Category> createAll(Collection<Category> categories) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            categories.forEach(category -> em.persist(category));
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();

        categories.forEach( category -> em.detach(category));
        return categories;
    }

    @Override
    public Category read(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Category category = em.find(Category.class, id);
        if (category != null) em.refresh(category);
        tx.commit();
        if (category != null) em.detach(category);
        return category;
    }

    /**
     * Merge the state of the given category (only if it is previously persisted category)
     * into the current persistence context.
     *
     * @param category  category instance
     * @return the managed category that the state was merged to
     * @throws IllegalArgumentException if instance is a new or removed category
     */
    @Override
    public Category update(Category category) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query q = em.createQuery("SELECT count(c.id) FROM Category c where c.id = ?1");
        q.setParameter(1, category.getId());
        long persistedCategoryCount = (Long) q.getSingleResult();
        if (persistedCategoryCount == 0) {
            tx.rollback();
            throw new IllegalArgumentException("unable to update category, that is not stored in database");
        }

        category = em.merge(category);
        tx.commit();

        em.detach(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Category c = em.getReference(Category.class, id);
        em.remove(c);
        tx.commit();
    }
}
