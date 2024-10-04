package com.ms.notification.dtos;

import java.util.UUID;

public record NotificationDto(
        UUID userId,
        String emailTo,
        String subject,
        String text
) {
}
