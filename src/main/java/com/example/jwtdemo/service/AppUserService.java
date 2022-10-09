package com.example.jwtdemo.service;

import com.example.jwtdemo.model.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> saveAllUsers(List<AppUser> appUsers);
    AppUser saveUser(AppUser appUser);
    AppUser getUserByUserName(String userName);
    List<AppUser> getAllUsers();
}
