package vouchertracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Customer findByUuid(@Param("id") String id);

    List<Customer> findAllByOrderByLastNameAsc();

    @Query("SELECT c FROM Customer c WHERE c.id != :id AND c.passport = :passport")
    List<Customer> findIdenticalPassports(@Param("id") String id, @Param("passport") String passport);

}