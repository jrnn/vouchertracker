package vouchertracker.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Comment;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.Shipment;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.repository.CommentRepository;
import vouchertracker.repository.CustomerRepository;
import vouchertracker.repository.ShipmentRepository;
import vouchertracker.repository.VoucherRepository;

@Service
public class CommentService {

    private static final List<String> SOURCES = Arrays.asList("vouchers", "customers", "ups");

    @Autowired
    private AccountService accountService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Comment> findAllForVoucher(String id) {
        return commentRepository.findByVoucherId(id);
    }

    public List<Comment> findAllForCustomer(String id) {
        return commentRepository.findByCustomerId(id);
    }

    public List<Comment> findAllForShipment(String id) {
        return commentRepository.findByShipmentId(id);
    }

    public Comment saveComment(String id, String src, String content) {
        if (content.isEmpty() || content.length() > 1000) return null;
        if (!SOURCES.contains(src)) return null;

        String author = getAuthor();
        if (author == null) return null;

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setContent(content);

        if (src.equals("vouchers")) attachToVoucher(comment, id);
        else if (src.equals("customers")) attachToCustomer(comment, id);
        else if (src.equals("ups")) attachToShipment(comment, id);

        return commentRepository.save(comment);
    }

    private String getAuthor() {
        boolean isSuperuser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("SUPERUSER"));

        if (isSuperuser) return "DungeonMaster";

        Account account = accountService.getAccountByAuthentication();

        if (account != null) return account.getFullName();
        else return null;
    }

    @Transactional
    private void attachToVoucher(Comment comment, String id) {
        Voucher voucher = voucherRepository.findByUuid(id);
        comment.setVoucher(voucher);
    }

    @Transactional
    private void attachToCustomer(Comment comment, String id) {
        Customer customer = customerRepository.findByUuid(id);
        comment.setCustomer(customer);
    }

    @Transactional
    private void attachToShipment(Comment comment, String id) {
        Shipment shipment = shipmentRepository.findByUuid(id);
        comment.setShipment(shipment);
    }

}