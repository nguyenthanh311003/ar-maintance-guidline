package com.capstone.ar_guideline.configurations;

import com.capstone.ar_guideline.entities.PointRequest;
import com.capstone.ar_guideline.repositories.PointRequestRepository;
import jakarta.persistence.PrePersist;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PointRequestListener {

  private static PointRequestRepository pointRequestRepository;

  @Autowired
  public void init(PointRequestRepository repository) {
    pointRequestRepository = repository;
  }

  @PrePersist
  public void prePersist(PointRequest request) {
    if (request.getRequestNumber() == null) {
      List<String> existingNumbers = pointRequestRepository.findAllRequestNumbers();
      String randomNumber;

      do {
        randomNumber = generateRandomNineDigitNumber();
      } while (existingNumbers != null && existingNumbers.contains(randomNumber));

      request.setRequestNumber(randomNumber);
    }
  }

  private String generateRandomNineDigitNumber() {
    Random random = new Random();
    // Generate a number between 100,000,000 and 999,999,999
    int number = 100_000_000 + random.nextInt(900_000_000);
    return String.valueOf(number);
  }
}
