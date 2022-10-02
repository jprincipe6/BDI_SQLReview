package com.sqlreview.dao;

import java.util.Optional;

public interface Dao <T, I>{

    Optional<I> save(T t);

    void delete(T t);

    void truncateTable();

    Optional<I> isEmptyTable();
}
