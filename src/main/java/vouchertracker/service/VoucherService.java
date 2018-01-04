package vouchertracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.domain.mapper.VoucherMapper;
import vouchertracker.repository.VoucherRepository;

@Service
public class VoucherService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VoucherMapper voucherMapper;
    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher getOne(String id) {
        return voucherRepository.findByUuid(id);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public VoucherDto getDtoForVoucher(String id) {
        VoucherDto dto = new VoucherDto();
        Voucher voucher = voucherRepository.findByUuid(id);

        if (voucher != null) {
            dto = voucherMapper.mapEntityToDto(dto, voucher);
            dto = voucherMapper.attachCustomer(dto, voucher.getCustomer());
        }

        return dto;
    }

    @Transactional
    public Voucher saveOrUpdateVoucher(VoucherDto dto) {
        Account account = accountService.getAccountByAuthentication();

        if (account == null) return null;

        Voucher voucher = voucherRepository.findByUuid(dto.getId());

        if (voucher == null) {
            voucher = new Voucher();
            voucher.setAccount(account);
        }

        voucher = voucherMapper.mapDtoToEntity(dto, voucher, account.getFullName());
        Customer customer = customerService.saveOrUpdateCustomer(
                voucherMapper.detachCustomerDto(dto));
        voucher.setCustomer(customer);

        return voucherRepository.save(voucher);
    }

}