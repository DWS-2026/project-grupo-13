package com.example.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
            // Necesario para permitir H2-console
            
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            .authorizeHttpRequests(authorize -> authorize
                // H2-console siempre permitido
                .requestMatchers("/h2-console/**").permitAll()

                // PÁGINAS PÚBLICAS
                .requestMatchers("/", "/Index").permitAll()
                .requestMatchers("/imagenes/**").permitAll()
                .requestMatchers("/image/**").permitAll()
                .requestMatchers("/CategoriesScreen").permitAll()
                .requestMatchers("/PromotionsScreen").permitAll()
                .requestMatchers("/producto/**").permitAll()
                .requestMatchers("/categoria/**").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/Login").permitAll()

                // PÁGINAS PRIVADAS
                .requestMatchers("/ShoppingCart").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/agregar-carrito").hasAnyRole("USER", "ADMIN")

                // ADMIN
                .requestMatchers("/Admin/**").hasRole("ADMIN")
                .requestMatchers("/AdminProduct/**").hasRole("ADMIN")
                .requestMatchers("/AdminUser/**").hasRole("ADMIN")
                .requestMatchers("/AdminCategories/**").hasRole("ADMIN")

                // Cualquier otra ruta → permitida
                .anyRequest().permitAll()
            )

            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureUrl("/Login?error")
                .defaultSuccessUrl("/Index", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/Index")
                .permitAll()
            );

        return http.build();
    }
}
