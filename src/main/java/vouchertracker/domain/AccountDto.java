package vouchertracker.domain;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.EmailFormat;
import vouchertracker.validation.NotEmpty;

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

    @NotNull
    private boolean administrator;

}