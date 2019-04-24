package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Service for category. Uses transactional scope entity manager.
 */
@Repository
public class DefaultCategoryDAO implements CategoryDAO {
    @PersistenceContext
    private EntityManager em;

    /**
     * save new category in inventory. At this point category id should be null,
     * name should be unique.
     * @param category with null id
     * @return created category with initialized id
     */
    @Override
    public Category save(Category category) {
        em.persist(category);
        return category;
    }

    /**
     * same as <b>save(Category category)</b>, but for the whole
     * Collection of categories
     * @param categories with null ids
     * @return Collection of created categories
     */
    @Override
    public Collection<Category> saveAll(Collection<Category> categories) {
        categories.forEach(em::persist);
        return categories;
    }

    /**
     * retrieve category from the Inventory.
     * @param id of wanted category
     * @return retrieved category, or null if nothing found.
     */
    @Override
    public Category find(Long id) {
        return em.find(Category.class, id);
    }

    /**
     * Merge the state of the given category into the inventory.
     *
     * @param category  category instance
     * @return updated category.
     */
    @Override
    public Category update(Category category) {
        return em.merge(category);
    }

    /**
     * removes category and all related orderItems from the Inventory.
     * @param id of category to be removed
     */
    @Override
    public void delete(Long id) {
        Category c = em.getReference(Category.class, id);
        em.remove(c);
    }
}
