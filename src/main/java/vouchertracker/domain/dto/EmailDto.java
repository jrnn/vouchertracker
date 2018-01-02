package vouchertracker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.constraint.EmailFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDto {

    @EmailFormat
    private String email;

}