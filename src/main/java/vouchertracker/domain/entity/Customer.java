package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Voucher> vouchers = new ArrayList<>();

    private String lastEditedBy;
    private LocalDateTime lastEditedOn;

    public int getVouchersCount() {
        return getVouchers().size();
    }

    @Override
    public String toString() {
        return getId();
    }

}