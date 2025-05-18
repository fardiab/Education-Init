package com.example.service;

import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public UserDetailsService(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.debug("Attempting to load user by username: {}", username);
            return studentService.getByUserName(username)
                .map(student -> (UserDetails) student)
                .or(() -> teacherService.getByUserName(username).map(teacher -> (UserDetails) teacher))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        } catch (Exception e) {
            log.error("Exception in loadUserByUsername for username: " + username, e);
            throw new InternalAuthenticationServiceException("Internal error during user lookup", e);
        }
    }

}
