package com.netcracker.edu.varabey.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PostgreSQLDatabaseEntityManagerFactory {
    private final static EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory("order_entry.catalog");

    private PostgreSQLDatabaseEntityManagerFactory() {}

    public static EntityManagerFactory getInstance() {
        return PostgreSQLDatabaseEntityManagerFactory.INSTANCE;
    }
}
