package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Notification;
import org.softuni.lease1.domain.model.service.NotificationServiceModel;
import org.softuni.lease1.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, OfferService offerService, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationServiceModel createNotification(String type, String message) {
        NotificationServiceModel model = this.findNotificationByType(type);
        if (model == null) {
            model = new NotificationServiceModel();
        }
        model.setType(type);
        model.setMessage(message);
        Notification notification = this.notificationRepository.saveAndFlush(this.modelMapper.map(model, Notification.class));
        return this.modelMapper.map(notification, NotificationServiceModel.class);
    }

    @Override
    public NotificationServiceModel findNotificationByType(String type) {
        Notification notification = this.notificationRepository.findByType(type)
                .orElse(null);
        return notification != null ? this.modelMapper.map(notification, NotificationServiceModel.class) : null;
    }

    @Scheduled(fixedRate = 300000)
    private void generateReminder() {
        int count = this.offerService.countOverdueRequest();
        String message = "";
        if (count > 0) {
            message = String.format("%d overdue requests", count);
        }
        message = this.createNotification("overdueRequests", message).getMessage();
    }
}
