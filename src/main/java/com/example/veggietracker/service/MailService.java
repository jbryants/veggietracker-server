package com.example.veggietracker.service;

import com.example.veggietracker.exceptions.VeggieTrackerException;
import com.example.veggietracker.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    // @Async is pretty cool, a separate thread will be assigned the task of sending the email,
    // when the current executing thread can respond back to the client.
    // However reliability is not guaranteed as the application server with all the threads in it
    // can be a Single point of failure.
    // if you want reliability, go with a message queue instead.
    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("pinnochiopizza19d@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new VeggieTrackerException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

}
