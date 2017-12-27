package vouchertracker.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vouchertracker.domain.AccountForm;
import vouchertracker.service.AccountService;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    // preauthorize
    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", accountService.findAll());

        return "users";
    }

    // preauthorize
    @GetMapping("/users/new")
    public String emptyForm(@ModelAttribute AccountForm accountForm) {
        return "user";
    }

    // preauthorize
    @PostMapping("/users/new")
    public String add(
            @Valid @ModelAttribute AccountForm accountForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "user";

        this.accountService.register(
                accountForm.getFirstName(),
                accountForm.getLastName(),
                accountForm.getEmail(),
                "qwerty", // <-- temporary .......
                accountForm.isAdministrator()
        );

        return "redirect:/users";
    }

}