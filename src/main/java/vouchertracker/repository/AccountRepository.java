package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a WHERE LOWER(a.email) = LOWER(:email)")
    Account findByEmailIgnoreCase(@Param("email") String email);

}