package com.example.jwtdemo.controller;

import com.example.jwtdemo.model.AppUser;
import com.example.jwtdemo.service.AppUserService;
import com.example.jwtdemo.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class AppUserController {
    private final AppUserService appUserService;
    private final UserRoleService userRoleService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        var appUsers = appUserService.getAllUsers();

        return ResponseEntity.ok().body(appUsers);
    }

    @GetMapping(value = "/{userName}")
    public ResponseEntity<AppUser> getUser(@PathVariable("userName") String userName) {
        var appUser = appUserService.getUserByUserName(userName);

        return ResponseEntity.ok().body(appUser);
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody AppUser appUser) {
        var newUser = appUserService.saveUser(appUser);
        var uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/addUser")
                .toUriString());

        return ResponseEntity.created(uri).body(newUser);
    }


    @PostMapping(value = "/{userName}/{roleName}")
    public ResponseEntity<?> addRoleToUser(
            @PathVariable("userName") String userName, @PathVariable("roleName") String roleName) {
        userRoleService.addRoleToUser(userName, roleName);

        return ResponseEntity.ok().build();
    }

}
