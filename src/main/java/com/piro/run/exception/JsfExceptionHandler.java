package com.piro.run.exception;

import com.sun.faces.context.FacesFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by ppirovski on 12/4/14. In Code we trust
 */
public class JsfExceptionHandler extends ExceptionHandlerWrapper {

    private static Logger LOG = LoggerFactory.getLogger(JsfExceptionHandler.class);

    final static String ERROR_PAGE  = "/public/error.jsf";
    final static String NOT_FOUND_PAGE  = "/static/404.html";

    private ExceptionHandler wrapped;

    public JsfExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }


    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {

        //Iterate over all unhandeled exceptions
        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            try {
                ExceptionQueuedEvent event = i.next();
                ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

                //obtain throwable object
                Throwable t = context.getException();

                if(t.getCause() != null && t.getCause() instanceof FacesFileNotFoundException){
                    this.notFound();
                    return;
                }
                String errorRef = String.valueOf(System.currentTimeMillis());
                LOG.warn("Handling exception with reference number: "+errorRef +" ==========================================================");
                LOG.warn("Exception: ", t);
                LOG.warn("End of handled exception: ========================================================================================");

                FacesContext fc = FacesContext.getCurrentInstance();

                fc.getExternalContext().getSessionMap().put("jsfException", t);
                fc.getExternalContext().getSessionMap().put("errorRef", errorRef);

                this.redirect(ERROR_PAGE);
            }
            catch (IOException e) {
                LOG.error("IOException in exception handler");
                e.printStackTrace();
            } finally {
                i.remove();
            }

        }
        //let the parent handle the rest
        getWrapped().handle();
    }

    private void redirect(String whereTo) throws IOException{

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, whereTo));

        extContext.redirect(url);


    }

    private void notFound() throws IOException{
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();

        extContext.redirect(NOT_FOUND_PAGE);
    }
}
