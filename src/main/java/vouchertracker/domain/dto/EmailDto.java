package vouchertracker.domain.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.constraint.EmailFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDto {

    @EmailFormat
    @Size(max = 255, message = "Must not exceed 255 characters")
    private String email;

}