package vouchertracker.domain;

import java.time.LocalDate;
import javax.persistence.Column;
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

    @Column(length = 64) // bcrypt hashes 60 chars
    private String password;
    private boolean administrator;
    private boolean enabled;

    @Column(updatable = false)
    private LocalDate createdOn = LocalDate.now();

}