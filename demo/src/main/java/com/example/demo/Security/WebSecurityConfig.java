package com.example.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import com.example.demo.Security.jwt.JwtRequestFilter;
import com.example.demo.Security.jwt.JwtTokenProvider;
import com.example.demo.Security.jwt.UnauthorizedHandlerJwt;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Autowired
        private RepositoryUserDetailsService userDetailService;

        @Autowired
        private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailService);
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        @Order(1)
        public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .securityMatcher("/api/**")
                .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http
                .authorizeHttpRequests(authorize -> authorize
                        // PRIVATE ENDPOINTS
                        // Images
                        .requestMatchers(HttpMethod.POST, "/api/v1/images/*/images").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/images/*/images").hasRole("ADMIN")
                        
                        // Methods for images on entities
                        //Product
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/*/images").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/api/v1/products/*/images").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/*/images/*").hasRole("ADMIN")

                        //User
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/*/image").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*/image").hasRole("ADMIN")

                        //Category
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/*/image").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/api/v1/categories/*/image").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/*/image").hasRole("ADMIN")

                        ///////////////////////////////////////////////////////////////////////////
                        ///////////////////////////////////////////////////////////////////////////

                        // Categories
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")
                        // Products
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")
                        // Reviews
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/*/reviews", "/api/products/*/reviews/").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/*/reviews/*").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/*/reviews/*").hasRole("USER")
                        // Users
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
                        // Files
                        .requestMatchers(HttpMethod.POST, "/api/documents/users/*/dni").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/documents/*/file").hasAnyRole("USER", "ADMIN")
                        // PUBLIC ENDPOINTS
                        .anyRequest().permitAll());

                // Disable Form login Authentication
                http.formLogin(formLogin -> formLogin.disable());

                // Disable CSRF protection (it is difficult to implement in REST APIs)
                http.csrf(csrf -> csrf.disable());

                // Enable Basic Authentication
                http.httpBasic(httpBasic -> httpBasic.disable()); //  Customizer.withDefaults()

                // Stateless session
                http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // Add JWT Token filter
                /*
                http.addFilterBefore(new JwtRequestFilter(userDetailService, jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
                */
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
                
                return http.build();
        }

    @Bean
    @Order(2)
    public SecurityFilterChain webfilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http

                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(authorize -> authorize
                        // H2-console siempre permitido
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/css/**", "/js/**", "/imagenes/**", "/image/**", "/*.css").permitAll()
                        .requestMatchers("/", "/Index").permitAll()
                        .requestMatchers("/imagenes/**").permitAll()
                        .requestMatchers("/image/**").permitAll()
                        .requestMatchers("/CategoriesScreen").permitAll()
                        .requestMatchers("/PromotionsScreen").permitAll()
                        .requestMatchers("/producto/**").permitAll()
                        .requestMatchers("/categoria/**").permitAll()
                        .requestMatchers("/register", "/Register").permitAll()
                        .requestMatchers("/login", "/Login").permitAll()
                        .requestMatchers("/registro", "/registro/**").permitAll()

                        .requestMatchers("/ShoppingCart").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/agregar-carrito/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/reviews/add").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .requestMatchers("/AdminProduct/**").hasRole("ADMIN")
                        .requestMatchers("/AdminUser/**").hasRole("ADMIN")
                        .requestMatchers("/AdminUser/promote/**").hasRole("ADMIN")
                        .requestMatchers("/AdminCategories/**").hasRole("ADMIN")
                        .requestMatchers("/ChangePassword").authenticated()

                        .requestMatchers("/user/profile/upload").authenticated()

                        // OpenAPI
			.requestMatchers("/v3/api-docs*/**").permitAll()
			.requestMatchers("/swagger-ui.html").permitAll()
			.requestMatchers("/swagger-ui/**").permitAll()

                        .anyRequest().authenticated()

                )

                .formLogin(formLogin -> formLogin
                        .loginPage("/Login")
                        .failureUrl("/Login?error")
                        .defaultSuccessUrl("/Index", true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/Index")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }
}
