package vouchertracker.domain;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoucherEntry extends UUIDPersistable {

    @Column(updatable = false, nullable = false)
    private LocalDate createdOn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToMany(fetch = EAGER, mappedBy = "voucherEntry")
    private List<VoucherVersion> voucherVersions;

}