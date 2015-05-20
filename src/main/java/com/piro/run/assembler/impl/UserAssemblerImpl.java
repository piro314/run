package com.piro.run.assembler.impl;

import com.piro.run.assembler.UserAssembler;
import com.piro.run.dto.UserDto;
import com.piro.run.entity.User;

/**
 * Created by ppirovski on 5/20/15. In Code we trust
 */
public class UserAssemblerImpl extends BaseAssembler<User, UserDto> implements UserAssembler {

    @Override
    public User toEntity(UserDto dto) {
        User entity = new User();

        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setEnabled(dto.isEnabled());
        entity.setAuthorities(dto.getAuthorities());
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setConfirm(dto.getConfirm());

        return entity;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();

        dto.setEnabled(entity.isEnabled());
        dto.setName(entity.getName());
        dto.setAuthorities(entity.getAuthorities());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setConfirm(entity.getConfirm());

        return dto;
    }
}
