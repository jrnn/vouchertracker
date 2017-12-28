package vouchertracker.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String listAll(Model model) {
        model.addAttribute("users", accountRepository.findAll());

        return "users";
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SUPERUSER')")
    public String emptyForm(@ModelAttribute AccountDto accountDto) {
        return "user";
    }

    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SUPERUSER')")
    public String addNew(
            @ModelAttribute("accountDto") @Valid AccountDto accountDto,
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            Account account = accountService.registerNewUser(accountDto);

            if (account == null) bindingResult.rejectValue("email", "",
                    "This email address is already in use by another user");
        }

        if (bindingResult.hasErrors()) return "user";

        return "redirect:/users";
    }

}