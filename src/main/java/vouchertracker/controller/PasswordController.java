package vouchertracker.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vouchertracker.domain.Account;
import vouchertracker.domain.EmailDto;
import vouchertracker.domain.PasswordDto;
import vouchertracker.service.AccountService;
import vouchertracker.service.PasswordService;

@Controller
public class PasswordController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/login/forgot", method = RequestMethod.GET)
    public String forgotPassword(@ModelAttribute("dto") EmailDto dto) {
        return "password_forgot";
    }

    @RequestMapping(value = "/login/forgot", method = RequestMethod.POST)
    public String requestPasswordReset(
            @ModelAttribute("dto") @Valid EmailDto dto,
            BindingResult result
    ) {
        if (!result.hasErrors() && !accountService.emailExists(dto.getEmail())) result
                    .rejectValue("email", "", "No user has registered with this email");

        if (result.hasErrors()) return "password_forgot";

        passwordService.sendResetToken(dto.getEmail().trim().toLowerCase());
        return "redirect:/login"; // success msg would be nice ...
    }

    @RequestMapping(value = "/login/reset", method = RequestMethod.GET)
    public String verifyResetToken(
            @RequestParam("id") String id,
            @RequestParam("token") String token
    ) {
        String result = passwordService.verifyResetToken(id, token);

        if (result != null) return "redirect:/"; // error msg would be nice ...

        return "redirect:/password/reset";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('RESET_PASSWORD')")
    public String inputNewPassword(@ModelAttribute("dto") PasswordDto dto) {
        return "password_reset";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('RESET_PASSWORD')")
    public String resetPassword(
            @ModelAttribute("dto") @Valid PasswordDto dto,
            BindingResult result
    ) {
        if (result.hasErrors()) return "password_reset";

        Account account = (Account) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        passwordService.changePassword(account, dto.getNewPassword());

        return "redirect:/login"; // success msg would be nice ...
    }

}