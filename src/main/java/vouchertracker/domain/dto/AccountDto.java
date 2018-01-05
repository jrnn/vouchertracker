package vouchertracker.domain.dto;

import java.time.LocalDate;
import javax.validation.constraints.Size;
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
    private LocalDate createdOn;

    @NotEmpty
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String firstName;

    @NotEmpty
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String lastName;

    @EmailFormat
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String email;

    private boolean administrator = false;
    private boolean enabled = true;

}