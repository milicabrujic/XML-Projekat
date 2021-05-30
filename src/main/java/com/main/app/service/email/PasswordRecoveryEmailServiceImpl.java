package com.main.app.service.email;

import com.main.app.static_data.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryEmailServiceImpl implements PasswordRecoveryEmailService {

    private static final String EMAIL_SUBJECT = "Zaboravljena lozinka";

    private static final String MESSAGE_BEFORE =
            "Poštovani, zatražili ste resetovanje vaše lozinke. Kliknite na link ispod kako biste je resetovali.";

    private static final String MESSAGE_AFTER =
            "Ukoliko vi niste zatražili resetovanje lozinke, molimo vas da ignorišete ovaj email ili nas obavestite o istom. " +
                    " Link za resetovanje vaše lozinke će biti validan u narednih " + Constants.VALIDITY_OF_TOKEN_IN_DAYS + " dana.";

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public PasswordRecoveryEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
        this.emailClient = emailClient;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Override
    @Async("processExecutor")
    public void sendEmail(String url, String pathParam, String emailFrom, String emailTo, String urlPart) {
        String link = url + urlPart + pathParam;

        String content = mailContentBuilder.buildContentWithLink(link, MESSAGE_BEFORE, MESSAGE_AFTER);

        emailClient.sendMimeEmail(
                emailFrom,
                emailTo,
                EMAIL_SUBJECT,
                content
        );
    }
}
