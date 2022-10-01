package com.sqlreview;

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

    private static PaperOperatorFacadeImpl paperOperatorFacade;
    public static void main( String[] args )
    {
        init();

        Paper testPaper = new Paper(1,"test_1","abstract_1");

        try{
            paperOperatorFacade.addRow(testPaper);
        }catch (DaoException ex){
            throw new RuntimeException(ex);
        }

        paperOperatorFacade.deleteRow(testPaper);
    }

    private static void init (){
        paperOperatorFacade = PaperOperatorFacadeImpl.getInstance();
    }
}
