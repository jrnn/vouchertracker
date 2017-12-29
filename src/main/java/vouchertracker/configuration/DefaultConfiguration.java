package vouchertracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Profile("default")
public class DefaultConfiguration {

    @Bean
    public JavaMailSender mailSender() {
        // not supported in sandbox
        return new JavaMailSenderImpl();
    }

}