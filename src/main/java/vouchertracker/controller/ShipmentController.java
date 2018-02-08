package vouchertracker.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vouchertracker.domain.dto.ShipmentDto;
import vouchertracker.domain.entity.Shipment;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.service.CommentService;
import vouchertracker.service.ShipmentService;
import vouchertracker.service.VoucherService;

@Controller
@PreAuthorize("hasAuthority('USER')")
public class ShipmentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private VoucherService voucherService;

    @ModelAttribute("vouchers")
    public List<Voucher> getValidVouchers() {
        return voucherService.findShippable();
    }

    @RequestMapping(value = "/ups", method = RequestMethod.GET)
    public String listAll(Model model) {
        model.addAttribute("shipments", shipmentService.findAll());

        return "shipments";
    }

    @RequestMapping(value = "/ups/{id}", method = RequestMethod.GET)
    public String viewOne(Model model, @PathVariable("id") String id) {
        model.addAttribute("shipment", shipmentService.getOne(id));
        model.addAttribute("comments", commentService.findAllForShipment(id));

        return "shipment";
    }

    @RequestMapping(value = "/ups/new", method = RequestMethod.GET)
    public String addNew(@ModelAttribute("dto") ShipmentDto dto) {
        return "shipment_edit";
    }

    @RequestMapping(value = "/ups", method = RequestMethod.POST)
    public String create(
            @ModelAttribute("dto") @Valid ShipmentDto dto,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) return "shipment_edit";

        Shipment shipment = shipmentService.create(dto);

        if (shipment != null) {
            redirectAttrs.addFlashAttribute(
                    "success", "Hooray! UPS shipment successfully created!");
            return "redirect:/ups/" + shipment.getId();
        }

        redirectAttrs.addFlashAttribute("failure", "Oh snap! Something went wrong.");
        return "redirect:/ups";
    }

}