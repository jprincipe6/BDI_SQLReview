package com.sqlreview;

import com.github.javafaker.*;
import com.github.javafaker.Number;
import com.sqlreview.entity.*;
import com.sqlreview.exception.DaoException;
import com.sqlreview.facade.entity.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger LOGGER =
            Logger.getLogger(App.class.getName());
    private static final Integer NUM_OF_DATA = 20;

    private static Faker FAKER;
    private static PaperOperatorFacadeImpl paperOperatorFacade;

    private static AuthorOperatorFacadeImpl authorOperatorFacade;

    private static ConferenceOperatorFacadeImpl conferenceOperatorFacade;

    private static WritesOperatorFacadeImpl writesOperatorFacade;

    private static SubmitsOperatorFacadeImpl submitsOperatorFacade;

    private static CitesOperatorFacadeImpl citesOperatorFacade;
    public static void main( String[] args )
    {
        init();
        cleanTables();
        insertPapers();
        insertAuthors();
        insertConferences();
        insertWrites();
        insertSubmits();
        insertCites();

    }

    private static void init(){
        FAKER = new Faker();
        paperOperatorFacade = PaperOperatorFacadeImpl.getInstance();
        authorOperatorFacade = AuthorOperatorFacadeImpl.getInstance();
        conferenceOperatorFacade = ConferenceOperatorFacadeImpl.getInstance();
        writesOperatorFacade = WritesOperatorFacadeImpl.getInstance();
        submitsOperatorFacade = SubmitsOperatorFacadeImpl.getInstance();
        citesOperatorFacade= CitesOperatorFacadeImpl.getInstance();
    }

    private static void insertPapers(){
        for (int i = 1; i <= NUM_OF_DATA; i++){
            Book book = FAKER.book();
            String lorem = FAKER.lorem().sentence();
            Paper testPaper = new Paper(i,book.title(),lorem);
            try{
                paperOperatorFacade.addRow(testPaper);
            }catch (DaoException ex){
                throw new RuntimeException(ex);
            }
        }
    }

    private static void insertAuthors(){
        for (int i = 1; i <= NUM_OF_DATA; i++){
            Name name = FAKER.name();
            University university = FAKER.university();
            String nameAuthor = name.firstName();
            String lastNameAuthor = name.lastName();
            String universityAuthor = university.name();
            String email = nameAuthor.toLowerCase()+"."+lastNameAuthor.toLowerCase()
                    +"@"+universityAuthor.replace(" ",".").toLowerCase()+".com";

            Author testAuthor = new Author(i,nameAuthor,email,universityAuthor);
            try{
                authorOperatorFacade.addRow(testAuthor);
            }catch (DaoException ex){
                throw new RuntimeException(ex);
            }
        }

    }

    private static void insertConferences(){
        for (int i = 1; i <= NUM_OF_DATA; i++){
            Number number = FAKER.number();
            Pokemon pokemon = FAKER.pokemon();
            int ranking = number.numberBetween(1, 10);
            String name = pokemon.name();

            Conference testConference = new Conference(i,name,ranking);
            try{
                conferenceOperatorFacade.addRow(testConference);
            }catch (DaoException ex){
                throw new RuntimeException(ex);
            }

        }
    }

    private static void insertWrites(){
        int i=1;
        while (i <= NUM_OF_DATA){
            Number number = FAKER.number();
            int authorId = number.numberBetween(1, NUM_OF_DATA);
            int paperId = number.numberBetween(1, NUM_OF_DATA);
            if(writesOperatorFacade.isDuplicate(authorId, paperId)){
                i = (NUM_OF_DATA - (NUM_OF_DATA-i)-1);
            }else {
                Writes testWrites = new Writes(authorId, paperId);
                try{
                    writesOperatorFacade.addRow(testWrites);
                }catch (DaoException ex){
                    throw new RuntimeException(ex);
                }
                i++;
            }
        }

    }

    private static void insertSubmits(){
        int i= 1;
        while (i <= NUM_OF_DATA) {
            Number number = FAKER.number();
            int paperId = number.numberBetween(1, NUM_OF_DATA);
            int confId = number.numberBetween(1, NUM_OF_DATA);
            int numAccepted = number.numberBetween(1, 4);
            boolean isAccepted = (numAccepted == 1) ? false : true;

            Calendar utilDate = Calendar.getInstance();
            utilDate.set(2023,number.numberBetween(1, 12), number.numberBetween(1, 30));
            Date currentTimestamp = new Date(utilDate.getTime().getTime());
            if(submitsOperatorFacade.isDuplicate(paperId, confId)){
                i = (NUM_OF_DATA - (NUM_OF_DATA-i)-1);
            }else {
                Submits testSubmits = new Submits(paperId, confId, isAccepted, currentTimestamp);
                try {
                    submitsOperatorFacade.addRow(testSubmits);
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
                i++;
            }

        }
    }

    private static void insertCites(){
        int i=1;
        while (i <= NUM_OF_DATA){
            Number number = FAKER.number();
            int paperIdFrom = number.numberBetween(1, NUM_OF_DATA);
            int paperIDto = number.numberBetween(1, NUM_OF_DATA);
            if(citesOperatorFacade.isDuplicate(paperIdFrom, paperIDto)){
                i = (NUM_OF_DATA - (NUM_OF_DATA-i)-1);
            }else {
                Cites testCites = new Cites(paperIdFrom, paperIDto);
                try{
                    citesOperatorFacade.addRow(testCites);
                }catch (DaoException ex){
                    throw new RuntimeException(ex);
                }
                i++;
            }
        }
    }

    private static void cleanTables(){
        if(!citesOperatorFacade.isEmpty()){
            citesOperatorFacade.deleteTable();
        }
        if(!submitsOperatorFacade.isEmpty()){
            submitsOperatorFacade.deleteTable();
        }
        if (!writesOperatorFacade.isEmpty()){
            writesOperatorFacade.deleteTable();
        }
        if (!paperOperatorFacade.isEmpty()){
            paperOperatorFacade.deleteTable();
        }
        if (!authorOperatorFacade.isEmpty()){
            authorOperatorFacade.deleteTable();
        }
        if(!conferenceOperatorFacade.isEmpty()){
            conferenceOperatorFacade.deleteTable();
        }
    }
}
