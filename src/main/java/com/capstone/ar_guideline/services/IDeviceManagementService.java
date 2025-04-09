
package com.capstone.ar_guideline.services;

import java.util.List;

public interface IDeviceManagementService {
    /**
     * Registers a device ID for a user
     * @param userId User ID
     * @param deviceId Device ID to register
     * @return true if successfully registered
     */
    boolean registerDeviceForUser(String userId, String deviceId);

    /**
     * Unregisters a device ID from a user
     * @param userId User ID
     * @param deviceId Device ID to unregister
     * @return true if successfully unregistered
     */
    boolean unregisterDeviceForUser(String userId, String deviceId);

    /**
     * Gets all devices registered to a user
     * @param userId User ID
     * @return List of device IDs
     */
    List<String> getUserDevices(String userId);

    /**
     * Checks if a device is registered for a user
     * @param userId User ID
     * @param deviceId Device ID to check
     * @return true if device is registered for user
     */
    boolean isDeviceRegisteredForUser(String userId, String deviceId);
}