package com.sqlreview.facade;

import com.sqlreview.exception.DaoException;

public interface OperatorFacade <T>{
    int addRow(T t) throws DaoException;

    void deleteRow(T t);
}
