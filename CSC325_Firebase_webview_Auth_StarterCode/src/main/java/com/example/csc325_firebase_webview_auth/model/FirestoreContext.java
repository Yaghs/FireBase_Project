package com.example.csc325_firebase_webview_auth.model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;

/**
 *
 * @author MoaathAlrajab
 */
public class FirestoreContext {
    public FirestoreContext() {
    }

    public Firestore firebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) { // If no FirebaseApp initialized
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("/files/key.json")))
                        .setStorageBucket("csc325yaghinlooweek6demo1.appspot.com")
                        .setProjectId("csc325yaghinlooweek6demo1") // Ensure this matches your Firebase project ID
                        .build();
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to initialize FirebaseApp", ex);
        }
        return FirestoreClient.getFirestore();
    }

}
