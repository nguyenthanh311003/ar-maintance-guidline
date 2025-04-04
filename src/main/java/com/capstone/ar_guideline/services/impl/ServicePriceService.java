package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.ServicePrice;
import com.capstone.ar_guideline.repositories.ServicePricerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePriceService {

  @Autowired private ServicePricerRepository servicePricerRepository;

  public ServicePrice create(ServicePrice servicePrice) {
    if (servicePricerRepository.existsByName(servicePrice.getName())) {
      throw new RuntimeException("ServicePrice with the same name already exists");
    }
    return servicePricerRepository.save(servicePrice);
  }

  public ServicePrice update(String id, ServicePrice servicePriceDetails) {
    Optional<ServicePrice> optionalServicePrice = servicePricerRepository.findById(id);
    if (optionalServicePrice.isPresent()) {
      ServicePrice servicePrice = optionalServicePrice.get();
      if (!servicePrice.getName().equals(servicePriceDetails.getName())
          && servicePricerRepository.existsByName(servicePriceDetails.getName())) {
        throw new RuntimeException("ServicePrice with the same name already exists");
      }
      servicePrice.setName(servicePriceDetails.getName());
      servicePrice.setPrice(servicePriceDetails.getPrice());
      return servicePricerRepository.save(servicePrice);
    } else {
      throw new RuntimeException("ServicePrice not found");
    }
  }

  public ServicePrice getById(String id) {
    return servicePricerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("ServicePrice not found"));
  }

  public List<ServicePrice> getAll() {
    return servicePricerRepository.findAll();
  }

  public void deleteById(String id) {
    if (servicePricerRepository.existsById(id)) {
      servicePricerRepository.deleteById(id);
    } else {
      throw new RuntimeException("ServicePrice not found");
    }
  }
}
