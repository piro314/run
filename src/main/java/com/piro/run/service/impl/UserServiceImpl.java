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
    private String subject;
    private String message;

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

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String msg = this.message + url;
        mailMessage.setText(msg);
        mailMessage.setFrom(this.from);
        mailMessage.setTo(to);
        mailMessage.setSubject(this.subject);

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
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Required
    public void setMessage(String message) {
        this.message = message;
    }
}
