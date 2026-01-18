package com.zosh.service.impl;

import com.zosh.configuration.JwtProvider;
import com.zosh.exceptions.UserException;
import com.zosh.modal.User;
import com.zosh.payload.dto.UserDto;
import com.zosh.payload.response.AuthResponse;
import com.zosh.repository.UserRepository;
import com.zosh.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new UserException("email id alreday registered ");
        }
        return null;
    }

    @Override
    public AuthResponse login(UserDto userDto) {
        return null;
    }
}
