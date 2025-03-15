package com.capstone.ar_guideline.controllers;

import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import com.capstone.ar_guideline.entities.ServicePrice;
import com.capstone.ar_guideline.services.impl.ServicePriceService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service-prices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServicePriceController {

  ServicePriceService servicePriceService;

  @PostMapping
  public ApiResponse<ServicePrice> createServicePrice(
      @RequestBody @Valid ServicePrice servicePrice) {
    ServicePrice createdServicePrice = servicePriceService.create(servicePrice);
    return ApiResponse.<ServicePrice>builder()
        .result(createdServicePrice)
        .message("Service price created successfully")
        .build();
  }

  @PutMapping("/{id}")
  public ApiResponse<ServicePrice> updateServicePrice(
      @PathVariable String id, @RequestBody @Valid ServicePrice servicePriceDetails) {
    ServicePrice updatedServicePrice = servicePriceService.update(id, servicePriceDetails);
    return ApiResponse.<ServicePrice>builder()
        .result(updatedServicePrice)
        .message("Service price updated successfully")
        .build();
  }

  @GetMapping("/{id}")
  public ApiResponse<ServicePrice> getServicePriceById(@PathVariable String id) {
    ServicePrice servicePrice = servicePriceService.getById(id);
    return ApiResponse.<ServicePrice>builder()
        .result(servicePrice)
        .message("Service price retrieved successfully")
        .build();
  }

  @GetMapping
  public ApiResponse<List<ServicePrice>> getAllServicePrices() {
    List<ServicePrice> servicePrices = servicePriceService.getAll();
    return ApiResponse.<List<ServicePrice>>builder()
        .result(servicePrices)
        .message("All service prices retrieved successfully")
        .build();
  }

  @DeleteMapping("/{id}")
  public ApiResponse<Void> deleteServicePriceById(@PathVariable String id) {
    servicePriceService.deleteById(id);
    return ApiResponse.<Void>builder().message("Service price deleted successfully").build();
  }
}
