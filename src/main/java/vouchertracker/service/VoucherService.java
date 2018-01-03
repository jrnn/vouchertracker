package vouchertracker.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public Voucher getOne(String id) {
        return voucherRepository.findByUuid(id);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public VoucherDto getDtoForVoucher(String id) {
        Voucher voucher = voucherRepository.findByUuid(id);

        return (voucher == null ? new VoucherDto() : writeDtoFromVoucher(voucher));
    }

    public Voucher saveOrUpdateVoucher(VoucherDto dto) {
        Account account = accountService.getAccountByAuthentication();
        Voucher voucher = voucherRepository.findByUuid(dto.getId());

        if (account == null) return null;

        if (voucher == null) {
            voucher = new Voucher();
            setAccountToVoucher(voucher, account);
        }

        voucher = writeDtoToVoucher(voucher, dto);
        setCustomerToVoucher(voucher, customerService.handleCustomerInVoucherDto(dto));
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
        voucher.setVoucherId(concatenateVoucherId(dto));
        voucher.setIssuedAt(dto.getIssuedAt().trim());
        voucher.setIssuedOn(dto.getIssuedOn());
        voucher.setReceivedOn(dto.getReceivedOn());
        voucher.setPurchaseAmount(parseToCurrency(dto.getPurchaseAmount()));
        voucher.setRefundAmount(parseToCurrency(dto.getRefundAmount()));
        voucher.setStamped(dto.isStamped());

        return voucher;
    }

    private VoucherDto writeDtoFromVoucher(Voucher voucher) {
        VoucherDto dto = new VoucherDto();

        dto.setId(voucher.getId());
        dto.setVoucherId(disjoinVoucherId(voucher, 0));
        dto.setVoucherIdExt(disjoinVoucherId(voucher, 1));
        dto.setIssuedAt(voucher.getIssuedAt());
        dto.setIssuedOn(voucher.getIssuedOn());
        dto.setReceivedOn(voucher.getReceivedOn());
        dto.setPurchaseAmount("" + (1.0 * voucher.getPurchaseAmount() / 100));
        dto.setRefundAmount("" + (1.0 * voucher.getRefundAmount() / 100));
        dto.setStamped(voucher.isStamped());

        return customerService.attachCustomerToVoucher(dto, voucher.getCustomer().getId());
    }

    private String concatenateVoucherId(VoucherDto dto) {
        String id = dto.getVoucherId().trim();
        String idExt = dto.getVoucherIdExt().trim();

        return (idExt.isEmpty() ? id : id + "\n" + idExt);
    }

    private String disjoinVoucherId(Voucher voucher, int index) {
        try {
            return voucher.getVoucherId().split("\n")[index];
        } catch (Exception e) {
        }

        return null;
    }

    private Long parseToCurrency(String s) { // this probably belongs elsewhere
        try {
            return Math.round(100 * Double.parseDouble(s.trim().replace(",", ".")));
        } catch (NumberFormatException e) {
        }

        return null;
    }

    public List<String> getCountries() { // this probably belongs elsewhere
        return Arrays.asList(new String[] {
            "Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus", "Czech Republic",
            "Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary",
            "Iceland", "Ireland", "Italy", "Latvia", "Liechtenstein", "Lithuania",
            "Luxembourg", "Malta", "Netherlands", "Norway", "Poland", "Portugal",
            "Romania", "Slovakia", "Slovenia", "Spain", "Sweden", "United Kingdom"
        });
    }

}