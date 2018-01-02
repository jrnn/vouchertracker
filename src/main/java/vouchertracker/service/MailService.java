package vouchertracker.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vouchertracker.domain.entity.Account;
import vouchertracker.domain.entity.VerificationToken;

@Service
public class MailService {

    private String appUrl;

    @Autowired
    private JavaMailSender mailSender;

    @PostConstruct
    public void init() {
        this.appUrl = System.getenv("HEROKU_APP_PATH"); // heroku config var
        if (appUrl == null) this.appUrl = "http://localhost:8080";
    }

    public void sendTokenByEmail(Account account, VerificationToken token) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(account.getEmail());
        message.setSubject("PvTracker | Password reset request");
        message.setText("Hello " + account.getFirstName() +
                "!\r\n\r\nTo reset your PvTracker password, follow this link: " +
                appUrl + "/login/reset?id=" + account.getId() + "&token=" + token.getToken() +
                "\r\n\r\nIf you have not requested a password reset, please disregard this email.");

        mailSender.send(message);
    }

}