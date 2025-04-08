package com.capstone.ar_guideline.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Load the file from classpath (resources folder)
        InputStream serviceAccount = getClass()
                .getClassLoader()
                .getResourceAsStream("google-services.json");

        if (serviceAccount == null) {
            throw new IOException("google-services.json not found in resources folder.");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
