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
import vouchertracker.domain.Account;
import vouchertracker.domain.AccountDto;
import vouchertracker.repository.AccountRepository;
import vouchertracker.service.AccountService;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SUPERUSER')")
    public String listAccounts(Model model) {
        model.addAttribute("users", accountRepository.findAll());

        return "users";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SUPERUSER')")
    public String editAccount(Model model, @PathVariable("id") String id) {
        model.addAttribute("dto", accountService.getDtoForAccount(id));

        return "user";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SUPERUSER')")
    public String addOrUpdateAccount(
            @ModelAttribute("dto") @Valid AccountDto dto,
            BindingResult result
    ) {
        if (!result.hasErrors()) {
            Account account = accountService.registerOrUpdateAccount(dto);

            if (account == null) result.rejectValue("email", "",
                    "This email address is already reserved for another user");
        }

        if (result.hasErrors()) return "user";

        return "redirect:/users";
    }

}