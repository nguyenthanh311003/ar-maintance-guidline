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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeviceManagementServiceImpl implements IDeviceManagementService {

    UserRepository userRepository;
    private static final String DEVICE_SEPARATOR = "@";
   // private static final int MAX_DEVICES = 5; // Maximum number of devices allowed per user

    @Override
    public boolean registerDeviceForUser(String userId, String deviceId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOptional.get();
        String currentDeviceIds = user.getDeviceId();
        List<String> deviceIds = new ArrayList<>();

        // If user already has devices registered
        if (currentDeviceIds != null && !currentDeviceIds.isEmpty()) {
            deviceIds = new ArrayList<>(Arrays.asList(currentDeviceIds.split(DEVICE_SEPARATOR)));

            // Check if device is already registered
            if (deviceIds.contains(deviceId)) {
                log.info("Device ID {} is already registered for user {}", deviceId, userId);
                return true;
            }

//            // Check if maximum number of devices is reached
//            if (deviceIds.size() >= MAX_DEVICES) {
//                log.warn("User {} has reached maximum number of devices ({})", userId, MAX_DEVICES);
//                throw new AppException(ErrorCode.MAX_DEVICES_REACHED);
//            }
        }

        // Add new device ID
        deviceIds.add(deviceId);

        // Update user with new device list
        user.setDeviceId(String.join(DEVICE_SEPARATOR, deviceIds));
        userRepository.save(user);

        log.info("Added device ID {} to user {}. Total devices: {}", deviceId, userId, deviceIds.size());
        return true;
    }

    @Override
    public boolean unregisterDeviceForUser(String userId, String deviceId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOptional.get();
        String currentDeviceIds = user.getDeviceId();

        if (currentDeviceIds == null || currentDeviceIds.isEmpty()) {
            log.info("User {} has no registered devices", userId);
            return false;
        }

        List<String> deviceIds = new ArrayList<>(Arrays.asList(currentDeviceIds.split(DEVICE_SEPARATOR)));

        if (!deviceIds.contains(deviceId)) {
            log.info("Device ID {} is not registered for user {}", deviceId, userId);
            return false;
        }

        // Remove the device ID
        deviceIds.remove(deviceId);

        // Update user with new device list
        user.setDeviceId(deviceIds.isEmpty() ? null : String.join(DEVICE_SEPARATOR, deviceIds));
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
        String currentDeviceIds = user.getDeviceId();

        if (currentDeviceIds == null || currentDeviceIds.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.asList(currentDeviceIds.split(DEVICE_SEPARATOR));
    }

    @Override
    public boolean isDeviceRegisteredForUser(String userId, String deviceId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        String currentDeviceIds = user.getDeviceId();

        if (currentDeviceIds == null || currentDeviceIds.isEmpty()) {
            return false;
        }

        List<String> deviceIds = Arrays.asList(currentDeviceIds.split(DEVICE_SEPARATOR));
        return deviceIds.contains(deviceId);
    }
}