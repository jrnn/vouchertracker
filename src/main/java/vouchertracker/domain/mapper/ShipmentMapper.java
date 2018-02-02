package vouchertracker.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vouchertracker.domain.dto.ShipmentDto;
import vouchertracker.domain.entity.Shipment;
import vouchertracker.service.ShipmentService;

@Component
public class ShipmentMapper implements EntityMapper<Shipment, ShipmentDto> {

    @Autowired
    private ShipmentService shipmentService;

    @Override
    public Shipment mapDtoToEntity(ShipmentDto dto, Shipment shipment, String user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ShipmentDto mapEntityToDto(ShipmentDto dto, Shipment shipment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String writeToCsv(String separator) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\nUPS SHIPMENTS\n")
                .append(getCsvHeaders())
                .append("\n");

        shipmentService
                .findAll()
                .stream()
                .map(s -> mapEntityToCsv(s, separator))
                .forEach(s -> sb.append(s).append("\n"));

        return sb.toString();
    }

    private String mapEntityToCsv(Shipment u, String s) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(u.getId()).append(s)
                .append(u.getCreatedOn()).append(s)
                .append(u.getTrackingNo()).append(s)
                .append(u.getShippedOn()).append(s)
                .append(u.getAddedBy()).append(s);

        return sb.toString();
    }

    private String getCsvHeaders() {
        return "id;created_on;ups_tracking_no;shipped_on;owner;";
    }

}