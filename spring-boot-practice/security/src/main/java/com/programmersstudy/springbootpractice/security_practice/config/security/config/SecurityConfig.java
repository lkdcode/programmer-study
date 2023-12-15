package com.programmersstudy.springbootpractice.security_practice.config.security.config;

import com.programmersstudy.springbootpractice.security_practice.domain.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    UserDetailsService authentication() {
        Users lkdcode = Users.builder()
                .email("lkdcode@email.com")
                .password(passwordEncoder.encode("password123"))
                .grade("GOLD")
                .role("ADMIN")
                .build();

        Users another = Users.builder()
                .email("another@email.com")
                .password(passwordEncoder.encode("password123"))
                .grade("SILVER")
                .role("ADMIN")
                .build();

        // TODO : BadCredentialsException handlingf
        System.out.println(lkdcode.getEmail() + " " + lkdcode.getPassword());
        System.out.println(another.getEmail() + " " + another.getPassword());
        return new InMemoryUserDetailsManager(lkdcode, another);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .antMatchers("/security/admin").hasAuthority("ADMIN")
                .antMatchers("/security/gold").hasAuthority("GOLD")
//                .requestMatchers("/security/admin").hasAuthority("ADMIN") Boot 3.0.2ver
//                .requestMatchers("/security/gold").hasAuthority("GOLD") Boot 3.0.2ver
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}
