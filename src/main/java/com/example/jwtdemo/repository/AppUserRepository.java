package com.example.jwtdemo.repository;

import com.example.jwtdemo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query(value = "SELECT au FROM App_User au WHERE au.userName = ?1")
    AppUser findAppUserByUserName(String userName);
}
