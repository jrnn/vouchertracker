package vouchertracker.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vouchertracker.domain.entity.Comment;
import vouchertracker.service.CommentService;

@Component
public class CommentMapper implements EntityMapper<Comment, Comment> {

    @Autowired
    private CommentService commentService;

    @Override
    public Comment mapDtoToEntity(Comment dto, Comment comment, String user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comment mapEntityToDto(Comment dto, Comment comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String writeToCsv(String separator) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\nCOMMENTS\n")
                .append(getCsvHeaders())
                .append("\n");

        commentService
                .findAll()
                .stream()
                .map(c -> mapEntityToCsv(c, separator))
                .forEach(c -> sb.append(c).append("\n"));

        return sb.toString();
    }

    private String mapEntityToCsv(Comment c, String s) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(c.getId()).append(s)
                .append(c.getCreatedOn()).append(s)
                .append(c.getExactTime()).append(s)
                .append(c.getAuthor()).append(s)
                .append(c.getContent().replaceAll("\n", "[LINE_BREAK]")).append(s);

        String voucher_id = (c.getVoucher() == null)
                ? "null"
                : c.getVoucher().getId();
        String customer_id = (c.getCustomer() == null)
                ? "null"
                : c.getCustomer().getId();
        String shipment_id = (c.getShipment() == null)
                ? "null"
                : c.getShipment().getId();

        sb
                .append(voucher_id).append(s)
                .append(customer_id).append(s)
                .append(shipment_id).append(s);

        return sb.toString();
    }

    private String getCsvHeaders() {
        return "id;created_on;time;author;content;voucher_id;customer_id;shipment_id;";
    }

}