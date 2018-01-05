package vouchertracker.domain.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileObject implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    //@Lob
    //@Basic(fetch = LAZY)
    private byte[] content;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

}