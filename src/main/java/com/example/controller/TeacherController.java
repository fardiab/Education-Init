package com.example.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.TeacherService;

import lombok.extern.slf4j.Slf4j;

import com.example.dto.AuthRequest;
import com.example.dto.CreateUser;
import com.example.entity.Teacher;
import com.example.service.JwtService;


@RestController
@RequestMapping("/auth/teacher")
@Slf4j
public class TeacherController {
	
	private final TeacherService teacherService;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public TeacherController(TeacherService teacherService, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.teacherService = teacherService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/add-new-teacher")
	public Teacher addUser(@RequestBody CreateUser reqeust) {
		return teacherService.createUser(reqeust);
	}
	
	@PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }
	
	@GetMapping("/admin")
	public String getUserString() {
		return "Hello, I am User";
	}
	
	@GetMapping("/welcome")
    public String welcome() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username =  authentication.getName();
        return "Hello " + username;
    }

}
