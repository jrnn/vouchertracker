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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (!result.hasErrors()) {
            String accountStatus = accountService.checkAccountStatus(dto.getEmail());

            if (accountStatus != null) result.rejectValue("email", "", accountStatus);
        }

        if (result.hasErrors()) return "password_forgot";

        boolean mailSent = passwordService.sendResetToken(dto.getEmail());

        if (mailSent) redirectAttrs.addFlashAttribute("success",
                "Verification token for resetting password successfully sent");
        else redirectAttrs.addFlashAttribute("failure",
                "Something went wrong, please try again or contact system administrator");

        return "redirect:/login";
    }

    @RequestMapping(value = "/login/reset", method = RequestMethod.GET)
    public String verifyResetToken(
            @RequestParam("id") String id,
            @RequestParam("token") String token,
            RedirectAttributes redirectAttrs
    ) {
        boolean isTokenValid = passwordService.verifyResetToken(id, token);

        if (!isTokenValid) {
            redirectAttrs.addFlashAttribute("failure", "Oops! Invalid or expired token!");
            return "redirect:/login";
        }

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
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) return "password_reset";

        Account account = (Account) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        passwordService.changePassword(account, dto.getNewPassword());
        SecurityContextHolder.getContext().setAuthentication(null);
        redirectAttrs.addFlashAttribute("success", "Hooray! Password successfully changed!");

        return "redirect:/login";
    }

}