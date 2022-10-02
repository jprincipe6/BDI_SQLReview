package com.sqlreview.facade.entity;

import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Paper;
import com.sqlreview.entity.daos.PaperDao;
import com.sqlreview.exception.DaoException;
import com.sqlreview.exception.custum.AddRecordException;
import com.sqlreview.facade.OperatorFacade;

import java.util.Optional;

public class PaperOperatorFacadeImpl implements OperatorFacade<Paper> {

    private static final PaperOperatorFacadeImpl instance = new PaperOperatorFacadeImpl();

    private static final Dao<Paper, Integer> PAPER_DAO = new PaperDao();

    public static PaperOperatorFacadeImpl getInstance(){
        return instance;
    }

    @Override
    public int addRow(Paper paper) throws DaoException {
        Optional<Integer> idPaper = PAPER_DAO.save(paper);
        if (idPaper.isEmpty()){
            throw new AddRecordException();
        }
        return idPaper.get().intValue();
    }

    @Override
    public void deleteRow(Paper paper) {
        PAPER_DAO.delete(paper);
    }

    @Override
    public void deleteTable() {
        PAPER_DAO.truncateTable();
    }

    @Override
    public boolean isEmpty() {
        return ((PAPER_DAO.isEmptyTable().get().intValue()==0)?true:false);
    }
}
