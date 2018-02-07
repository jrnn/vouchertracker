package vouchertracker.domain.mapper;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.Shipment;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.service.VoucherService;
import vouchertracker.utility.CustomParser;

@Component
public class VoucherMapper implements EntityMapper<Voucher, VoucherDto> {

    @Autowired
    private VoucherService voucherService;

    @Override
    public Voucher mapDtoToEntity(VoucherDto dto, Voucher voucher, String user) {
        voucher.setVoucherId(concatenateVoucherId(dto));
        voucher.setIssuedAt(dto.getIssuedAt().trim());
        voucher.setIssuedOn(dto.getIssuedOn());
        voucher.setReceivedOn(dto.getReceivedOn());
        voucher.setPurchaseAmount(CustomParser.parseCurrency(dto.getPurchaseAmount()));
        voucher.setRefundAmount(CustomParser.parseCurrency(dto.getRefundAmount()));
        voucher.setStamped(dto.isStamped());
        voucher.setPrepaid(dto.isPrepaid());

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
        dto.setPrepaid(voucher.isPrepaid());

        attachCustomer(dto, voucher.getCustomer());
        attachAccount(dto, voucher.getAccount());
        attachShipment(dto, voucher.getShipment());

        return dto;
    }

    @Override
    public String writeToCsv(String separator) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\nVOUCHERS\n")
                .append(getCsvHeaders())
                .append("\n");

        voucherService
                .findAll()
                .stream()
                .map(v -> mapEntityToCsv(v, separator))
                .forEach(v -> sb.append(v).append("\n"));

        return sb.toString();
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

    private String mapEntityToCsv(Voucher v, String s) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(v.getId()).append(s)
                .append(v.getCreatedOn()).append(s)
                .append(v.getLastEditedBy()).append(s)
                .append(v.getLastEditedOn()).append(s)
                .append(v.getVoucherId().replace("\n", "//")).append(s)
                .append(v.getIssuedAt()).append(s)
                .append(v.getIssuedOn()).append(s)
                .append(v.getReceivedOn()).append(s)
                .append(CustomParser.parseCurrency(v.getPurchaseAmount())).append(s)
                .append(CustomParser.parseCurrency(v.getRefundAmount())).append(s)
                .append(v.isStamped()).append(s)
                .append(v.isPrepaid()).append(s)
                .append(v.getAccount().getId()).append(s)
                .append(v.getCustomer().getId()).append(s);

        if (v.getShipment() != null) sb
                .append(v.getShipment().getId()).append(s);
        else sb
                .append("null").append(s);

        return sb.toString();
    }

    private String getCsvHeaders() {
        return "id;created_on;last_edited_by;last_edited_on;voucher_id;issued_at;" +
                "issued_on;received_on;purchase_amount;refund_amount;customs_stamp;" +
                "prepaid;owner_id;customer_id;shipment_id;";
    }

}