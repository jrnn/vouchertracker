package vouchertracker.domain.dto;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vouchertracker.validation.constraint.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {

    private String id; // = "new" ?
    private LocalDate createdOn;
    // private String lastEditedBy;
    // private LocalDateTime lastEditedOn;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Size(max = 32, message = "Must not exceed 32 characters")
    private String passport;

}