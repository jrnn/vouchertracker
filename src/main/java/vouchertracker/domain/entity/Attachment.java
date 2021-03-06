package vouchertracker.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attachment extends UUIDPersistable {

    private String fileName;
    private Long fileSize;

    @Column(length = 32)
    private String mediaType;

    @OneToOne(mappedBy = "attachment", fetch = LAZY)
    private FileObject fileObject;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @Override
    public String toString() {
        return getId();
    }

}