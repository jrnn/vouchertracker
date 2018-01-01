package vouchertracker.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class VerificationToken implements Serializable {

    private static final int VALIDITY = 24 * 60; // in minutes

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(length = 32)
    private String token = UUID.randomUUID().toString().replace("-", "");

    private LocalDateTime expiresOn = LocalDateTime.now().plusMinutes(VALIDITY);

    @OneToOne(targetEntity = Account.class, fetch = EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}