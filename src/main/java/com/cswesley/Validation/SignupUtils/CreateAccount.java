package com.cswesley.Validation.SignupUtils;

import com.cswesley.Utils.EncryptDecrypt;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreateAccount {

    public boolean checkIfExists(String username, String email) throws IOException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query = db.collection("users").get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            if (document.get("username").equals(username)) {
                return true;
            } else if (document.get("email").equals(email)) {
                return true;
            }
        }

        return false;
    }

    public void createAccount(String username, String password, String email) throws IOException {
        Firestore db = FirestoreClient.getFirestore();

        String encryptedPassword = EncryptDecrypt.encrypt(password);

        DocumentReference docRef = db.collection("users").document(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", encryptedPassword);
        data.put("email", email);

        docRef.set(data);
    }
}
