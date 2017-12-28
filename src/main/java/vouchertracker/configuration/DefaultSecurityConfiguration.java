package vouchertracker.configuration;

import javax.annotation.PostConstruct;
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
import vouchertracker.domain.AccountDto;
import vouchertracker.service.AccountService;
import vouchertracker.service.CustomUserDetailsService;

@Configuration
@Profile("default")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostConstruct // initialize a few fake users for sandbox
    public void init() {
        this.accountService.registerNewUser(new AccountDto("Spongebob", "Squarepants", "sponge@bob.io", false));
        this.accountService.registerNewUser(new AccountDto("Chuck", "Norris", "chuck@norr.is", true));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // enable use of H2 console in sandbox
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/h2-console/", "/h2-console/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/users/", "/users/**").hasAuthority("SUPERUSER")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login").permitAll().and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("superuser")
                .password("superuser")
                .authorities("ADMIN", "SUPERUSER");

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}