package com.piro.run.web.beans;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by ppirovski on 4/24/15. In Code we trust
 */
public class SessionBean {

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
}
