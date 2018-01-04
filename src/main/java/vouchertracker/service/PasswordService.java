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
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.VerificationToken;
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

    public boolean sendResetToken(String email) {
        Account account = accountRepository.findByEmailIgnoreCase(email.trim());
        VerificationToken token = setTokenToAccount(account);

        return mailService.sendTokenByEmail(account, token);
    }

    public boolean verifyResetToken(String accountId, String tokenValue) {
        VerificationToken token = tokenRepository.findByAccountId(accountId);

        if (token == null) return false;
        if (!token.getToken().equals(tokenValue) ||
                LocalDateTime.now().isAfter(token.getExpiresOn())) return false;

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        token.getAccount(),
                        null,
                        Arrays.asList(new SimpleGrantedAuthority("RESET_PASSWORD"))));

        return true;
    }

    private VerificationToken setTokenToAccount(Account account) {
        VerificationToken token = tokenRepository.findByAccountId(account.getId());

        if (token != null) tokenRepository.delete(token);

        token = new VerificationToken();
        token.setAccount(account);

        return tokenRepository.save(token);
    }

    public void deleteTokenForAccount(String accountId) {
        VerificationToken token = tokenRepository.findByAccountId(accountId);
        tokenRepository.delete(token);
    }

}