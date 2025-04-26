//package com.capstone.ar_guideline.entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.UUID;
//
//@Entity
//@Table
//@Getter
//@Setter
//public class Notification {
//
//    @Id
//    @GeneratedValue
//    private UUID id;
//    private String title;
//    private String content;
//    private String type;
//    private String key;
//    private String status;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private String createdAt;
//    private String updatedAt;
//
//    // Constructors, getters, and setters
//}
