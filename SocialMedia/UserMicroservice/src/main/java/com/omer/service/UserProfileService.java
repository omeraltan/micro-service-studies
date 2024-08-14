package com.omer.service;

import com.omer.document.UserProfile;
import com.omer.dto.request.CreateUserRequestDto;
import com.omer.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;


    public void createUser(CreateUserRequestDto dto) {
        repository.save(UserProfile.builder()
                .authId(dto.getAuthId())
                .userName(dto.getUsername())
                .email(dto.getEmail())
                .build());
    }

    public List<UserProfile> getAll() {
        return repository.findAll();
    }
}
