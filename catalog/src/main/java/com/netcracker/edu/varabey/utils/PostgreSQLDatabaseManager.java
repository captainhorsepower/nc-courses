package com.netcracker.edu.varabey.utils;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PostgreSQLDatabaseManager implements DatabaseManager {
    private EntityManager entityManager;
    private final static DatabaseManager INSTANCE = new PostgreSQLDatabaseManager();

    private PostgreSQLDatabaseManager() {
        entityManager = Persistence
                .createEntityManagerFactory("orderEntryPersistenceUnit")
                .createEntityManager();
    }

    public static DatabaseManager getInstance() {
        return PostgreSQLDatabaseManager.INSTANCE;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
