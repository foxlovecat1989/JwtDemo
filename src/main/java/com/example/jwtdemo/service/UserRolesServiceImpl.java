package com.example.jwtdemo.service;

import com.example.jwtdemo.model.UserRole;
import com.example.jwtdemo.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserRolesServiceImpl implements UserRoleService{
    private final UserRoleRepository userRoleRepository;
    private final AppUserService appUserService;

    @Override
    public UserRole saveRole(UserRole userRole) {
        log.info("Saving role: {}", userRole);
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> saveAllRoles(List<UserRole> userRoles) {
        log.info("Saving roles: {}", userRoles);
        return userRoleRepository.saveAll(userRoles);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role: {} to user: {}", roleName, username);
        var userRole = userRoleRepository.findByRoleName(roleName);
        var appUser = appUserService.getUserByUserName(username);
        appUser.getUserRoles().add(userRole);
    }
}
