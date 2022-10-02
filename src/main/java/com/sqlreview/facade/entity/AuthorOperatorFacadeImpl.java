package com.sqlreview.facade.entity;

import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Author;
import com.sqlreview.entity.daos.AuthorDao;
import com.sqlreview.exception.DaoException;
import com.sqlreview.exception.custum.AddRecordException;
import com.sqlreview.facade.OperatorFacade;

import java.util.Optional;

public class AuthorOperatorFacadeImpl implements OperatorFacade<Author> {

    private static final AuthorOperatorFacadeImpl instance = new AuthorOperatorFacadeImpl();

    private static final Dao<Author, Integer> AUTHOR_DAO = new AuthorDao();

    public static AuthorOperatorFacadeImpl getInstance(){
        return instance;
    }
    @Override
    public int addRow(Author author) throws DaoException {
        Optional<Integer> idPaper = AUTHOR_DAO.save(author);
        if (idPaper.isEmpty()){
            throw new AddRecordException();
        }
        return idPaper.get().intValue();
    }

    @Override
    public void deleteRow(Author author) {

    }

    @Override
    public void deleteTable() {
        AUTHOR_DAO.truncateTable();
    }

    @Override
    public boolean isEmpty() {
        return ((AUTHOR_DAO.isEmptyTable().get().intValue()==0)?true:false);
    }

    @Override
    public boolean isDuplicate(Integer pk1, Integer pk2) {
        return false;
    }
}
