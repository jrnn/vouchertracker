package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
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
public class Shipment extends UUIDPersistable {

    @Column(length = 64, nullable = false)
    private String trackingNo;
    private LocalDate shippedOn;

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    List<Voucher> vouchers = new ArrayList<>();

    @Override
    public String toString() {
        return getId();
    }

}