package com.vorobeyyyyyy.openchat.web;

import com.vorobeyyyyyy.openchat.model.dto.LoginRequestDto;
import com.vorobeyyyyyy.openchat.model.dto.SendCodeRequest;
import com.vorobeyyyyyy.openchat.model.dto.TokenDto;
import com.vorobeyyyyyy.openchat.model.dto.UserDto;
import com.vorobeyyyyyy.openchat.service.PhoneVerificationService;
import com.vorobeyyyyyy.openchat.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@CrossOrigin
public class UserController {

	private UserService userService;

	private PhoneVerificationService phoneVerificationService;

	@PostMapping("/login")
	public TokenDto login(
		@RequestBody LoginRequestDto loginRequestDto
	) {
		return userService.login(loginRequestDto);
	}

	@PostMapping("/send-code")
	public void register(
		@RequestBody @Valid SendCodeRequest sendCodeRequest
	) {
		phoneVerificationService.sendCode(sendCodeRequest);
	}

	@GetMapping("/me")
	public UserDto me() {
		return userService.getMyself();
	}
}
