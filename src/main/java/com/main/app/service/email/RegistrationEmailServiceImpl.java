package com.main.app.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RegistrationEmailServiceImpl implements RegistrationEmailService {

    private static final String EMAIL_SUBJECT = "Potvrda Registracije!";

    private static final String MESSAGE_BEFORE =
            "Pozdrav, naš administratorski tim je dodao i sačuvao vaš korisničlo nalog. <br/>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp; Potvrda registracije je moguća na donjem linku.";

    private static final String MESSAGE_AFTER =
            "";

    private EmailClient emailClient;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public RegistrationEmailServiceImpl(EmailClient emailClient, MailContentBuilder mailContentBuilder) {
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
