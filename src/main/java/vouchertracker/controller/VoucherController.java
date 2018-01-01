package vouchertracker.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vouchertracker.domain.VoucherDto;

@Controller
public class VoucherController {

    @RequestMapping(value = "/vouchers", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String editVoucher(Model model) {
        model.addAttribute("dto", new VoucherDto());

        return "voucher";
    }

}