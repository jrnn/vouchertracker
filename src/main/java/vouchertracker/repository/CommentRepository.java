package vouchertracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vouchertracker.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c JOIN c.voucher v WHERE v.id = :id ORDER BY c.exactTime DESC")
    List<Comment> findByVoucherId(@Param("id") String id);

    @Query("SELECT c FROM Comment c JOIN c.customer cu WHERE cu.id = :id ORDER BY c.exactTime DESC")
    List<Comment> findByCustomerId(@Param("id") String id);

    @Query("SELECT c FROM Comment c JOIN c.shipment s WHERE s.id = :id ORDER BY c.exactTime DESC")
    List<Comment> findByShipmentId(@Param("id") String id);

}