package vouchertracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vouchertracker.domain.Account;
import vouchertracker.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // this method could be prettier
    public Account register(String firstName, String lastName, String email, String password) {
        email = email.trim().toLowerCase(); // should trimming etc. be done upstream?
        Account account = accountRepository.findByEmail(email);

        if (account != null) throw new IllegalArgumentException(
                "An account with email address " + email + " already exists!"
        );

        account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        account.setAdministrator(false);

        return this.accountRepository.save(account);
    }

    public void changePassword(String accountId, String password) {
        throw new UnsupportedOperationException();
    }

    public void toggleAdmin(String accountId) {
        Account account = accountRepository.getOne(accountId);

        if (account == null) return;

        account.setAdministrator(!account.isAdministrator());
        accountRepository.save(account);
    }

}