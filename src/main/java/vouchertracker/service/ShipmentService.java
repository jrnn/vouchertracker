package vouchertracker.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vouchertracker.domain.dto.ShipmentDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.Shipment;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.repository.ShipmentRepository;
import vouchertracker.repository.VoucherRepository;

@Service
public class ShipmentService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    public Shipment getOne(String id) {
        return shipmentRepository.findByUuid(id);
    }

    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    public Shipment create(ShipmentDto dto) {
        Shipment shipment = initShipment(dto);

        Arrays.stream(dto.getVoucherIds()).forEach(voucherId -> {
            addVoucherToShipment(shipment, voucherId);
        });

        return shipmentRepository.save(shipment);
    }

    private Shipment initShipment(ShipmentDto dto) {
        Shipment shipment = new Shipment();
        Account account = accountService.getAccountByAuthentication();

        shipment.setTrackingNo(dto.getTrackingNo().trim());
        shipment.setShippedOn(dto.getShippedOn());
        shipment.setAddedBy(account.getFullName());

        return shipmentRepository.save(shipment);
    }

    @Transactional
    private void addVoucherToShipment(Shipment shipment, String voucherId) {
        Voucher voucher = voucherRepository.findByUuid(voucherId);

        if (!voucher.isStamped() || voucher.getShipment() != null) return;

        voucher.setShipment(shipment);
        voucherRepository.save(voucher);

        shipment.getVouchers().add(voucher);
    }

}