package vouchertracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vouchertracker.domain.Account;
import vouchertracker.domain.AccountDto;
import vouchertracker.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isEmailReserved(String email) {
        return accountRepository.findByEmailIgnoreCase(email.trim()) != null;
    }

    public Account registerNewUser(AccountDto dto) {
        if (isEmailReserved(dto.getEmail())) return null;

        Account account = writeDtoToAccount(new Account(), dto);
        account.setPassword(passwordEncoder.encode("qwerty")); // <-- TEMPORARY

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

    private Account writeDtoToAccount(Account account, AccountDto dto) {
        account.setFirstName(dto.getFirstName().trim());
        account.setLastName(dto.getLastName().trim());
        account.setEmail(dto.getEmail().trim().toLowerCase());
        account.setAdministrator(dto.isAdministrator());

        return account;
    }

}