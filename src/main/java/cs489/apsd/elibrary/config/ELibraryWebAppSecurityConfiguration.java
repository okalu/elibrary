package cs489.apsd.elibrary.config;

import cs489.apsd.elibrary.service.impl.ElibraryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ELibraryWebAppSecurityConfiguration {
    private UserDetailsService elibraryUserDetailsService;

    @Autowired
    public ELibraryWebAppSecurityConfiguration(ElibraryUserDetailsService elibraryUserDetailsService) {
        this.elibraryUserDetailsService = elibraryUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    .requestMatchers("/resources/static/**").permitAll()
                                    .requestMatchers("/images/**").permitAll()
                                    .requestMatchers("/css/**").permitAll()
                                    .requestMatchers("/").permitAll()
                                    .requestMatchers("/elibrary").permitAll()
                                    .requestMatchers("/elibrary/public/**").permitAll()
                                    .requestMatchers("/elibrary/secured/services/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/elibrary/secured/services/librarian/**").hasRole("LIBRARIAN")
                                    .requestMatchers("/elibrary/secured/services/libmember/**").hasRole("LIBMEMBER")
                                    .anyRequest().authenticated();
                        }
                )
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/elibrary/public/login")
                        .defaultSuccessUrl("/elibrary/secured/home")
                        .failureUrl("/elibrary/public/login?error").permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/elibrary/public/logout"))
                        .logoutSuccessUrl("/elibrary/public/login?logout").permitAll())
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(elibraryUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
