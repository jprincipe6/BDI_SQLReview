package com.sqlreview;

import com.github.javafaker.*;
import com.github.javafaker.Number;
import com.sqlreview.entity.Author;
import com.sqlreview.entity.Conference;
import com.sqlreview.entity.Paper;
import com.sqlreview.entity.Writes;
import com.sqlreview.exception.DaoException;
import com.sqlreview.facade.entity.AuthorOperatorFacadeImpl;
import com.sqlreview.facade.entity.ConferenceOperatorFacadeImpl;
import com.sqlreview.facade.entity.PaperOperatorFacadeImpl;
import com.sqlreview.facade.entity.WritesOperatorFacadeImpl;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger LOGGER =
            Logger.getLogger(App.class.getName());
    private static final Integer NUM_OF_DATE = 20;

    private static Faker FAKER;
    private static PaperOperatorFacadeImpl paperOperatorFacade;

    private static AuthorOperatorFacadeImpl authorOperatorFacade;

    private static ConferenceOperatorFacadeImpl conferenceOperatorFacade;

    private static WritesOperatorFacadeImpl writesOperatorFacade;
    public static void main( String[] args )
    {
        init();
        cleanTables();
        insertPapers();
        insertAuthors();
        insertConferences();
        insertWrites();

    }

    private static void init(){
        FAKER = new Faker();
        paperOperatorFacade = PaperOperatorFacadeImpl.getInstance();
        authorOperatorFacade = AuthorOperatorFacadeImpl.getInstance();
        conferenceOperatorFacade = ConferenceOperatorFacadeImpl.getInstance();
        writesOperatorFacade = WritesOperatorFacadeImpl.getInstance();
    }

    private static void insertPapers(){
        for (int i = 1; i <= NUM_OF_DATE; i++){
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
        for (int i = 1; i <= NUM_OF_DATE; i++){
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
        for (int i = 1; i <= NUM_OF_DATE; i++){
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
        for (int i = 1; i <= NUM_OF_DATE; i++){
            Number number = FAKER.number();
            int authorId = number.numberBetween(1, NUM_OF_DATE);
            int paperId = number.numberBetween(1, NUM_OF_DATE);

            Writes testWrites = new Writes(authorId, paperId);
            try{
                writesOperatorFacade.addRow(testWrites);
            }catch (DaoException ex){
                throw new RuntimeException(ex);
            }

        }
    }

    private static void cleanTables(){
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
