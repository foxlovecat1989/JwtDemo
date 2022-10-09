package com.example.jwtdemo.service;

import com.example.jwtdemo.model.AppUser;
import com.example.jwtdemo.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;

    @Override
    public List<AppUser> saveAllUsers(List<AppUser> appUsers) {
        log.info("Saving all users: {}", appUsers);
        return appUserRepository.saveAll(appUsers);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving user: {}", appUser);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser getUserByUserName(String userName) {
        var appUser = appUserRepository.findAppUserByUserName(userName);
        log.info("Fetching user: {}", appUser);
        return appUser;
    }

    @Override
    public List<AppUser> getAllUsers() {
        List<AppUser> appUsers = appUserRepository.findAll();
        log.info("Fetching all users: {}", appUsers);
        return appUsers;
    }
}
