package com.petclinic.petclinic.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
        http.authenticationProvider(authenticationProvider).authorizeHttpRequests(auth -> auth.requestMatchers("/", "/home", "/about", "/clinics", "/clinics/**").permitAll().requestMatchers("/login", "/register/**").permitAll().requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/clinic/**").hasRole("CLINIC").requestMatchers("/owner/**").hasRole("PET_OWNER").requestMatchers("/inquiries/**").hasAnyRole("CLINIC", "PET_OWNER").requestMatchers("/servlet/**").hasAnyRole("ADMIN", "CLINIC", "PET_OWNER").anyRequest().authenticated()).formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").successHandler(authenticationSuccessHandler()).failureHandler(authenticationFailureHandler()).permitAll()).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/?loggedOut").permitAll()).exceptionHandling(ex -> ex.accessDeniedPage("/error/403"));

        return http.build();
    }

    /**
     * Sends each role to the dashboard that matters to them right after login.
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = "/";
            for (var authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                if (role.equals("ROLE_ADMIN")) {
                    redirectUrl = "/admin/dashboard";
                    break;
                } else if (role.equals("ROLE_CLINIC")) {
                    redirectUrl = "/clinic/dashboard";
                    break;
                } else if (role.equals("ROLE_PET_OWNER")) {
                    redirectUrl = "/owner/dashboard";
                    break;
                }
            }
            response.sendRedirect(redirectUrl);
        };
    }

    /**
     * Distinguishes "pending/rejected clinic account" from a plain bad password.
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String reason = (exception instanceof DisabledException) ? "disabled" : "bad_credentials";
            response.sendRedirect("/login?error=" + reason);
        };
    }
}
