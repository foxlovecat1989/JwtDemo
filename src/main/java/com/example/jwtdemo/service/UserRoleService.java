package com.example.jwtdemo.service;

import com.example.jwtdemo.model.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole saveRole(UserRole userRole);
    List<UserRole> saveAllRoles(List<UserRole> userRoles);
    void addRoleToUser(String username, String roleName);
}
