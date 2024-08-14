package com.omer.service;

import com.omer.dto.request.CreateUserRequestDto;
import com.omer.dto.request.RegisterRequestDto;
import com.omer.dto.response.LoginResponseDto;
import com.omer.entity.Auth;
import com.omer.manager.UserProfileManager;
import com.omer.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repository;
    private final UserProfileManager userProfileManager;

    public Auth register(RegisterRequestDto dto) {
        Auth auth = repository.save(Auth.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
            .build());
        userProfileManager.createUser(CreateUserRequestDto.builder()
                .authId(auth.getId())
                .email(auth.getEmail())
                .username(auth.getUsername())
            .build());
        return auth;
    }

    public Boolean login(LoginResponseDto dto) {
        return repository.existsByUsernameAndPassword(dto.getUserName(), dto.getPassword());
    }
}
