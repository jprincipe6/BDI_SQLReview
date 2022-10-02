package com.sqlreview.facade.entity;

import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Author;
import com.sqlreview.entity.Submits;
import com.sqlreview.entity.daos.AuthorDao;
import com.sqlreview.entity.daos.SubmitsDao;
import com.sqlreview.exception.DaoException;
import com.sqlreview.exception.custum.AddRecordException;
import com.sqlreview.facade.OperatorFacade;

import java.util.Optional;

public class SubmitsOperatorFacadeImpl implements OperatorFacade<Submits> {

    private static final SubmitsOperatorFacadeImpl instance = new SubmitsOperatorFacadeImpl();

    private static final Dao<Submits, Integer> SUBMITS_DAO = new SubmitsDao();

    public static SubmitsOperatorFacadeImpl getInstance(){
        return instance;
    }
    @Override
    public int addRow(Submits submits) throws DaoException {
        Optional<Integer> idPaper = SUBMITS_DAO.save(submits);
        if (idPaper.isEmpty()){
            throw new AddRecordException();
        }
        return idPaper.get().intValue();
    }

    @Override
    public void deleteRow(Submits submits) {

    }

    @Override
    public void deleteTable() {
        SUBMITS_DAO.truncateTable();
    }

    @Override
    public boolean isEmpty() {
        return ((SUBMITS_DAO.isEmptyTable().get().intValue()==0)?true:false);
    }

    @Override
    public boolean isDuplicate(Integer pk1, Integer pk2) {
        return ((SUBMITS_DAO.isDuplicate(pk1, pk2).get().intValue()==0)?false:true);
    }
}
