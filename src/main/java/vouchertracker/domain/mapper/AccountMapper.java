package vouchertracker.domain.mapper;

import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.AccountDto;
import vouchertracker.domain.entity.Account;

@Component
public class AccountMapper implements EntityDtoMapper<Account, AccountDto> {

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

}