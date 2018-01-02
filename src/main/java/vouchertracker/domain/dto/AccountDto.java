package vouchertracker.domain.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.constraint.EmailFormat;
import vouchertracker.validation.constraint.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {

    private String id = "new";

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @EmailFormat
    private String email;
    private boolean administrator = false;
    private boolean enabled = true;
    private LocalDate createdOn;

}