package vouchertracker.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vouchertracker.validation.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
// validation needed : issuedOn cannot be later than receivedOn
// validation needed : refundAmount cannot be greater than purchaseAmount
public class VoucherDto {

    private String accountId = "new";

    @NotEmpty
    private String voucherId;
    private String voucherIdExt;

    @NotEmpty
    private String issuedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    // validation needed : cannot be a future date
    private LocalDate issuedOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    // validation needed : cannot be a future date
    private LocalDate receivedOn;

    // validation needed : must be "parsable" as double
    private Double purchaseAmount;

    // validation needed : must be "parsable" as double
    private Double refundAmount;
    private boolean stamped = false;

}