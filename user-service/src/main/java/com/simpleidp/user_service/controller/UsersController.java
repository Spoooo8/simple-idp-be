package com.simpleidp.user_service.controller;

import com.simpleidp.user_service.dto.ClientDetailsDTO;
import com.simpleidp.user_service.dto.UserDetailsDTO;
import com.simpleidp.user_service.dto.UserInfoDTO;
import com.simpleidp.user_service.dto.UsersRequestDTO;
import com.simpleidp.user_service.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersRequestDTO> createUser(
            @RequestBody UsersRequestDTO request) {
        return ResponseEntity.ok(usersService.createUser(request));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getUser(){
        return ResponseEntity.ok(usersService.getUser());
    }

    @GetMapping("{clientId}/client")
    public ResponseEntity<List<UserDetailsDTO>> getClientDetails(@PathVariable Long clientId){
        return ResponseEntity.ok(usersService.getUserDetails(clientId));
    }
}
