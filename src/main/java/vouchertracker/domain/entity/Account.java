package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Account extends UUIDPersistable {

    private String firstName;
    private String lastName;
    private String email;

    @Column(length = 64) // bcrypt hashes 60 chars
    private String password;
    private boolean administrator;
    private boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Voucher> vouchers = new ArrayList<>();

    @Override
    public String toString() {
        return getId();
    }

}