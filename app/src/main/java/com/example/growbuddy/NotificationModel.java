package com.example.growbuddy;

public class NotificationModel {
    private String notification;
    private String notificationDetails;

    public NotificationModel(String notification, String notificationDetails) {
        this.notification = notification;
        this.notificationDetails = notificationDetails;
    }

    public String getNotification() {
        return notification;
    }

    public String getNotificationDetails() {
        return notificationDetails;
    }
}
