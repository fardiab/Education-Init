package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query(value = "SELECT * FROM edu.student WHERE username = :username", nativeQuery = true)
	Optional<Student> getByUserName(@Param("username") String username);
	
}
