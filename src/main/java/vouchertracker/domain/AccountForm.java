package vouchertracker.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.EmailFormat;
import vouchertracker.validation.EmailNotReserved;
import vouchertracker.validation.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountForm {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @EmailFormat
    @EmailNotReserved
    private String email;

    @NotNull
    private boolean administrator;

}