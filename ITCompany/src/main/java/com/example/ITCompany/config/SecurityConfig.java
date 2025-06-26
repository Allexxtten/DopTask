package com.example.ITCompany.config;

import com.example.ITCompany.entity.Employee;
import com.example.ITCompany.repository.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error").permitAll()
                        .requestMatchers("/projects").permitAll()
                        .requestMatchers("/projects/save", "/projects/edit/**")
                        .hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/projects/create", "/projects/delete/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers("/employees/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/departments/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/welcome", true)
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(EmployeeRepository employeeRepository) {
        return email -> {
            Employee employee = employeeRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String role = "ROLE_" + employee.getPost().getAccessRights().getRightName().toUpperCase();
            return new User(
                    employee.getEmail(),
                    employee.getPassword(),
                    List.of(new SimpleGrantedAuthority(role))
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}