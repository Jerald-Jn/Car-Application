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

import AutoMobile.Cars.Auth.JwtFilter;
import AutoMobile.Cars.Auth.PrincpleUser;
import AutoMobile.Cars.Excrptionfold.CustomException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
    JwtFilter jwtFilter;
    PrincpleUser princpleUser;

    // @Autowired
    public SecurityConfig(JwtFilter jwtFilter,PrincpleUser princpleUser){
        this.jwtFilter=jwtFilter;
        this.princpleUser=princpleUser;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        System.out.println("SecurityConfig.securityFilterChain()");
        security
                // This used to we don't need login and logout 
                .csrf(c -> c.disable())
                // It allow the API without login
                .authorizeHttpRequests(req -> req.requestMatchers(
                        "/swagger-ui/**",
                        "/user/**",
                        "/v3/api-docs/**",
                        // "/swagger-resources/**",
                        "/swagger-ui.html",
                        // "/webjars/**",
                        "/car",
                        "/car/add",
                        "/login"
                        ).permitAll()
                        // It is used to enable authentication and we acces the api using login or token
                        .anyRequest().authenticated())
                // It enable "OpenSource" login like google and github account
                // .oauth2Login(Customizer.withDefaults())
                .sessionManagement(temp->temp.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // It is enable the basic login like alert message tap
                .httpBasic(Customizer.withDefaults())
                // It is enable the login form
                // .formLogin(Customizer.withDefaults())
                // It is used add our filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }

    

    @Bean
    public AuthenticationProvider authProvider() throws CustomException {
        try {
            System.out.println("SecurityConfig.authProvider()");
            //  It DaoAuthenticationProvider to use our custom princpleUser (UserDetailsService)
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
                System.out.println("SecurityConfig.authenticationManager()");
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}