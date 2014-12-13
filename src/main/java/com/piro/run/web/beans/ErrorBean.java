package com.piro.run.web.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class ErrorBean implements Serializable {

	/**
	 * 
	 */


	public String getStackTrace() {
        // Get the current JSF context
        FacesContext context = FacesContext.getCurrentInstance();
        
        @SuppressWarnings("rawtypes")
		Map requestMap = context.getExternalContext().getRequestMap();
 
        // Fetch the exception
        Throwable ex = (Throwable) requestMap.get("jsfException");
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        if(ex == null){
        	
        	
        	System.out.println(session.getId());
        	ex = (Throwable)session.getAttribute("jsfException");
        	
         }
 
        session.removeAttribute("jsfException");
        
        // Create a writer for keeping the stacktrace of the exception
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        
        if(ex == null){
        	pw.println("Cannot show error");
        	return writer.toString();
        }
        
        pw.println(ex.toString());
 
        // Fill the stack trace into the write
        fillStackTrace(ex, pw);
 
        return writer.toString();
    }
 
    /**
    * Write the stack trace from an exception into a writer.
    *
    * @param ex
    *         Exception for which to get the stack trace
    * @param pw
    *         PrintWriter to write the stack trace
    */
    private void fillStackTrace(Throwable ex, PrintWriter pw) {
        if (null == ex) {
            return;
        }
        
        Throwable cause = ex.getCause();
 
        if (null != cause) {
            pw.println("Cause: "+ cause);
            fillStackTrace(cause, pw);
        }
        
    }
    
    public String getErrorRef(){
    	String errorRef = (String)FacesContext.getCurrentInstance().
    						getExternalContext().getSessionMap().get("errorRef");
    	
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("errorRef");
   
    	return errorRef;
    	
    }
    
    public boolean hasError(final String componentId) {
    	final List<FacesMessage> allErrors = FacesContext.getCurrentInstance().getMessageList();
    	final List<FacesMessage> componentErrors = FacesContext.getCurrentInstance().getMessageList(componentId);
    	
    	if (allErrors != null && !allErrors.isEmpty()) {
	    	if (componentErrors != null && !componentErrors.isEmpty()) {
	    		return true;
	    	}
	    	
	    	return false;
    	}
    	
    	return true;
    }
}
