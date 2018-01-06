package vouchertracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, String> {

    @Query("SELECT v FROM Voucher v WHERE v.id = :id")
    Voucher findByUuid(@Param("id") String id);

    @Query("SELECT v FROM Voucher v JOIN v.customer c WHERE c.id = :id")
    List<Voucher> findByCustomerId(@Param("id")String id);

}