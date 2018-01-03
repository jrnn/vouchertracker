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
import vouchertracker.domain.dto.AccountDto;
import vouchertracker.domain.entity.Account;
import vouchertracker.service.AccountService;

@Controller
@PreAuthorize("hasAuthority('SUPERUSER')")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listAll(Model model) {
        model.addAttribute("users", accountService.findAll());

        return "users";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String viewOne(Model model, @PathVariable("id") String id) {
        model.addAttribute("dto", accountService.getDtoForAccount(id));

        return "user";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String createOrUpdate(
            @ModelAttribute("dto") @Valid AccountDto dto,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (!result.hasErrors()) {
            Account account = accountService.registerOrUpdateAccount(dto);

            if (account == null) result.rejectValue("email", "",
                    "This email address is already reserved for another user");
        }

        if (result.hasErrors()) return "user";

        redirectAttrs.addFlashAttribute("success", "Hooray! Operation successful!");

        return "redirect:/users";
    }

}