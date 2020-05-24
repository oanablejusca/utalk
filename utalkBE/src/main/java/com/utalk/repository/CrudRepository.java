package com.utalk.repository;

import java.sql.Connection;
import java.util.List;

public interface CrudRepository<T> {

    T create(Connection connection, T object);

    T getById(Connection connection, Integer id);

    List<T> getAll(Connection connection);

    boolean update(Connection connection, T object);

    boolean delete(Connection connection, Integer id);

    boolean deleteAll(Connection connection);
}

