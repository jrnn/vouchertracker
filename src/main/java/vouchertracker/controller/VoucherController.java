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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Customer;
import vouchertracker.domain.entity.Voucher;
import vouchertracker.service.CommentService;
import vouchertracker.service.CustomerService;
import vouchertracker.service.VoucherService;
import vouchertracker.utility.Librarian;

@Controller
public class VoucherController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VoucherService voucherService;

    @ModelAttribute("countries")
    public List<String> getCountries() {
        return Librarian.listEEACountries();
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }

    @RequestMapping(value = "/vouchers", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('USER')")
    public String listAll(Model model) {
        model.addAttribute("open", voucherService.findOpen());
        model.addAttribute("closed", voucherService.findClosed());

        return "vouchers";
    }

    @RequestMapping(value = "/vouchers/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('USER')")
    public String viewOne(Model model, @PathVariable("id") String id) {
        model.addAttribute("dto", voucherService.getDtoForVoucher(id));
        model.addAttribute("attachments", voucherService.getAttachments(id));
        model.addAttribute("comments", commentService.findAllForVoucher(id));

        return "voucher";
    }

    @RequestMapping(value = "/vouchers/new", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('VOUCHER_OWNER')")
    public String addNew(Model model) {
        model.addAttribute("dto", new VoucherDto());

        return "voucher_edit";
    }

    @RequestMapping(value = "/vouchers/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority(#ownerId) OR hasAuthority('ADMIN')")
    public String edit(
            Model model,
            @PathVariable("id") String id,
            @RequestParam("ownerId") String ownerId
    ) {
        model.addAttribute("dto", voucherService.getDtoForVoucher(id));

        return "voucher_edit";
    }

    @RequestMapping(value = "/vouchers", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('USER')")
    public String createOrUpdate(
            @ModelAttribute("dto") @Valid VoucherDto dto,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        String c = customerService.doesPassportExist(dto.getCustomerId(), dto.getPassport());
        if (c != null) result.rejectValue("passport", "",
                "Passport already reserved to customer by name " + c);

        if (voucherService.doesVoucherIdExist(dto)) result.rejectValue("voucherId", "",
                "Voucher with this exact ID exists already");

        if (result.hasErrors()) return "voucher_edit";

        Voucher voucher = voucherService.saveOrUpdateVoucher(dto);

        if (voucher != null) redirectAttrs
                .addFlashAttribute("success", "Hooray! Operation successful!");
        else redirectAttrs
                .addFlashAttribute("failure", "Oh snap! Something went wrong.");

        return "redirect:/vouchers";
    }

}