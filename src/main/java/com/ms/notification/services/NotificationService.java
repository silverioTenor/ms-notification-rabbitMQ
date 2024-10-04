package com.ms.notification.services;

import com.ms.notification.enums.StatusNotification;
import com.ms.notification.models.NotificationModel;
import com.ms.notification.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    final NotificationRepository notificationRepository;
    final JavaMailSender notificationSender;

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender notificationSender) {
        this.notificationRepository = notificationRepository;
        this.notificationSender = notificationSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendMail(NotificationModel notificationModel) {
        try {
            notificationModel.setSendDateNotification(LocalDateTime.now());
            notificationModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notificationModel.getEmailTo());
            message.setSubject(notificationModel.getSubject());
            message.setText(notificationModel.getText());
            notificationSender.send(message);

            notificationModel.setStatusNotification(StatusNotification.SENT);
        } catch (MailException e) {
            notificationModel.setStatusNotification(StatusNotification.ERROR);
        }

        notificationRepository.save(notificationModel);
    }
}
