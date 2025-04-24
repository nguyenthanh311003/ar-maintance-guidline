package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.repositories.UserRepository;
import com.capstone.ar_guideline.services.IDeviceManagementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeviceManagementServiceImpl implements IDeviceManagementService {

    UserRepository userRepository;
    private static final String DEVICE_SEPARATOR = "@";
    // Maximum number of devices allowed per user (can be uncommented if needed)
    // private static final int MAX_DEVICES = 5;

    @Override
    public boolean registerDeviceForUser(String userId, String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            log.warn("Cannot register empty device ID for user {}", userId);
            return false;
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOptional.get();
        List<String> deviceIds = getDeviceIdsFromString(user.getDeviceId());

        // Check if device is already registered
        if (deviceIds.contains(deviceId)) {
            log.info("Device ID {} is already registered for user {}", deviceId, userId);
            return true;
        }

        // Uncomment if maximum device limit needed
        // if (deviceIds.size() >= MAX_DEVICES) {
        //     log.warn("User {} has reached maximum number of devices ({})", userId, MAX_DEVICES);
        //     throw new AppException(ErrorCode.MAX_DEVICES_REACHED);
        // }

        // Add new device ID
        deviceIds.add(deviceId);

        // Update user with new device list
        user.setDeviceId(convertDeviceIdsToString(deviceIds));
        userRepository.save(user);

        log.info("Added device ID {} to user {}. Total devices: {}", deviceId, userId, deviceIds.size());
        return true;
    }

    @Override
    public boolean unregisterDeviceForUser(String userId, String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            log.warn("Cannot unregister empty device ID for user {}", userId);
            return false;
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOptional.get();
        List<String> deviceIds = getDeviceIdsFromString(user.getDeviceId());

        if (!deviceIds.contains(deviceId)) {
            log.info("Device ID {} is not registered for user {}", deviceId, userId);
            return false;
        }

        // Remove the device ID
        deviceIds.remove(deviceId);

        // Update user with new device list
        user.setDeviceId(convertDeviceIdsToString(deviceIds));
        userRepository.save(user);

        log.info("Removed device ID {} from user {}. Remaining devices: {}", deviceId, userId, deviceIds.size());
        return true;
    }

    @Override
    public List<String> getUserDevices(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOptional.get();
        return getDeviceIdsFromString(user.getDeviceId());
    }

    @Override
    public boolean isDeviceRegisteredForUser(String userId, String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            return false;
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        List<String> deviceIds = getDeviceIdsFromString(user.getDeviceId());
        return deviceIds.contains(deviceId);
    }

    /**
     * Converts a string of device IDs to a List
     *
     * @param deviceIdsString String containing device IDs separated by DEVICE_SEPARATOR
     * @return List of device IDs
     */
    private List<String> getDeviceIdsFromString(String deviceIdsString) {
        if (deviceIdsString == null || deviceIdsString.isEmpty()) {
            return new ArrayList<>();
        }

        // Filter out any empty strings that might result from splitting
        return Arrays.stream(deviceIdsString.split(DEVICE_SEPARATOR))
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Converts a List of device IDs to a string
     *
     * @param deviceIds List of device IDs
     * @return String containing device IDs separated by DEVICE_SEPARATOR
     */
    private String convertDeviceIdsToString(List<String> deviceIds) {
        if (deviceIds == null || deviceIds.isEmpty()) {
            return null;
        }
        return String.join(DEVICE_SEPARATOR, deviceIds);
    }
}