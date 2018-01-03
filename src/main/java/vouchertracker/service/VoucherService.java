package vouchertracker.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.repository.VoucherRepository;

@Service
public class VoucherService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher saveOrUpdateVoucher(VoucherDto dto) {
        Account account = accountService.getAccountByAuthentication();
        Voucher voucher = voucherRepository.findByUuid(dto.getId());

        if (voucher == null) {
            voucher = new Voucher();
            setAccountToVoucher(voucher, account);
        }

        voucher = writeDtoToVoucher(voucher, dto);
        setCustomerToVoucher(voucher, customerService.handleCustomerInVoucher(detachCustomerDto(dto)));
        voucher.setLastEditedBy(account.getFirstName() + " " + account.getLastName());
        voucher.setLastEditedOn(LocalDateTime.now());

        return voucherRepository.save(voucher);
    }

    @Transactional
    private void setAccountToVoucher(Voucher voucher, Account account) {
        voucher.setAccount(account);
        account.getVouchers().add(voucher);
    }

    @Transactional
    private void setCustomerToVoucher(Voucher voucher, Customer customer) {
        voucher.setCustomer(customer);
        customer.getVouchers().add(voucher);
    }

    private Voucher writeDtoToVoucher(Voucher voucher, VoucherDto dto) {
        voucher.setVoucherId(concatVoucherId(dto));
        voucher.setIssuedAt(dto.getIssuedAt().trim());
        voucher.setIssuedOn(dto.getIssuedOn());
        voucher.setReceivedOn(dto.getReceivedOn());
        voucher.setPurchaseAmount(parseCurrency(dto.getPurchaseAmount()));
        voucher.setRefundAmount(parseCurrency(dto.getRefundAmount()));
        voucher.setStamped(dto.isStamped());

        return voucher;
    }

    private CustomerDto detachCustomerDto(VoucherDto vDto) {
        CustomerDto cDto = new CustomerDto();

        cDto.setId(vDto.getCustomerId());
        cDto.setFirstName(vDto.getFirstName());
        cDto.setLastName(vDto.getLastName());
        cDto.setPassport(vDto.getPassport());

        return cDto;
    }

    private String concatVoucherId(VoucherDto dto) {
        if (dto.getVoucherIdExt().isEmpty()) return dto.getVoucherId().trim();

        return dto.getVoucherId().trim() + "\n" + dto.getVoucherIdExt().trim();
    }

    private Long parseCurrency(String s) { // this probably belongs elsewhere
        try {
            return Math.round(100 * Double.parseDouble(s.trim().replace(",", ".")));
        } catch (NumberFormatException e) {
        }

        return null;
    }

}