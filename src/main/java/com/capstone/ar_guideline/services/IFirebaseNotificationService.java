package com.capstone.ar_guideline.services;


import com.capstone.ar_guideline.dtos.requests.Devices.DeviceRegistrationRequest;

public interface IFirebaseNotificationService {
    Boolean registerDeviceToken(DeviceRegistrationRequest request);
    Boolean subscribeToTopic(String token, String topic);
    Boolean unsubscribeFromTopic(String token, String topic);
    String sendNotificationToTopic(String topic, String title, String body, String data);
    String sendNotificationToToken(String token, String title, String body, String data);
}