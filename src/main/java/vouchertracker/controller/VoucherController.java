package vouchertracker.controller;

import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vouchertracker.domain.dto.VoucherDto;

@Controller
public class VoucherController {

    @RequestMapping(value = "/vouchers", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String editVoucher(@ModelAttribute("dto") VoucherDto dto) {
        return "voucher";
    }

    @RequestMapping(value = "/vouchers", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String addVoucher(
            @ModelAttribute("dto") @Valid VoucherDto dto,
            BindingResult result
    ) {
        if (result.hasErrors()) return "voucher";

        System.out.println("DEBUGGING >> " + dto);
        return "redirect:/vouchers";
    }

}