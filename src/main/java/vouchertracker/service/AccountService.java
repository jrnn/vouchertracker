package vouchertracker.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vouchertracker.domain.Account;
import vouchertracker.domain.AccountDto;
import vouchertracker.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordService passwordService;

    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

//    public boolean emailExists(String email) {
//        return accountRepository.findByEmailIgnoreCase(email.trim()) != null;
//    }

    public String checkAccountStatus(String email) {
        Account account = accountRepository.findByEmailIgnoreCase(email.trim());

        if (account == null) return "No user has registered with this email";
        if (!account.isEnabled()) return "The account tied to this email has been disabled";

        return null;
    }

    public boolean anotherUserHasThisEmail(String id, String email) {
        return accountRepository.countIdenticalEmails(id, email.trim()) > 0;
    }

    public AccountDto getDtoForAccount(String id) {
        Account account = accountRepository.findByUuid(id);

        if (account == null) return new AccountDto();

        return writeDtoFromAccount(account);
    }

    public Account registerOrUpdateAccount(AccountDto dto) {
        if (anotherUserHasThisEmail(dto.getId(), dto.getEmail())) return null;

        Account account = accountRepository.findByUuid(dto.getId());

        if (account == null) {
            account = new Account();
            account.setPassword(passwordService.getRandomPassword());
        }

        account = writeDtoToAccount(account, dto);
        return accountRepository.save(account);
    }

    private Account writeDtoToAccount(Account account, AccountDto dto) {
        account.setFirstName(dto.getFirstName().trim());
        account.setLastName(dto.getLastName().trim());
        account.setEmail(dto.getEmail().trim().toLowerCase());
        account.setAdministrator(dto.isAdministrator());
        account.setEnabled(dto.isEnabled());

        return account;
    }

    private AccountDto writeDtoFromAccount(Account account) {
        AccountDto dto = new AccountDto();

        dto.setId(account.getId());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setAdministrator(account.isAdministrator());
        dto.setEnabled(account.isEnabled());
        dto.setCreatedOn(account.getCreatedOn());

        return dto;
    }

}