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
import vouchertracker.domain.dto.VoucherDto;
import vouchertracker.domain.entity.Customer;
import vouchertracker.repository.CustomerRepository;
import vouchertracker.service.CustomerService;
import vouchertracker.service.VoucherService;

@Controller
public class VoucherController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VoucherService voucherService;

//    @PostConstruct // fake users for testing
//    public void init() {
//        Customer c = new Customer();
//        c.setFirstName("Pelle");
//        c.setLastName("McHermanni");
//        c.setPassport("GG6661337");
//        customerRepository.save(c);
//        c = new Customer();
//        c.setFirstName("Roland");
//        c.setLastName("McMorath");
//        c.setPassport("SYF-42-69");
//        customerRepository.save(c);
//    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }

    @RequestMapping(value = "/vouchers", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String listVouchers(Model model) {
        model.addAttribute("vouchers", voucherService.findAll());

        return "vouchers";
    }

    @RequestMapping(value = "/vouchers/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String editVoucher(Model model, @PathVariable("id") String id) {
        model.addAttribute("dto", new VoucherDto()); // voucherService.getDtoForVoucher(id);

        return "voucher_edit";
    }

    @RequestMapping(value = "/vouchers/edit", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String addOrUpdateVoucher(
            @ModelAttribute("dto") @Valid VoucherDto dto,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) return "voucher_edit";

        voucherService.saveOrUpdateVoucher(dto);
        redirectAttrs.addFlashAttribute("success", "Hooray! Operation successful!");

//        System.out.println("DEBUGGING >> " + debugging());

        return "redirect:/vouchers";
    }

//    @Autowired
//    private AccountService accountService;
//    private String debugging() {
//        StringBuilder sb = new StringBuilder();
//        customerService.findAll().forEach(c -> {
//            sb.append("\nVouchers of customer ").append(c.getFirstName()).append(": ");
//            c.getVouchers().forEach(v -> {
//                sb.append(v.getId()).append(" ; ");
//            });
//        });
//        accountService.findAll().forEach(a -> {
//            sb.append("\nVouchers of account ").append(a.getFirstName()).append(": ");
//            a.getVouchers().forEach(v -> {
//                sb.append(v.getId()).append(" ; ");
//            });
//        });
//        return sb.toString();
//    }

}