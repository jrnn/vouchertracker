package vouchertracker.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vouchertracker.domain.Account;
import vouchertracker.domain.VerificationToken;
import vouchertracker.repository.AccountRepository;
import vouchertracker.repository.VerificationTokenRepository;

@Service
public class PasswordService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;

    public void changePassword(Account account, String password) {
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    public String getRandomPassword() {
        return passwordEncoder.encode(
                UUID.randomUUID().toString().replace("-", ""));
    }

    public void sendResetToken(String email) {
        Account account = accountRepository.findByEmailIgnoreCase(email);
        VerificationToken token = setTokenToAccount(account);
        mailService.sendTokenByEmail(account, token);
    }

    public String verifyResetToken(String accountId, String tokenValue) {
        VerificationToken token = tokenRepository.findByToken(tokenValue);

        // error msg would be nice FOR ALL OF THESE
        if (token == null) return "Invalid token!";
        if (!token.getAccount().getId().equals(accountId)) return "Invalid token!";
        if (LocalDateTime.now().isAfter(token.getExpiresOn())) return "Token has expired!";

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        token.getAccount(),
                        null,
                        Arrays.asList(new SimpleGrantedAuthority("RESET_PASSWORD"))));

        tokenRepository.delete(token);
        return null;
    }

    private VerificationToken setTokenToAccount(Account account) {
        VerificationToken token = tokenRepository.findByAccountId(account.getId());

        if (token != null) tokenRepository.delete(token);

        token = new VerificationToken();
        token.setAccount(account);
        return tokenRepository.save(token);
    }

}