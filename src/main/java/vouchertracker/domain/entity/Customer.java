package vouchertracker.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer extends UUIDPersistable {

    private String firstName;
    private String lastName;

    @Column(length = 32, nullable = false)
    private String passport;

    @OneToMany(mappedBy = "customer")
    private List<Voucher> vouchers;

    private String lastEditedBy;
    private LocalDateTime lastEditedOn;

}