package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.constants.ConstAPI;
import com.capstone.ar_guideline.dtos.requests.Devices.DeviceRequest;
import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.services.IDeviceManagementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeviceManagementController {

    IDeviceManagementService deviceManagementService;

    @PostMapping(value = ConstAPI.DeviceAPI.REGISTER_DEVICE)
    public ApiResponse<Boolean> registerDevice(@RequestBody DeviceRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(deviceManagementService.registerDeviceForUser(request.getUserId(), request.getDeviceId()))
                .message("Device registered successfully")
                .build();
    }

    @PostMapping(value = ConstAPI.DeviceAPI.UNREGISTER_DEVICE)
    public ApiResponse<Boolean> unregisterDevice(@RequestBody DeviceRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(deviceManagementService.unregisterDeviceForUser(request.getUserId(), request.getDeviceId()))
                .message("Device unregistered successfully")
                .build();
    }

    @GetMapping(value = ConstAPI.DeviceAPI.GET_USER_DEVICES + "{userId}")
    public ApiResponse<List<String>> getUserDevices(@PathVariable String userId) {
        return ApiResponse.<List<String>>builder()
                .result(deviceManagementService.getUserDevices(userId))
                .message("User devices retrieved successfully")
                .build();
    }

    @PostMapping(value = ConstAPI.DeviceAPI.CHECK_DEVICE)
    public ApiResponse<Boolean> checkDevice(@RequestBody DeviceRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(deviceManagementService.isDeviceRegisteredForUser(request.getUserId(), request.getDeviceId()))
                .message("Device check completed")
                .build();
    }
}