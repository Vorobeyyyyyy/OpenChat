package com.vorobeyyyyyy.openchat.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vorobeyyyyyy.openchat.model.domain.User;
import com.vorobeyyyyyy.openchat.model.dto.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.LoginResponseDto;
import com.vorobeyyyyyy.openchat.repository.UserRepository;
import com.vorobeyyyyyy.openchat.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;


	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		User user = userRepository.findByUsernameAndPassword(loginRequestDto.getUsername(), passwordEncoder.encode(loginRequestDto.getPassword()))
			.orElseThrow(() -> new EntityNo)
		return null;
	}

}
