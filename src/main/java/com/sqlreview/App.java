package com.sqlreview;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.github.javafaker.Lorem;
import com.sqlreview.entity.Paper;
import com.sqlreview.exception.DaoException;
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
    private static final Integer NUM_OF_PAPERS = 20;

    private static Faker FAKER;
    private static PaperOperatorFacadeImpl paperOperatorFacade;
    public static void main( String[] args )
    {
        init();
//        insertPapers();
        paperOperatorFacade.deleteTable();
    }

    private static void init(){
        FAKER = new Faker();
        paperOperatorFacade = PaperOperatorFacadeImpl.getInstance();
    }

    private static void insertPapers(){
        for (int i=1; i <= NUM_OF_PAPERS; i++){
            Book book = FAKER.book();
            String lorem = FAKER.lorem().sentence();
            Paper testPaper = new Paper(i,book.author(),lorem);
            try{
                paperOperatorFacade.addRow(testPaper);
            }catch (DaoException ex){
                throw new RuntimeException(ex);
            }
        }

    }
}
