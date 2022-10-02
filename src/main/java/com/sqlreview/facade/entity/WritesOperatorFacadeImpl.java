package com.sqlreview.facade.entity;

import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Writes;
import com.sqlreview.entity.daos.WritesDao;
import com.sqlreview.exception.DaoException;
import com.sqlreview.exception.custum.AddRecordException;
import com.sqlreview.facade.OperatorFacade;

import java.util.Optional;

public class WritesOperatorFacadeImpl implements OperatorFacade<Writes> {

    private static final WritesOperatorFacadeImpl instance = new WritesOperatorFacadeImpl();

    private static final Dao<Writes, Integer> WRITES_DAO = new WritesDao();

    public static WritesOperatorFacadeImpl getInstance(){
        return instance;
    }
    @Override
    public int addRow(Writes writes) throws DaoException {
        Optional<Integer> idPaper = WRITES_DAO.save(writes);
        if (idPaper.isEmpty()){
            throw new AddRecordException();
        }
        return idPaper.get().intValue();
    }

    @Override
    public void deleteRow(Writes writes) {

    }

    @Override
    public void deleteTable() {
        WRITES_DAO.truncateTable();
    }

    @Override
    public boolean isEmpty() {
        return ((WRITES_DAO.isEmptyTable().get().intValue()==0)?true:false);
    }
}
