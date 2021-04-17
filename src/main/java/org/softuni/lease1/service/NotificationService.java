package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.NotificationServiceModel;

public interface NotificationService {
    NotificationServiceModel createNotification(String type, String message);
    NotificationServiceModel findNotificationByType(String type);

}
