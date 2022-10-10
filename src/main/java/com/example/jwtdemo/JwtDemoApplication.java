package com.example.jwtdemo;

import com.example.jwtdemo.model.AppUser;
import com.example.jwtdemo.model.UserRole;
import com.example.jwtdemo.service.AppUserService;
import com.example.jwtdemo.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class JwtDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(AppUserService appUserService, UserRoleService userRoleService) {
        return args -> {
            var roleNormal = UserRole.builder()
                    .roleName("NORMAL")
                    .build();
            var roleAdmin = UserRole.builder()
                    .roleName("ADMIN")
                    .build();
            userRoleService.saveAllRoles(List.of(roleNormal, roleAdmin));

            var userA = AppUser.builder()
                    .userSn(UUID.randomUUID())
                    .name("Ed")
                    .userName("Ed123")
                    .password("pwd")
                    .build();
            var userB = AppUser.builder()
                    .userSn(UUID.randomUUID())
                    .name("Mary")
                    .userName("Mary123")
                    .password("pwd")
                    .build();
            appUserService.saveAllUsers(List.of(userA, userB));

            userRoleService.addRoleToUser(userA.getUserName(), roleAdmin.getRoleName());
            userRoleService.addRoleToUser(userA.getUserName(), roleNormal.getRoleName());
            userRoleService.addRoleToUser(userB.getUserName(), roleNormal.getRoleName());
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
