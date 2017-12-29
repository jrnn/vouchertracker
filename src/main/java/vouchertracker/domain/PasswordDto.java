package vouchertracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.PasswordFormat;
import vouchertracker.validation.PasswordMatches;

@NoArgsConstructor
@AllArgsConstructor
@Data
@PasswordMatches
public class PasswordDto {

    @PasswordFormat
    private String newPassword;

    @PasswordFormat
    private String confirmPassword;

}