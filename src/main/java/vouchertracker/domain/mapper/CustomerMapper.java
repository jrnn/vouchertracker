package vouchertracker.domain.mapper;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.entity.Customer;

@Component
public class CustomerMapper implements EntityDtoMapper<Customer, CustomerDto> {

    @Override
    public Customer mapDtoToEntity(CustomerDto dto, Customer customer, String user) {
        customer.setFirstName(dto.getFirstName().trim());
        customer.setLastName(dto.getLastName().trim());
        customer.setPassport(dto.getPassport().trim());

        customer.setLastEditedBy(user);
        customer.setLastEditedOn(LocalDateTime.now());

        return customer;
    }

    @Override
    public CustomerDto mapEntityToDto(CustomerDto dto, Customer customer) {
        dto.setId(customer.getId());
        dto.setCreatedOn(customer.getCreatedOn());
        dto.setLastEditedBy(customer.getLastEditedBy());
        dto.setLastEditedOn(customer.getLastEditedOn());

        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPassport(customer.getPassport());

        return dto;
    }

}