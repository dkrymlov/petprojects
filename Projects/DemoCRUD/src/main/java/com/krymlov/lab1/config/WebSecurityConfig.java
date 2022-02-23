package com.krymlov.lab1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/category").permitAll()
                    .antMatchers("/category/details").permitAll()
                    .antMatchers("/item").permitAll()
                    .antMatchers("/brand").permitAll()
                    .antMatchers("/brand/details").permitAll()
                    .antMatchers("/seller").permitAll()
                    .antMatchers("/seller/details").permitAll()
                    .antMatchers("/about").permitAll()
                    .antMatchers("/category/edit").hasRole("ADMIN")
                    .antMatchers("/category/create").hasRole("ADMIN")
                    .antMatchers("/category/delete").hasRole("ADMIN")
                    .antMatchers("/item/edit").hasRole("ADMIN")
                    .antMatchers("/item/create").hasRole("ADMIN")
                    .antMatchers("/item/delete").hasRole("ADMIN")
                    .antMatchers("/brand/edit").hasRole("ADMIN")
                    .antMatchers("/brand/create").hasRole("ADMIN")
                    .antMatchers("/brand/delete").hasRole("ADMIN")
                    .antMatchers("/seller/create").hasRole("ADMIN")
                    .antMatchers("/seller/edit").hasRole("ADMIN")
                    .antMatchers("/seller/delete").hasRole("ADMIN")
                    .antMatchers("/cart").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/cart/add").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/cart/clean").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/cart/delete").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/order/create").hasAnyRole("USER", "ADMIN")
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .csrf().disable()
                    .exceptionHandling().accessDeniedPage("/access/denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder())
                .usersByUsernameQuery("select username, password, active from user_entity where username = ?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from user_entity u inner join user_entity_roles ur on u.id = ur.user_entity_id where u.username=?");
    }
}
