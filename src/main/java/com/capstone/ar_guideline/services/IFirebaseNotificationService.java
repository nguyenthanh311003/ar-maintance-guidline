package com.capstone.ar_guideline.services;

import com.capstone.ar_guideline.dtos.requests.Devices.DeviceRegistrationRequest;

import java.util.List;

public interface IFirebaseNotificationService {
  /**
   * Register a device token for push notifications
   * @param request The device registration request
   * @return True if registration was successful
   */
  Boolean registerDeviceToken(DeviceRegistrationRequest request);

  /**
   * Subscribe a device token to a topic
   * @param token The FCM token
   * @param topic The topic name
   * @return True if subscription was successful
   */
  Boolean subscribeToTopic(String token, String topic);

  /**
   * Unsubscribe a device token from a topic
   * @param token The FCM token
   * @param topic The topic name
   * @return True if unsubscription was successful
   */
  Boolean unsubscribeFromTopic(String token, String topic);

  /**
   * Send a notification to a topic
   * @param topic The topic name
   * @param title The notification title
   * @param body The notification body
   * @param data Additional data in format "key1:value1,key2:value2"
   * @return The message ID
   */
  String sendNotificationToTopic(String topic, String title, String body, String data);

  /**
   * Send a notification to a specific token
   * @param token The FCM token
   * @param title The notification title
   * @param body The notification body
   * @param data Additional data in format "key1:value1,key2:value2"
   * @return The message ID
   */
  String sendNotificationToToken(String token, String title, String body, String data);

  /**
   * Send a notification to all devices of a user
   * @param userId The user ID
   * @param title The notification title
   * @param body The notification body
   * @param data Additional data in format "key1:value1,key2:value2"
   * @return List of message IDs for each device
   */
  List<String> sendNotificationToUser(String userId, String title, String body, String data);

  /**
   * Unregister a device token and remove it from user's devices
   * @param userId The user ID
   * @param token The FCM token to remove
   * @return True if unregistration was successful
   */
  Boolean unregisterDeviceToken(String userId, String token);
}