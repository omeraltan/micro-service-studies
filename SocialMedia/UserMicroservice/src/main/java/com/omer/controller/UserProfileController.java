package com.omer.controller;

import com.omer.document.UserProfile;
import com.omer.dto.request.CreateUserRequestDto;
import com.omer.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.omer.config.RestApis.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERPROFILE)
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(CREATE_USER)
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDto dto) {
        userProfileService.createUser(dto);
        return ResponseEntity.ok(true);
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<UserProfile>> getAll() {
        return ResponseEntity.ok(userProfileService.getAll());
    }

    @GetMapping("/upper-name")
    public ResponseEntity<String> upperName(String name) {
        return ResponseEntity.ok(userProfileService.upperName(name));
    }

}
