package com.example.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.CreateUser;
import com.example.entity.Teacher;
import com.example.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository teacherRepository, BCryptPasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Teacher> getByUserName(String username) {
        return teacherRepository.getByUserName(username);
    }

    public Teacher createUser(CreateUser request) {
        Teacher newTeacher = Teacher.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .age(request.age())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .accountNonLocked(true)
                .isEnabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .authorities(request.authorities())
                .build();
        return teacherRepository.save(newTeacher);
    }
}
