package vouchertracker.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vouchertracker.domain.dto.CustomerDto;
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

    // simplify? (i.e. 'if dto.getId().equals("new")' ...)
    public Customer handleCustomerInVoucher(CustomerDto dto) {
        Customer customer = customerRepository.findByUuid(dto.getId());

        if (customer == null) return saveOrUpdateCustomer(dto);

        return customer;
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

    private Customer writeDtoToCustomer(Customer customer, CustomerDto dto) {
        customer.setFirstName(dto.getFirstName().trim());
        customer.setLastName(dto.getLastName().trim());
        customer.setPassport(dto.getPassport().trim());

        return customer;
    }

}