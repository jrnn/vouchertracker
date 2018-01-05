package vouchertracker.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
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

    private String id = "new";
    private LocalDate createdOn;
    private String lastEditedBy;
    private LocalDateTime lastEditedOn;

    private String customerId = "new";
    private String accountId;
    private String accountName;

    // Voucher fields
    @NotEmpty
    @Size(max = 128, message = "Must not exceed 128 characters")
    private String voucherId;

    @Size(max = 128, message = "Must not exceed 128 characters")
    private String voucherIdExt;

    @NotEmpty
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String issuedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotFuture
    private LocalDate issuedOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotFuture
    private LocalDate receivedOn = LocalDate.now();

    @ParsableAsDouble
    private String purchaseAmount;

    @ParsableAsDouble
    private String refundAmount;
    private boolean stamped = false;

    // Customer fields
    @NotEmpty
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String firstName;

    @NotEmpty
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String lastName;

    @NotEmpty
    @Size(max = 32, message = "Must not exceed 32 characters")
    private String passport;

}