package com.invoiceservice.security.login.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(	name = "USERS")
public class User {
    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @NotNull
    @Column(unique = true, name="USER_NAME")
    private String username;

    @NotNull
    @Column(name="PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();

    @NotNull
    @Column(name="DATE_ACCOUNT_CREATED")
    private LocalDate dateAccountCreated;

    @Column(name="DATE_LAST_LOGIN")
    private LocalDate dateLastLogin;

}