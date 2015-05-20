package com.piro.run.service;

import com.piro.run.dto.UserDto;

/**
 * Created by ppirovski on 5/19/15. In Code we trust
 */
public interface UserService {

    UserDto findUser(String username);

    UserDto createUser(UserDto forCreate);

    void sendConfirmEmail(String username, String url, String to);

    boolean activate(String uId);
}
