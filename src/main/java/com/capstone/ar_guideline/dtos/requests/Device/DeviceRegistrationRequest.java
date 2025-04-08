package com.capstone.ar_guideline.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRegistrationRequest {

    private String token;

    private String userId;

    private String companyId;
}