package com.example.jwtdemo.controller;

import com.example.jwtdemo.model.UserRole;
import com.example.jwtdemo.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping(value = "/addRole")
    public ResponseEntity<UserRole> addRole(@RequestBody UserRole userRole) {
        var newRole = userRoleService.saveRole(userRole);
        var uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/addRole").toUriString());

        return ResponseEntity.created(uri).body(newRole);
    }
}
