package com.example.spring.security.simpleprivileges.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Spring Beans] ==============================

    // -------------------- [Public Spring Beans] --------------------

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    // -------------------- [Protected Methods] --------------------

    // -------------------- [Public Methods] --------------------

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        // @formatter:off
        auth.inMemoryAuthentication()
            .withUser("user1").password(passwordEncoder().encode("user1Pass")).authorities("ADMIN", "PRIVILEGE1", "PRIVILEGE2", "PRIVILEGE4").and()
            .withUser("user2").password(passwordEncoder().encode("user2Pass")).authorities("ADMIN");
//            .withUser("user1").password(passwordEncoder().encode("user1Pass")).authorities("ADMIN").and()
//            .withUser("user2").password(passwordEncoder().encode("user2Pass")).authorities("PRIVILEGE2");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
                // .antMatcher("/test/**")   // The current HttpSecurity instance does handle only these URLs.
                .authorizeRequests(authorize ->
                        authorize
                                .mvcMatchers(HttpMethod.GET, "/hello")
                                    .access("hasAuthority('ADMIN') or (hasAuthority('PRIVILEGE1') and hasAuthority('PRIVILEGE2') and hasAnyAuthority('PRIVILEGE3', 'PRIVILEGE4'))")
                                .mvcMatchers(HttpMethod.GET, "/hello/{id}")
                                    .access("hasAuthority('ADMIN') or hasAuthority('PRIVILEGE1')")
                                )
                .authorizeRequests(authorize ->
                        authorize.antMatchers("/**").denyAll())   // Deny all other requests. Caution: has to be specified after all other matchers!!!
                .httpBasic();
        // @formatter:on
    }
}
