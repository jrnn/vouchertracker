package vouchertracker.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Customer;
import vouchertracker.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer handleCustomerInVoucherDto(VoucherDto vDto) {
        CustomerDto cDto = detachCustomerFromVoucher(vDto);

        return (cDto.getId().equals("new")
                ? saveOrUpdateCustomer(cDto)
                : customerRepository.findByUuid(cDto.getId()));
    }

    public Customer saveOrUpdateCustomer(CustomerDto dto) {
        Account account = accountService.getAccountByAuthentication();
        Customer customer = customerRepository.findByUuid(dto.getId());

        if (customer == null) {
            customer = new Customer();
        }

        customer = this.writeDtoToCustomer(customer, dto);
        customer.setLastEditedBy(account.getFirstName() + " " + account.getLastName());
        customer.setLastEditedOn(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    public CustomerDto detachCustomerFromVoucher(VoucherDto vDto) {
        CustomerDto cDto = new CustomerDto();

        cDto.setId(vDto.getCustomerId());
        cDto.setFirstName(vDto.getFirstName());
        cDto.setLastName(vDto.getLastName());
        cDto.setPassport(vDto.getPassport());

        return cDto;
    }

    public VoucherDto attachCustomerToVoucher(VoucherDto dto, String customerId) {
        Customer customer = customerRepository.findByUuid(customerId);

        if (customer != null) {
            dto.setCustomerId(customer.getId());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setPassport(customer.getPassport());
        }

        return dto;
    }

    private Customer writeDtoToCustomer(Customer customer, CustomerDto dto) {
        customer.setFirstName(dto.getFirstName().trim());
        customer.setLastName(dto.getLastName().trim());
        customer.setPassport(dto.getPassport().trim());

        return customer;
    }

    private CustomerDto writeDtoFromCustomer(Customer customer) {
        CustomerDto dto = new CustomerDto();

        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPassport(customer.getPassport());

        return null;
    }

}