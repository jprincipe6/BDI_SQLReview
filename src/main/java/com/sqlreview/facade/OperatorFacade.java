package com.sqlreview.facade;

import com.sqlreview.exception.DaoException;

public interface OperatorFacade <T>{
    int addRow(T t) throws DaoException;

    void deleteRow(T t);

    void deleteTable();

    boolean isEmpty();

    boolean isDuplicate(Integer pk1, Integer pk2);
}
