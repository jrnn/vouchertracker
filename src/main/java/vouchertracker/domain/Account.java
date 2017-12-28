package vouchertracker.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends UUIDPersistable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean administrator;
    // private boolean active; <-- ??
    // private LocalDate addedOn; <-- ??

}