package com.vorobeyyyyyy.openchat.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vorobeyyyyyy.openchat.model.dto.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.LoginResponseDto;
import com.vorobeyyyyyy.openchat.service.UserService;

import lombok.AllArgsConstructor;

@RestController("/user")
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@GetMapping("/login")
	public LoginResponseDto login(
		@RequestBody LoginRequestDto loginRequestDto
	) {
		return userService.login(loginRequestDto);
	}
}
