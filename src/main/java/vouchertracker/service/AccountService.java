package vouchertracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vouchertracker.domain.dto.AccountDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.mapper.AccountMapper;
import vouchertracker.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordService passwordService;

    public List<Account> findAll() {
        return accountRepository.findAllByOrderByLastNameAsc();
    }

    public Account getAccountByAuthentication() {
        return accountRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public String checkAccountStatus(String email) {
        Account account = accountRepository.findByEmailIgnoreCase(email.trim());

        if (account == null) return "No user has registered with this email";

        return (account.isEnabled()
                ? null
                : "The account tied to this email has been disabled");
    }

    public boolean emailReserved(String id, String email) {
        return accountRepository.countIdenticalEmails(id, email.trim()) > 0;
    }

    public AccountDto getDtoForAccount(String id) {
        Account account = accountRepository.findByUuid(id);

        return (account == null
                ? new AccountDto()
                : accountMapper.mapEntityToDto(new AccountDto(), account));
    }

    public Account registerOrUpdateAccount(AccountDto dto) {
        if (emailReserved(dto.getId(), dto.getEmail())) return null;

        Account account = accountRepository.findByUuid(dto.getId());

        if (account == null) {
            account = new Account();
            account.setPassword(passwordService.getRandomPassword());
        }

        account = accountMapper.mapDtoToEntity(dto, account, null);

        return accountRepository.save(account);
    }

}