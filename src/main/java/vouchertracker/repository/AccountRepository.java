package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    Account findByEmail(String email);

}