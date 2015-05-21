package com.piro.run.service.impl;

import com.piro.run.assembler.UserAssembler;
import com.piro.run.assembler.impl.UserAssemblerImpl;
import com.piro.run.dao.UserRepository;
import com.piro.run.dto.UserDto;
import com.piro.run.entity.User;
import com.piro.run.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ppirovski on 5/19/15. In Code we trust
 */
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private UserAssembler userAssembler;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;

    private String from;
    private String activateSubject;
    private String activateMessage;
    private String passwordSubject;
    private String passwordMessage;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public UserDto findUser(String username) {

        User user = userRepository.findOne(username);
        if(user == null){
            return null;
        }
        return userAssembler.toDto(user);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto forCreate) {

        forCreate.setEnabled(false);
        forCreate.setPassword(passwordEncoder.encode(forCreate.getPassword()));
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_USER");
        forCreate.setAuthorities(authorities);

        String confirm = UUID.randomUUID().toString();
        forCreate.setConfirm(confirm);


        LOG.debug("creating user with username = "+forCreate.getUsername());

        User created = userRepository.save(userAssembler.toEntity(forCreate));
        return userAssembler.toDto(created);
    }

    @Override
    public void sendConfirmEmail(String username, String url, String to) {
        LOG.debug("sending message to user with username: " + username);

        String msg = null;
        String subject = null;
        try {
            msg = new String(this.activateMessage.getBytes("ISO-8859-1"), "UTF-8");
            subject = new String(this.activateSubject.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        msg = msg + url;
        mailMessage.setText(msg);
        mailMessage.setFrom(this.from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);

        try {
            mailSender.send(mailMessage);
        }
        catch (Exception e){
            LOG.warn("failed sending email: "+e.getMessage() );
        }
    }

    @Override
    @Transactional
    public boolean activate(String uId) {

        User user = userRepository.findByConfirm(uId);
        if(user == null){
            return false;
        }

        LOG.debug("Enable user with username = "+user.getUsername());
        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public String editUser(UserDto forEdit, String newEmail, String newPassword, String oldPassword) {

        if(newEmail.equals(forEdit.getEmail()) && StringUtils.isEmpty(newPassword)){
            userRepository.save(userAssembler.toEntity(forEdit));
            return "";
        }
        if(StringUtils.isEmpty(oldPassword)){
            return "empty password";
        }
        if(passwordEncoder.matches(oldPassword, forEdit.getPassword())){
            forEdit.setPassword(passwordEncoder.encode(newPassword));
            forEdit.setEmail(newEmail);
            userRepository.save(userAssembler.toEntity(forEdit));
            return "";
        }

        return "wrong password";
    }

    @Override
    @Transactional
    public boolean generateAndSendPassword(String username, String to) {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(to)){
            return false;
        }

        User user = userRepository.findOne(username);
        if(user == null || !user.getEmail().equals(to)){
            return false;
        }

        String random = UUID.randomUUID().toString().replace("-", "_");
        String newPass = random.substring(0,6);

        user.setPassword(passwordEncoder.encode(newPass));

        LOG.debug("saving new generated password: " + username);
        userRepository.save(user);

        LOG.debug("sending new password to user with username: " + username);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String msg = null;
        String subject = null;
        try {
            msg = new String(this.passwordMessage.getBytes("ISO-8859-1"), "UTF-8");
            subject = new String(this.passwordSubject.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        msg = msg + " " + username + " : " + newPass;
        mailMessage.setText(msg);
        mailMessage.setFrom(this.from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);

        try {
            mailSender.send(mailMessage);
        }
        catch (Exception e){
            LOG.warn("failed sending email: "+e.getMessage() );
            return  false;
        }
        return true;
    }

    @Required
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Required
    public void setUserAssembler(UserAssembler userAssembler) {
        this.userAssembler = userAssembler;
    }

    @Required
    public void setEncoder(PasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setFrom(String from) {
        this.from = from;
    }

    @Required
    public void setActivateSubject(String activateSubject) {
        this.activateSubject = activateSubject;
    }

    @Required
    public void setActivateMessage(String activateMessage) {
        this.activateMessage = activateMessage;
    }

    @Required
    public void setPasswordSubject(String passwordSubject) {
        this.passwordSubject = passwordSubject;
    }

    @Required
    public void setPasswordMessage(String passwordMessage) {
        this.passwordMessage = passwordMessage;
    }
}
