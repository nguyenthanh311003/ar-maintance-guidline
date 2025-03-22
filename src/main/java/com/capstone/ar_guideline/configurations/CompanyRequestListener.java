package com.capstone.ar_guideline.configurations;

import com.capstone.ar_guideline.entities.CompanyRequest;
import com.capstone.ar_guideline.repositories.CompanyRequestRepository;
import jakarta.persistence.PrePersist;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyRequestListener {

  private static CompanyRequestRepository companyRequestRepository;

  @Autowired
  public void init(CompanyRequestRepository repository) {
    companyRequestRepository = repository;
  }

  @PrePersist
  public void prePersist(CompanyRequest request) {
    if (request.getRequestNumber() == null) {
      List<String> existingNumbers = companyRequestRepository.findAllRequestNumbers();
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
