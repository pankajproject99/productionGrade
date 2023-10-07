package com.example.productionGrade.security.repository;

import com.example.productionGrade.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Since email is unique, we'll find users by email
    Optional<Role> findByName(String name);
}
