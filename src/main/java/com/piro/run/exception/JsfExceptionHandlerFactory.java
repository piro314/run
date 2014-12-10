package com.piro.run.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Created by ppirovski on 12/4/14. In Code we trust
 */
public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;
    // this injection handles jsf
    public JsfExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    //create your own ExceptionHandler
    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result =
                new JsfExceptionHandler(parent.getExceptionHandler());
        return result;
    }
}
