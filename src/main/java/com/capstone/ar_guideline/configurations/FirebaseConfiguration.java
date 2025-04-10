package com.capstone.ar_guideline.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfiguration {

  @Bean
  public FirebaseApp firebaseApp() throws IOException {
    // Load the file from classpath (resources folder)
    FileInputStream serviceAccount = new FileInputStream("ar-firebase.json");

    FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    return FirebaseApp.initializeApp(options);
  }
}
