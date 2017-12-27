package vouchertracker.service;

import java.util.Collection;
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

    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

    public boolean isEmailReserved(String email) {
        return accountRepository.findByEmail(email.trim().toLowerCase()) != null;
    }

    // this method could be prettier
    public Account register(String firstName, String lastName, String email, String password, boolean admin) {
        if (isEmailReserved(email)) throw new IllegalArgumentException(
                "An account with email address " + email + " already exists!"
        );

        Account account = new Account();
        account.setFirstName(firstName.trim());
        account.setLastName(lastName.trim());
        account.setEmail(email.trim().toLowerCase());
        account.setPassword(passwordEncoder.encode(password));
        account.setAdministrator(admin);

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