package AutoMobile.Cars.Security;


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
import org.springframework.web.cors.CorsConfigurationSource;

import AutoMobile.Cars.Auth.JwtFilter;
import AutoMobile.Cars.Auth.PrincpleUser;
import AutoMobile.Cars.Excrptionfold.CustomException;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    JwtFilter jwtFilter;
    PrincpleUser princpleUser;
    CorsConfigurationSource configurationSource;
    CustomLogout customLogout;

    public SecurityConfig(JwtFilter jwtFilter, PrincpleUser princpleUser, CorsConfigurationSource configurationSource,
            CustomLogout customLogout) {
        this.jwtFilter = jwtFilter;
        this.princpleUser = princpleUser;
        this.configurationSource = configurationSource;
        this.customLogout = customLogout;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                // <-- This must be enabled the Custom CROSS-ORIGIN-RESOURCE-SHARING
                // .cors(c -> c.configurationSource(configurationSource))
                // This used to we don't need login and logout
                .csrf(c -> c.disable())
                // It allow the API without login
                .authorizeHttpRequests(req -> req.requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/login",
                        "/user/add",
                        "/payments/**","/cart/**","/user/**",
                        "/cars/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/payments/**","/cart/**","/user/**").hasAnyRole("ADMIN","USER")
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
                        l -> l.logoutUrl("/logout")
                                .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                                .addLogoutHandler(customLogout));
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
}