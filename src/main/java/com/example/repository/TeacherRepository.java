package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{

	@Query(value = "SELECT * FROM edu.teacher WHERE username = :username", nativeQuery = true)
	Optional<Teacher> getByUserName(@Param("username") String username);
	
}
