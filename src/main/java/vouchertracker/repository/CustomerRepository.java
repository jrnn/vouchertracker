package vouchertracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vouchertracker.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}