package vouchertracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.mapper.CustomerMapper;
import vouchertracker.repository.CustomerRepository;
import vouchertracker.utility.CustomParser;

@Service
public class CustomerService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAllByOrderByLastNameAsc();
    }

    public String doesPassportExist(String id, String passport) {
        List<Customer> c = customerRepository
                .findIdenticalPassports(id, CustomParser.trim(passport));

        return (c.isEmpty())
                ? null
                : c.get(0).getFirstName() + " " + c.get(0).getLastName();
    }

    public CustomerDto getDtoForCustomer(String id) {
        CustomerDto dto = new CustomerDto();
        Customer customer = customerRepository.findByUuid(id);

        return (customer == null
                ? dto
                : customerMapper.mapEntityToDto(dto, customer));
    }

    public Customer saveOrUpdateCustomer(CustomerDto dto) {
        Account account = accountService.getAccountByAuthentication();

        if (account == null) return null;

        Customer customer = customerRepository.findByUuid(dto.getId());

        if (customer == null) customer = new Customer();

        customer = customerMapper.mapDtoToEntity(dto, customer, account.getFullName());

        return customerRepository.save(customer);
    }

}