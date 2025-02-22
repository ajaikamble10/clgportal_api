package com.aj.clgportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aj.clgportal.dto.JwtAuthResponse;
import com.aj.clgportal.dto.LoginDto;
import com.aj.clgportal.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authServ;

	// build Login REST API
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto,HttpServletRequest request) {
		String token = authServ.login(loginDto);
		String authority=authServ.getAuthority(loginDto);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setLoginId(loginDto.getUsernameOrEmail());
		jwtAuthResponse.setAuthority(authority);
		HttpSession session=request.getSession();
		session.setAttribute("usernameoremail",loginDto.getUsernameOrEmail());
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
}
