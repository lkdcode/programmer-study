package com.programmersstudy.springbootpractice.security_practice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_WRITE;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = READ_WRITE)
    @Column(nullable = false, unique = true)
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @JsonProperty(access = WRITE_ONLY)
    @Column(nullable = false)
    private String role;

    @JsonProperty(access = WRITE_ONLY)
    @Column(nullable = false)
    private String grade;

    @Builder
    public Users(Long id, String email, String password, String role, String grade) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.grade = grade;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role), new SimpleGrantedAuthority(this.grade));
    }

    @JsonProperty(access = WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
