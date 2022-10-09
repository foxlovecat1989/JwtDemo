package com.example.jwtdemo.repository;

import com.example.jwtdemo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query(value = "SELECT ur FROM User_Role ur WHERE ur.roleName = ?1")
    UserRole findByRoleName(String roleName);
}
