package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Service for category. Uses transactional scope entity manager.
 */
public class DefaultCategoryDAO implements CategoryDAO {
    private EntityManagerFactory emf = PostgreSQLDatabaseEntityManagerFactory.getInstance();

    /**
     * create new category in catalog. At this point category id should be null,
     * offers should be empty, name should be unique.
     * @param category with null id
     * @return created category with initialized id
     */
    @Override
    public Category create(Category category) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
        em.close();
        return category;
    }

    /**
     * same as <code>create(Category category)</code>, but for the whole
     * Collection of categories
     * @param categories with null ids
     * @return Collection of created categories
     */
    @Override
    public Collection<Category> createAll(Collection<Category> categories) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        categories.forEach(em::persist);
        em.getTransaction().commit();
        em.close();
        return categories;
    }

    /**
     * retrieve category from the Catalog.
     * @param id of wanted category
     * @return retrieved category, or null if nothing found.
     */
    @Override
    public Category read(Long id) {
        EntityManager em = emf.createEntityManager();
        Category category = em.find(Category.class, id);
        em.close();
        return category;
    }

    /**
     * Merge the state of the given category into the catalog.
     *
     * @param category  category instance
     * @return updated category.
     * @throws IllegalArgumentException if category is new or removed category
     */
    @Override
    public Category update(Category category) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery("SELECT count(c.id) FROM Category c where c.id = ?1");
        q.setParameter(1, category.getId());
        long persistedCategoryCount = (Long) q.getSingleResult();
        if (persistedCategoryCount == 0) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("unable to update category, that is not stored in database");
        }

        category = em.merge(category);
        em.getTransaction().commit();
        em.close();
        return category;
    }

    /**
     * removes category and all related offers from the catalog.
     * @param id of category to be removed
     */
    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Category c = em.getReference(Category.class, id);
        em.remove(c);
        em.getTransaction().commit();
        em.close();
    }
}
