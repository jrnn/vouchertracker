package vouchertracker.domain.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vouchertracker.validation.constraint.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShipmentDto {

    @NotEmpty(message = "Tracking number must not be empty")
    @Size(max = 64, message = "Tracking number must not exceed 64 characters")
    private String trackingNo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate shippedOn = LocalDate.now();

    @NotNull(message = "Must select at least one voucher")
    private String[] voucherIds;

}