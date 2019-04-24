package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/** Service for category. Uses transactional scope entity manager. */
@Repository
public class DefaultCategoryDAO implements CategoryDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * save new category in catalog. At this point category id should be null,
     * offers should be empty, name should be unique.
     * @param category with null id
     * @return created category with initialized id
     */
    @Override
    public Category save(Category category) {
        em.persist(category);
        return category;
    }

    /**
     * same as <code>save(Category category)</code>, but for the whole
     * Collection of categories
     * @param categories with null ids
     * @return Collection of created categories
     */
    @Override
    public Collection<Category> createAll(Collection<Category> categories) {
        categories.forEach(em::persist);
        em.getTransaction().commit();
        return categories;
    }

    /**
     * retrieve category from the Catalog.
     * @param id of wanted category
     * @return retrieved category, or null if nothing found.
     */
    @Override
    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    /**
     * Merge the state of the given category into the catalog.
     * @param category  category instance
     * @return updated category.
     */
    @Override
    public Category update(Category category) {
        category = em.merge(category);
        return category;
    }

    /**
     * removes category and all related offers from the catalog.
     * @param id of category to be removed
     */
    @Override
    public void delete(Long id) {
        Category c = em.getReference(Category.class, id);
        em.remove(c);
    }
}
