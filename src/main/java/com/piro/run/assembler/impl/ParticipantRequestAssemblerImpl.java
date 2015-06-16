package com.piro.run.assembler.impl;

import com.piro.run.assembler.ParticipantRequestAssembler;
import com.piro.run.dao.ParticipantRepository;
import com.piro.run.dao.ParticipantRequestRepository;
import com.piro.run.dao.UserRepository;
import com.piro.run.dto.ParticipantRequestDto;
import com.piro.run.dto.ParticipantResultDto;
import com.piro.run.entity.Participant;
import com.piro.run.entity.ParticipantRequest;
import com.piro.run.entity.User;
import org.springframework.beans.factory.annotation.Required;

/**
 * Created by ppirovski on 6/12/15. In Code we trust
 */
public class ParticipantRequestAssemblerImpl extends BaseAssembler<ParticipantRequest,ParticipantRequestDto> implements ParticipantRequestAssembler {

    ParticipantRepository participantRepository;
    UserRepository userRepository;

    @Override
    public ParticipantRequest toEntity(ParticipantRequestDto dto) {

        ParticipantRequest entity = new ParticipantRequest();

        entity.setId(dto.getId());
        Participant participant = participantRepository.findOne(dto.getParticipantId());
        User user = userRepository.findOne(dto.getUsername());

        return entity;
    }

    @Override
    public ParticipantRequestDto toDto(ParticipantRequest entity) {
        ParticipantRequestDto dto = new ParticipantRequestDto();

        dto.setId(entity.getId());
        dto.setParticipantId(entity.getParticipant().getId());
        dto.setParticipantName(entity.getParticipant().getName());
        dto.setUsername(entity.getUser().getUsername());
        dto.setUserEmail(entity.getUser().getEmail());

        return dto;
    }



    @Required
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setParticipantRepository(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }
}
