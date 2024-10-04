package com.ms.notification.consumer;

import com.ms.notification.dtos.NotificationDto;
import com.ms.notification.models.NotificationModel;
import com.ms.notification.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${broker.queue.notification.name}")
    public void listenNotificationQueue(@Payload NotificationDto notificationDto) {
        var notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationDto, notificationModel);
        notificationService.sendMail(notificationModel);
    }
}
