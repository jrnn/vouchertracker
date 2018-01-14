package vouchertracker.domain.mapper;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.Shipment;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.utility.CustomParser;

@Component
public class VoucherMapper implements EntityDtoMapper<Voucher, VoucherDto> {

    @Override
    public Voucher mapDtoToEntity(VoucherDto dto, Voucher voucher, String user) {
        voucher.setVoucherId(concatenateVoucherId(dto));
        voucher.setIssuedAt(dto.getIssuedAt().trim());
        voucher.setIssuedOn(dto.getIssuedOn());
        voucher.setReceivedOn(dto.getReceivedOn());
        voucher.setPurchaseAmount(CustomParser.parseCurrency(dto.getPurchaseAmount()));
        voucher.setRefundAmount(CustomParser.parseCurrency(dto.getRefundAmount()));
        voucher.setStamped(dto.isStamped());

        voucher.setLastEditedBy(user);
        voucher.setLastEditedOn(LocalDateTime.now());

        return voucher;
    }

    @Override
    public VoucherDto mapEntityToDto(VoucherDto dto, Voucher voucher) {
        dto.setId(voucher.getId());
        dto.setCreatedOn(voucher.getCreatedOn());
        dto.setLastEditedBy(voucher.getLastEditedBy());
        dto.setLastEditedOn(voucher.getLastEditedOn());

        dto.setVoucherId(disjoinVoucherId(voucher, 0));
        dto.setVoucherIdExt(disjoinVoucherId(voucher, 1));
        dto.setIssuedAt(voucher.getIssuedAt());
        dto.setIssuedOn(voucher.getIssuedOn());
        dto.setReceivedOn(voucher.getReceivedOn());
        dto.setPurchaseAmount(CustomParser.parseCurrency(voucher.getPurchaseAmount()));
        dto.setRefundAmount(CustomParser.parseCurrency(voucher.getRefundAmount()));
        dto.setStamped(voucher.isStamped());

        attachCustomer(dto, voucher.getCustomer());
        attachAccount(dto, voucher.getAccount());
        attachShipment(dto, voucher.getShipment());

        return dto;
    }

    private VoucherDto attachAccount(VoucherDto dto, Account account) {
        dto.setAccountId(account.getId());
        dto.setAccountName(account.getFullName());

        return dto;
    }

    private VoucherDto attachShipment(VoucherDto dto, Shipment shipment) {
        if (shipment != null) dto.setTrackingNo(shipment.getTrackingNo());

        return dto;
    }

    private VoucherDto attachCustomer(VoucherDto dto, Customer customer) {
        if (customer != null) {
            dto.setCustomerId(customer.getId());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setPassport(customer.getPassport());
        }

        return dto;
    }

    public CustomerDto detachCustomerDto(VoucherDto vDto) {
        CustomerDto dto = new CustomerDto();

        dto.setId(vDto.getCustomerId());
        dto.setFirstName(vDto.getFirstName());
        dto.setLastName(vDto.getLastName());
        dto.setPassport(vDto.getPassport());

        return dto;
    }

    private String concatenateVoucherId(VoucherDto dto) {
        String id = dto.getVoucherId().trim();
        String idExt = dto.getVoucherIdExt().trim();

        return (idExt.isEmpty()
                ? id
                : id + "\n" + idExt);
    }

    private String disjoinVoucherId(Voucher voucher, int index) {
        try {
            return voucher.getVoucherId().split("\n")[index];
        } catch (Exception e) {
        }

        return null;
    }

}