package org.example.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private String firebaseUrl = "T:/Utill/Firebase/serviceAccountKey.json";
    @PostConstruct
    public void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseUrl);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase has been initialized successfully.");
        }
    }
}
