package vouchertracker.controller;

import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping(value = "/vouchers", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String addVoucher(
            @ModelAttribute("dto") @Valid VoucherDto dto,
            BindingResult result
    ) {
        if (result.hasErrors()) return "voucher";

        System.out.println("SUCCESS!\n" + dto);
        return "redirect:/vouchers";
    }

}