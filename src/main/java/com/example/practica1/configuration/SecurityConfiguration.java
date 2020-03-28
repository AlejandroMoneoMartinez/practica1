package com.example.practica1.configuration;

import com.example.practica1.entity.Role;
import com.example.practica1.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    private static final String SALT = "salt";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //De menos a mas restrictivo
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                //.mvcMatchers("/users/**").hasAuthority(Role.ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin() //a login form is showed when no authenticated request
                //.loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/index")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                //.and()
                //.httpBasic()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me") //atención sin el rememberMeParameter no funciona!!!
                .tokenValiditySeconds(2419200) //28 days
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //necesario ponerlo así en vez de .logoutUrl("/logout") cuando .csrf() esta habilitado, ya que tendriamos que hacer un post en ese caso
                .logoutSuccessUrl("/logout")
                .deleteCookies("remember-me")
                .permitAll()
                .and()
                //.cors()
                //.and()
                .csrf().disable();
        http.headers().frameOptions().disable();
        //.ignoringAntMatchers("/sourcelist/**") //deshabilitamos la proteccion csrf solo en la api source
        //.and().requiresChannel().anyRequest().requiresSecure();
    }
}
