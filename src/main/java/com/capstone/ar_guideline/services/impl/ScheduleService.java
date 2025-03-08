package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.CompanySubscription;
import com.capstone.ar_guideline.entities.User;
import com.capstone.ar_guideline.payos.CreatePaymentLinkRequestBody;
import com.capstone.ar_guideline.repositories.CompanySubscriptionRepository;
import com.capstone.ar_guideline.repositories.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    EmailService emailService;
    UserRepository userRepository;
    CompanySubscriptionRepository companySubscriptionRepository;
    @Lazy
    PayOsService payOsService;

    @Scheduled(cron = "0 0 12 * * ?") // Runs every day at 12:00 PM
    public void sendSubscriptionExpiryReminder() {
        LocalDateTime reminderDate = LocalDateTime.now().plusDays(3); // 7 days before expiry
        List<CompanySubscription> expiringSubscriptions = companySubscriptionRepository.findBySubscriptionExpireDateThatOnlyHave3DaysLeft(reminderDate);

        for (CompanySubscription companySubscription : expiringSubscriptions) {
            User user = userRepository.findUserByCompanyId(companySubscription.getId());
            ObjectNode objectNode = payOsService.createPaymentLink(CreatePaymentLinkRequestBody.builder().productName(companySubscription.getSubscription().getSubscriptionCode()).userId(user.getId()).build());
            emailService.sendSubscriptionReminderEmail(
                    user.getEmail(),
                    user.getCompany().getCompanyName(),
                    companySubscription.getSubscriptionExpireDate(),
                    objectNode.get("data").get("checkoutUrl").asText()
            );
        }
    }
}
