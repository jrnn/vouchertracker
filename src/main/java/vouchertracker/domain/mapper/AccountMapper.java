package vouchertracker.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.AccountDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.service.AccountService;

@Component
public class AccountMapper implements EntityMapper<Account, AccountDto> {

    @Autowired
    private AccountService accountService;

    @Override
    public Account mapDtoToEntity(AccountDto dto, Account account, String user) {
        account.setFirstName(dto.getFirstName().trim());
        account.setLastName(dto.getLastName().trim());
        account.setEmail(dto.getEmail().trim().toLowerCase());
        account.setAdministrator(dto.isAdministrator());
        account.setEnabled(dto.isEnabled());

        return account;
    }

    @Override
    public AccountDto mapEntityToDto(AccountDto dto, Account account) {
        dto.setId(account.getId());
        dto.setCreatedOn(account.getCreatedOn());

        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());
        dto.setAdministrator(account.isAdministrator());
        dto.setEnabled(account.isEnabled());

        return dto;
    }

    @Override
    public String writeToCsv(String separator) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\nUSER ACCOUNTS\n")
                .append(getCsvHeaders())
                .append("\n");

        accountService
                .findAll()
                .stream()
                .map(a -> mapEntityToCsv(a, separator))
                .forEach(a -> sb.append(a).append("\n"));

        return sb.toString();
    }

    private String mapEntityToCsv(Account a, String s) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(a.getId()).append(s)
                .append(a.getCreatedOn()).append(s)
                .append(a.getFirstName()).append(s)
                .append(a.getLastName()).append(s)
                .append(a.getEmail()).append(s)
                .append(a.isAdministrator()).append(s)
                .append(a.isEnabled()).append(s);

        return sb.toString();
    }

    private String getCsvHeaders() {
        return "id;created_on;first_name;last_name;email;admin;enabled;";
    }

}