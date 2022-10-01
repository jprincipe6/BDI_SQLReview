package com.sqlreview.exception.custum;

import com.sqlreview.exception.DaoException;

public class AddRecordException extends DaoException {
    public AddRecordException() {
        super("This record is impossible to save");
    }
}
