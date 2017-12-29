package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByAccountId(String accountId);

    VerificationToken findByToken(String token);

}