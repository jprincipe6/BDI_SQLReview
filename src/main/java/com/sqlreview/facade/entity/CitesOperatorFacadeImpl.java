package com.sqlreview.facade.entity;

import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Cites;
import com.sqlreview.entity.Writes;
import com.sqlreview.entity.daos.CitesDao;
import com.sqlreview.entity.daos.WritesDao;
import com.sqlreview.exception.DaoException;
import com.sqlreview.exception.custum.AddRecordException;
import com.sqlreview.facade.OperatorFacade;

import java.util.Optional;

public class CitesOperatorFacadeImpl implements OperatorFacade<Cites> {

    private static final CitesOperatorFacadeImpl instance = new CitesOperatorFacadeImpl();

    private static final Dao<Cites, Integer> CITES_DAO = new CitesDao();

    public static CitesOperatorFacadeImpl getInstance(){
        return instance;
    }
    @Override
    public int addRow(Cites cites) throws DaoException {
        Optional<Integer> idPaper = CITES_DAO.save(cites);
        if (idPaper.isEmpty()){
            throw new AddRecordException();
        }
        return idPaper.get().intValue();
    }

    @Override
    public void deleteRow(Cites cites) {

    }

    @Override
    public void deleteTable() {
        CITES_DAO.truncateTable();
    }

    @Override
    public boolean isEmpty() {
        return ((CITES_DAO.isEmptyTable().get().intValue()==0)?true:false);
    }

    @Override
    public boolean isDuplicate(Integer pk1, Integer pk2) {
        return ((CITES_DAO.isDuplicate(pk1, pk2).get().intValue()==0)?false:true);
    }
}
