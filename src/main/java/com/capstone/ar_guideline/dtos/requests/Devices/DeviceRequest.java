package com.capstone.ar_guideline.dtos.requests.Devices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest {
    private String userId;
    private String deviceId;
}