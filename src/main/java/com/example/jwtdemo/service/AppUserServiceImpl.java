package com.example.jwtdemo.service;

import com.example.jwtdemo.model.AppUser;
import com.example.jwtdemo.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var appUser = Optional.ofNullable(
                appUserRepository.findAppUserByUserName(username)).orElseThrow(() -> {
            var errorMsg = String.format("AppUser Not Found by username: %s", username);
            log.error(errorMsg);
            return new UsernameNotFoundException(errorMsg);
        });
        log.info("AppUser Found in the database: {}", appUser);
        Collection<SimpleGrantedAuthority> authorities = appUser.getUserRoles().stream()
                .map(role -> {
                    var roleName = role.getRoleName();
                    return new SimpleGrantedAuthority(roleName);
                }).toList();

        return new org.springframework.security.core.userdetails.User(
                appUser.getUserName(), appUser.getPassword(), authorities);
    }

    @Override
    public List<AppUser> saveAllUsers(List<AppUser> appUsers) {
        log.info("Saving all users: {}", appUsers);
        appUsers.forEach(appUser -> {
            var encodePassword = passwordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodePassword);
        });

        return appUserRepository.saveAll(appUsers);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving user: {}", appUser);
        var encodePassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePassword);

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
