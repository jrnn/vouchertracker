package vouchertracker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vouchertracker.service.CustomUserDetailsService;

@Configuration
@Profile("production")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ProductionSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // note that RESET_PASSWORD now can access everything not explicitly restricted to a role
        http.authorizeRequests()
                .antMatchers("/css/**", "/login/**").permitAll()
                .antMatchers("/password/reset").hasAuthority("RESET_PASSWORD")
                .antMatchers("/users/", "/users/**").hasAuthority("SUPERUSER")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login").permitAll().and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("DungeonMaster")
                .password(System.getenv().get("SU_PASSWORD")) // heroku config var
                .authorities("ADMIN", "SUPERUSER");

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}