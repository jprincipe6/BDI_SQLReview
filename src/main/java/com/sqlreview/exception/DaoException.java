package com.sqlreview.exception;

public abstract class DaoException extends Exception{
    public DaoException(String message) {
        super(message);
    }
}
