package AutoMobile.Cars.Security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import AutoMobile.Cars.Auth.JwtFilter;
import AutoMobile.Cars.Auth.PrincpleUser;
import AutoMobile.Cars.Excrptionfold.CustomException;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    JwtFilter jwtFilter;
    PrincpleUser princpleUser;

    @Autowired
    CorsConfigurationSource configurationSource;
    @Autowired
    CustomLogout customLogout;

    public SecurityConfig(JwtFilter jwtFilter, PrincpleUser princpleUser) {
        this.jwtFilter = jwtFilter;
        this.princpleUser = princpleUser;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                // <-- This must be enabled the Custom CROSS-ORIGIN-RESOURCE-SHARING
                .cors(c -> c.configurationSource(configurationSource))
                // This used to we don't need login and logout
                .csrf(c -> c.disable())
                // It allow the API without login
                .authorizeHttpRequests(req -> req.requestMatchers(
                        "/swagger-ui/**",
                        "/user/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/user",
                        "/user/add",
                        "/login",
                        "/payments/create-payment",
                        // "/cart/**",
                        "/cars/**").permitAll()
                        // It is used to enable authentication and we acces the api using login or token
                        .anyRequest().authenticated())
                // It enable "OpenSource" login like google and github account
                // .oauth2Login(Customizer.withDefaults())
                .sessionManagement(temp -> temp.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // It is enable the basic login like alert message tap
                .httpBasic(Customizer.withDefaults())
                // It is enable the login form
                // .formLogin(Customizer.withDefaults())
                // It is used add our filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // It allow logout using this 'logout' Api
                .logout(
                    l->l.logoutUrl("/logout")
                    .logoutSuccessHandler((req, res, auth) ->
                            res.setStatus(HttpServletResponse.SC_OK))
                            .addLogoutHandler(customLogout)
                );
        return security.build();
    }

    @Bean
    public AuthenticationProvider authProvider() throws CustomException {
        try {
            // It DaoAuthenticationProvider to use our custom princpleUser
            // (UserDetailsService)
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider(princpleUser);
            // You’re telling the provider to use BCrypt with strength 8 to check passwords.
            provider.setPasswordEncoder(new BCryptPasswordEncoder(8));
            return provider;
        } catch (Exception e) {
            throw new CustomException(e);
        }

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws CustomException {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // This object allows you to define which origins, methods, headers, etc., are
        // allowed to access the backend.
        CorsConfiguration config = new CorsConfiguration();
        // only requests from http://localhost:5173 (likely your frontend in
        // development) are allowed.
        // You could use "*" for all origins, but it's not allowed when allowCredentials
        // is true.
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        // config.setExposedHeaders(List.of("Authorization", "Content-Type")); //
        // Optional but safe
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}