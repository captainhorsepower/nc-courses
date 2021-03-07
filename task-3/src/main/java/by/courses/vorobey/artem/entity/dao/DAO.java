package by.courses.vorobey.artem.entity.dao;

import java.util.List;

public interface DAO <T> {

    T create(T item);

    T read(Long id);

    T update(T item);

    void delete(Long id);

    List<T> readAll();
}
