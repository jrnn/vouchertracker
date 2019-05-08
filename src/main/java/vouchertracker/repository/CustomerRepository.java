package vouchertracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.CustomerLite;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    @EntityGraph(value = "customer-entity-graph", type = EntityGraphType.LOAD)
    List<Customer> findAll();

    List<CustomerLite> findAllByOrderByLastNameAsc();

    @Query("SELECT c FROM Customer c WHERE c.id = :id")
    Customer findByUuid(@Param("id") String id);

    @Query("SELECT c FROM Customer c WHERE c.id != :id AND c.passport = :passport")
    List<Customer> findIdenticalPassports(@Param("id") String id, @Param("passport") String passport);

}