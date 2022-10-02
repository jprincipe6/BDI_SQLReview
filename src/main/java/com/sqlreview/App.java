package com.sqlreview;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.University;
import com.sqlreview.entity.Author;
import com.sqlreview.entity.Paper;
import com.sqlreview.exception.DaoException;
import com.sqlreview.facade.entity.AuthorOperatorFacadeImpl;
import com.sqlreview.facade.entity.PaperOperatorFacadeImpl;

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
    public static void main( String[] args )
    {
        init();
        cleanTables();
        insertPapers();
        insertAuthors();

    }

    private static void init(){
        FAKER = new Faker();
        paperOperatorFacade = PaperOperatorFacadeImpl.getInstance();
        authorOperatorFacade = AuthorOperatorFacadeImpl.getInstance();
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

    private static void cleanTables(){
        if (!paperOperatorFacade.isEmpty()){
            paperOperatorFacade.deleteTable();
        }
        if (!authorOperatorFacade.isEmpty()){
            authorOperatorFacade.deleteTable();
        }
    }
}
