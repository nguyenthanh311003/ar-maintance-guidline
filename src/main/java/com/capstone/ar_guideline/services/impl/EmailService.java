package com.capstone.ar_guideline.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailService {
  private final JavaMailSender mailSender;

  public void sendAccountActivationEmail(String toEmail, String userName) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("Account Activation");
      message.setText(
          "Hello "
              + userName
              + ",\n\nYour account has been activated successfully. You can now log in and use our services.\n\nThank you!");
      mailSender.send(message);
      log.info("Account activation email sent to {}", toEmail);
    } catch (Exception e) {
      log.error("Failed to send activation email to {}: {}", toEmail, e.getMessage());
    }
  }

  public void sendAccountRejectionEmail(String toEmail, String userName) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("Account Rejection");
      message.setText(
          "Hello "
              + userName
              + ",\n\nWe regret to inform you that your account registration has been rejected. If you have any questions or need further clarification, please contact our support team.\n\nThank you!");
      mailSender.send(message);
      log.info("Account rejection email sent to {}", toEmail);
    } catch (Exception e) {
      log.error("Failed to send rejection email to {}: {}", toEmail, e.getMessage());
    }
  }
  public void sendSubscriptionReminderEmail(String toEmail, String companyName, LocalDateTime expiryDate,String linkPayment) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("Subscription Expiry Reminder - 7 Days Left");
      message.setText(
              "Hello " + companyName + ",\n\n"
                      + "Your subscription is set to expire on " + expiryDate + ".\n"
                      + "Please renew your subscription to avoid service interruptions.\n\n"
                      + "This is payment link: "+linkPayment+".\n\n"
                      + "Thank you!");

      mailSender.send(message);
      log.info("Subscription expiry reminder email sent to {}", toEmail);
    } catch (Exception e) {
      log.error("Failed to send subscription reminder email to {}: {}", toEmail, e.getMessage());
    }
  }


}
