package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.entities.CompanyRequest;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EmailService {
  @Value("${frontend.url}")
  private String frontEndHost;

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

  public void sendSubscriptionReminderEmail(
      String toEmail, String companyName, LocalDateTime expiryDate, String linkPayment) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("Subscription Expiry Reminder - 7 Days Left");
      message.setText(
          "Hello "
              + companyName
              + ",\n\n"
              + "Your subscription is set to expire on "
              + expiryDate
              + ".\n"
              + "Please renew your subscription to avoid service interruptions.\n\n"
              + "This is payment link: "
              + linkPayment
              + ".\n\n"
              + "Thank you!");

      mailSender.send(message);
      log.info("Subscription expiry reminder email sent to {}", toEmail);
    } catch (Exception e) {
      log.error("Failed to send subscription reminder email to {}: {}", toEmail, e.getMessage());
    }
  }

  public void sendActiveAccountToCompany(String toEmail, String companyName, String password) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("AR Guideline - Account Created");
      message.setText(
          "Hello "
              + companyName
              + ",\n\n"
              + "Your account has been created by admin platform.\n\n"
              + "Email: "
              + toEmail
              + ".\n"
              + "Password: "
              + password
              + ".\n\n"
              + "Please login to the platform and first subscribe the best plan for you.\n\n"
              + "URL: "
              + frontEndHost
              + ".\n\n"
              + "Thank you!");

      mailSender.send(message);
      log.info("Subscription expiry reminder email sent to {}", toEmail);
    } catch (Exception e) {
      log.error("Failed to send subscription reminder email to {}: {}", toEmail, e.getMessage());
    }
  }

  public void sendAssignGuidelineEmail(String toEmail, String managerEmail, String courseName) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("AR Guideline - New Guideline Assigned!");
      message.setText(
          "You has been assigned to new course "
              + courseName
              + " by "
              + managerEmail
              + ".\n\n"
              + "Please login to the AR guideline mobile app and process your guideline.\n\n"
              + "\n\n"
              + "Thank you!");

      mailSender.send(message);
    } catch (Exception e) {
      log.error("Failed to send assigned  reminder email to {}: {}", toEmail, e.getMessage());
    }
  }

  public void sendCompanyRequestEmail(String toEmail, CompanyRequest companyRequest) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("AR Guideline - Request Approved!");

      String designerName =
          companyRequest.getDesigner() != null
              ? companyRequest.getDesigner().getEmail()
              : "a designer";

      message.setText(
          "Good news! Your request \""
              + companyRequest.getRequestSubject()
              + "\" has been approved by "
              + designerName
              + ".\n\n"
              + "You can now contact with the designer to discuss about new asset model.\n\n"
              + "Thank you for using our platform!");

      mailSender.send(message);
    } catch (Exception e) {
      log.error("Failed to send approval notification email to {}: {}", toEmail, e.getMessage());
    }
  }

  public void sendDesignerCancelledEmail(String toEmail, CompanyRequest companyRequest) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("AR Guideline - Request Cancelled by Designer");

      String designerName =
          companyRequest.getDesigner() != null
              ? companyRequest.getDesigner().getEmail()
              : "the assigned designer";

      message.setText(
          "Your request \""
              + companyRequest.getRequestSubject()
              + "\" has been cancelled by "
              + designerName
              + ".\n\n"
              + "Your request will be reopened for other designers to pick up. "
              + "You'll be notified when a new designer accepts your request.\n\n"
              + "Thank you for using our platform!");

      mailSender.send(message);
      log.info("Designer cancellation email sent to {}", toEmail);
    } catch (Exception e) {
      log.error("Failed to send designer cancellation email to {}: {}", toEmail, e.getMessage());
    }
  }

  public void sendRequesterCancelledEmail(String toEmail, CompanyRequest companyRequest) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("AR Guideline - Request Cancelled by Requester");

      String requesterName =
          companyRequest.getRequester() != null
              ? companyRequest.getRequester().getEmail()
              : "the requester";

      message.setText(
          "The request \""
              + companyRequest.getRequestSubject()
              + "\" has been cancelled by "
              + requesterName
              + ".\n\n"
              + "The request will no longer be available in your tasks. "
              + "Thank you for your participation.\n\n"
              + "Thank you for using our platform!");

      mailSender.send(message);
      log.info("Requester cancellation email sent to designer {}", toEmail);
    } catch (Exception e) {
      log.error(
          "Failed to send requester cancellation email to designer {}: {}",
          toEmail,
          e.getMessage());
    }
  }
}
