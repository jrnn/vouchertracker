package vouchertracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.constraint.PasswordFormat;
import vouchertracker.validation.constraint.PasswordMatches;

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