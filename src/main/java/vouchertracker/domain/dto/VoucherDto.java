package vouchertracker.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vouchertracker.validation.constraint.IssuedBeforeReceived;
import vouchertracker.validation.constraint.NotEmpty;
import vouchertracker.validation.constraint.NotFuture;
import vouchertracker.validation.constraint.ParsableAsDouble;
import vouchertracker.validation.constraint.RefundLessThanPurchase;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RefundLessThanPurchase
@IssuedBeforeReceived
public class VoucherDto {

    private String accountId;

    @NotEmpty
    private String voucherId;
    private String voucherIdExt;

    @NotEmpty
    private String issuedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotFuture
    private LocalDate issuedOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotFuture
    private LocalDate receivedOn;

    @ParsableAsDouble
    private String purchaseAmount;

    @ParsableAsDouble
    private String refundAmount;
    private boolean stamped = false;

}