package com.piro.run.web.beans;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by ppirovski on 4/24/15. In Code we trust
 */
public class SessionBean implements Serializable{

    public final long serialVersionUID = 3314523456621114828L;

    private String username;
    private String password;

    private AuthenticationManager authenticationManager;

    public String getUserTheme(){

        if(isAdmin()){
            return "aristo";
        }

        return "sunny";
    }


    public boolean isLoggedIn(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == null){
            return false;
        }
        if(principal instanceof String){
            if(((String)principal).equals("anonymousUser")){
                return false;
            }
            return true;
        }
        if (principal instanceof UserDetails){
            if(((UserDetails)principal).getUsername().equals("anonymousUser")){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isAdmin(){
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().contains("ADMIN")){
                return true;
            }
        }
        return false;
    }

    public void login() throws IOException {

        try {
            Authentication request = new UsernamePasswordAuthenticationToken(this.getUsername(), this.getPassword());
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (AuthenticationException e) {
            String error = e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, error,""));
            return;
        }

        setPassword(null);

        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String redirect = isAdmin() ? "/admin/competitionsCRUD.jsf" : "/public/competitions.jsf";

        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, redirect));
        extContext.redirect(url);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Required
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
