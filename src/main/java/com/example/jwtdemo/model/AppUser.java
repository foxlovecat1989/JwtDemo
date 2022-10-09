package com.example.jwtdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "App_User")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sn;
    private UUID userSn;
    private String name;
    private String userName;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "app_user_user_role",
            joinColumns = @JoinColumn(
                    name = "user_sn",
                    foreignKey = @ForeignKey(name = "appUser_userRole_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "role_sn",
                    foreignKey = @ForeignKey(name = "userRole_appUser_id_fk")))
    private List<UserRole> userRoles;
}
