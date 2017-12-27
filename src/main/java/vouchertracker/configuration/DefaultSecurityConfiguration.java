package vouchertracker.configuration;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vouchertracker.service.AccountService;

@Configuration
@Profile("default")
@EnableWebSecurity
public class DefaultSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct // initialize a few fake users for sandbox
    public void init() {
        accountService.register("Huey", "Duck", "huey", "qwerty", false);
        accountService.register("Dewey", "Duck", "dewey", "qwerty", true);
        accountService.register("Louie", "Duck", "louie", "qwerty", false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // enable use of H2 console in sandbox
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/h2-console/", "/h2-console/**").permitAll()
                // .antMatchers("/css/**").permitAll() <-- needed later for custom login page
                .antMatchers("/users/", "/users/**").hasAuthority("SUPERUSER")
                .anyRequest().authenticated();

        http.formLogin()
                // .loginPage("/login") <-- neede later for custom login page
                .permitAll().and()
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