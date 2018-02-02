package vouchertracker.domain.mapper;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.entity.Customer;
import vouchertracker.service.CustomerService;

@Component
public class CustomerMapper implements EntityMapper<Customer, CustomerDto> {

    @Autowired
    private CustomerService customerService;

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

    @Override
    public String writeToCsv(String separator) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\nCUSTOMERS\n")
                .append(getCsvHeaders())
                .append("\n");

        customerService
                .findAll()
                .stream()
                .map(c -> mapEntityToCsv(c, separator))
                .forEach(c -> sb.append(c).append("\n"));

        return sb.toString();
    }

    private String mapEntityToCsv(Customer c, String s) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(c.getId()).append(s)
                .append(c.getCreatedOn()).append(s)
                .append(c.getLastEditedBy()).append(s)
                .append(c.getLastEditedOn()).append(s)
                .append(c.getFirstName()).append(s)
                .append(c.getLastName()).append(s)
                .append(c.getPassport()).append(s);

        return sb.toString();
    }

    private String getCsvHeaders() {
        return "id;created_on;last_edited_by;last_edited_on;first_name;" +
                "last_name;passport_no;";
    }

}