package com.example.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import com.example.dto.CreateUser;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, BCryptPasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Student> getByUserName(String username) {
        return studentRepository.getByUserName(username);
    }

    public Student createUser(CreateUser request) {
        Student newStudent = Student.builder()
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
        return studentRepository.save(newStudent);
    }
}