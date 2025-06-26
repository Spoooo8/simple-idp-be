package com.simpleidp.user_service.service;


import com.simpleidp.user_service.dto.AuthorityDTO;
import com.simpleidp.user_service.entity.Authority;
import com.simpleidp.user_service.entity.Users;
import com.simpleidp.user_service.repository.AuthorityRepository;
import com.simpleidp.user_service.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final UsersRepository usersRepository;



    public List<Authority> getAuthoritiesByUserId(Long userId) {
        return authorityRepository.findByUsersId(userId);
    }
}