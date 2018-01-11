package vouchertracker.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vouchertracker.domain.dto.ShipmentDto;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.service.ShipmentService;
import vouchertracker.service.VoucherService;

@Controller
@PreAuthorize("hasAuthority('USER')")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private VoucherService voucherService;

    @ModelAttribute("vouchers")
    public List<Voucher> getValidVouchers() {
        return voucherService.findShippable();
    }

    public String listAll() {
        return "not yet supported";
    }

    public String viewOne() {
        return "not yet supported";
    }

    @RequestMapping(value = "/ups", method = RequestMethod.GET)
    public String addNew(@ModelAttribute("dto") ShipmentDto dto) {
        return "ups_edit";
    }

    @RequestMapping(value = "/ups", method = RequestMethod.POST)
    public String create(
            @ModelAttribute("dto") @Valid ShipmentDto dto,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) return "ups_edit";

        shipmentService.create(dto);
        redirectAttrs.addFlashAttribute(
                "success", "Hooray! UPS shipment successfully created!");

        return "redirect:/ups";
    }

}