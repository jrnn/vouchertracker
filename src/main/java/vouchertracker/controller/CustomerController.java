package vouchertracker.controller;

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
import vouchertracker.domain.dto.CustomerDto;
import vouchertracker.domain.entity.Customer;
import vouchertracker.service.CommentService;
import vouchertracker.service.CustomerService;
import vouchertracker.service.VoucherService;

@Controller
public class CustomerController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VoucherService voucherService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('USER')")
    public String listAll(Model model) {
        model.addAttribute("customers", customerService.findAll());

        return "customers";
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('USER')")
    public String viewOne(Model model, @PathVariable("id") String id) {
        model.addAttribute("dto", customerService.getDtoForCustomer(id));
        model.addAttribute("vouchers", voucherService.findAllForCustomer(id));
        model.addAttribute("comments", commentService.findAllForCustomer(id));

        return "customer";
    }

    @RequestMapping(value = "/customers/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String edit(Model model, @PathVariable("id") String id) {
        model.addAttribute("dto", customerService.getDtoForCustomer(id));

        return "customer_edit";
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(
            @ModelAttribute("dto") @Valid CustomerDto dto,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) return "customer_edit";

        Customer customer = customerService.saveOrUpdateCustomer(dto);

        if (customer != null) {
            redirectAttrs.addFlashAttribute("success", "Hooray! Operation successful!");
            return "redirect:/customers/" + customer.getId();
        }

        redirectAttrs.addFlashAttribute("failure", "Oh snap! Something went wrong.");
        return "redirect:/customers";
    }

}