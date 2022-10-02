package com.sqlreview.facade.entity;

import com.sqlreview.dao.Dao;
import com.sqlreview.entity.Author;
import com.sqlreview.entity.Conference;
import com.sqlreview.entity.daos.AuthorDao;
import com.sqlreview.entity.daos.ConferenceDao;
import com.sqlreview.exception.DaoException;
import com.sqlreview.exception.custum.AddRecordException;
import com.sqlreview.facade.OperatorFacade;

import java.util.Optional;

public class ConferenceOperatorFacadeImpl implements OperatorFacade<Conference> {

    private static final ConferenceOperatorFacadeImpl instance = new ConferenceOperatorFacadeImpl();

    private static final Dao<Conference, Integer> CONFERENCE_DAO = new ConferenceDao();

    public static ConferenceOperatorFacadeImpl getInstance(){
        return instance;
    }
    @Override
    public int addRow(Conference conference) throws DaoException {
        Optional<Integer> idPaper = CONFERENCE_DAO.save(conference);
        if (idPaper.isEmpty()){
            throw new AddRecordException();
        }
        return idPaper.get().intValue();
    }

    @Override
    public void deleteRow(Conference conference) {

    }

    @Override
    public void deleteTable() {
        CONFERENCE_DAO.truncateTable();
    }

    @Override
    public boolean isEmpty() {
        return ((CONFERENCE_DAO.isEmptyTable().get().intValue()==0)?true:false);
    }
}
