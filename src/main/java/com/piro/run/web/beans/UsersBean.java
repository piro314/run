package com.piro.run.web.beans;

import com.piro.run.dto.UserDto;
import com.piro.run.service.UserService;
import com.piro.run.service.impl.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by ppirovski on 5/19/15. In Code we trust
 */
public class UsersBean implements Serializable {

    public final long serialVersionUID = 2638543344826414128L;

    final static String ACTIVATE_PAGE  = "public/login.jsf?uId=";
    final static String REDIRECT_PAGE  = "/public/accountCreated.jsf";

    private transient UserService userService;

    private UserDto forCreate;
    private UserDto currentUser;

    private String newName;
    private String newEmail;
    private String oldPassword;
    private String newPassword;

    private String forgottenUser;
    private String forgottenEmail;


    public UsersBean(UserService userService) {
        this.userService = userService;
        forCreate = new UserDto();
    }


    public UserDto getForCreate() {
        return forCreate;
    }

    public UserDto getCurrentUser() {
        if(currentUser == null){
            String username = this.getCurrentUserName();
            currentUser = userService.findUser(username);
        }
        return currentUser;
    }

    public String getNewName() {
        if(newName == null){
            newName = getCurrentUser().getName();
        }
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewEmail() {
        if(newEmail == null){
            newEmail = getCurrentUser().getEmail();
        }
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getForgottenUser() {
        return forgottenUser;
    }

    public void setForgottenUser(String forgottenUser) {
        this.forgottenUser = forgottenUser;
    }

    public String getForgottenEmail() {
        return forgottenEmail;
    }

    public void setForgottenEmail(String forgottenEmail) {
        this.forgottenEmail = forgottenEmail;
    }

    public void checkUsername(){
        UserDto user = userService.findUser(forCreate.getUsername());

        if(user != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,"Username is already used", null));
        }

    }

    public void checkPassword(){
        if(forCreate.getPassword() == null || forCreate.getRepeatPassword() == null || getForCreate().getRepeatPassword().isEmpty()){
            return;
        }
        if(!forCreate.getPassword().equals(forCreate.getRepeatPassword())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,"Repeat Password does not match password", null));
        }
    }

    public void createAccount() {
        boolean errors = false;
        FacesContext ctx = FacesContext.getCurrentInstance();
        UserDto user = userService.findUser(forCreate.getUsername());

        if(user != null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is already used", null));
            errors = true;
        }
        if(!forCreate.getPassword().equals(forCreate.getRepeatPassword())){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Repeat Password does not match password", null));
            errors = true;
        }
        if(errors){
            return;
        }

        UserDto created = userService.createUser(forCreate);


        ExternalContext extContext = ctx.getExternalContext();
        String url = getBaseUrl(extContext)+ACTIVATE_PAGE+created.getConfirm();

        userService.sendConfirmEmail(created.getUsername(), url, created.getEmail());

        String redirectUrl = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, REDIRECT_PAGE));

        try {
            extContext.redirect(redirectUrl);
        } catch (IOException e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not send email for account confirmation", null));
        }
    }

    public String activate(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext externalContext = ctx.getExternalContext();
        Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();
        String uId = parameterMap.get("uId");


        if(!StringUtils.isEmpty(uId)){
            if(userService.activate(uId)){
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Успешна Активация!", null));
            }
        }
        return "";

    }

    public void editUser(){

        currentUser.setName(getNewName());
        String result = userService.editUser(getCurrentUser(), getNewEmail(), getNewPassword(), getOldPassword());
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(StringUtils.isEmpty(result)){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Успешна промяна!", null));

        }
        else {
            if (result.equals("empty password") || result.equals("wrong password")) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Грешна парола", null));
            }
        }

        currentUser = null;
        newEmail = null;
        newName = null;
        newPassword = null;
        oldPassword = null;


    }


    public boolean checkChange(){
        return checkEmailOrPasswordChanged() || !getNewName().equals(getCurrentUser().getName());
    }

    public boolean checkEmailOrPasswordChanged(){
        if(!getNewEmail().equals(getCurrentUser().getEmail())){
            return true;
        }
        if(!StringUtils.isEmpty(newPassword)){
            return true;
        }
        return false;
    }

    public void forgottenPassword(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        if(userService.generateAndSendPassword(getForgottenUser(), getForgottenEmail())){

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Изпратена е нова парола на вашия email", null));
        }
        else {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Проблем с изпращането на новата ви парола. Моля, пробвайте по-късно", null));
        }
    }

    private String getBaseUrl(ExternalContext extContext){
        HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return baseURL;
    }

    private String getCurrentUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == null){
            throw new SecurityException("not logged in");
        }
        if(principal instanceof String){
           return (String)principal;
        }
        if (principal instanceof UserDetails){
            return ((UserDetails)principal).getUsername();
        }
        throw new SecurityException("cannot find principal");
    }


}
