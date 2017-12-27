package vouchertracker.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vouchertracker.domain.Account;
import vouchertracker.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        email = email.trim().toLowerCase(); // should trimming etc. be done upstream?
        Account account = accountRepository.findByEmail(email);

        if (account == null) throw new UsernameNotFoundException("No such user: " + email);

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(account)
        );
    }

    private List<GrantedAuthority> getAuthorities(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority(account.getId()));

        if (account.isAdministrator()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return authorities;
    }

}