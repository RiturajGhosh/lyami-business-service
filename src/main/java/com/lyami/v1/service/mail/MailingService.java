package com.lyami.v1.service.mail;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailingService {

    void sendEmail(String to, String from, String subject, String content) throws MessagingException;
}
