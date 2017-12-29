package vouchertracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.EmailFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDto {

    @EmailFormat
    private String email;

}