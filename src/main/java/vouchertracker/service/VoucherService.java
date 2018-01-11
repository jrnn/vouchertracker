package vouchertracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Attachment;
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

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findAllForCustomer(String customerId) {
        return voucherRepository.findByCustomerId(customerId);
    }

    public List<Voucher> findShippable() {
        return voucherRepository.findShippable();
    }

    public List<Attachment> getAttachments(String id) {
        return voucherRepository.findByUuid(id).getAttachments();
    }

    public VoucherDto getDtoForVoucher(String id) {
        VoucherDto dto = new VoucherDto();
        Voucher voucher = voucherRepository.findByUuid(id);

        return (voucher == null
                ? dto
                : voucherMapper.mapEntityToDto(dto, voucher));
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