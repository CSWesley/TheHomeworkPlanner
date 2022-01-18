package com.cswesley.Validation.LoginUtils;

import com.cswesley.Utils.EncryptDecrypt;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AccountLogin {

    public boolean matches(String username, String password) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        // first check if the username exists
        ApiFuture<QuerySnapshot> query = db.collection("users").get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        String encrypt = EncryptDecrypt.encrypt(password);

        for (QueryDocumentSnapshot document : documents) {
            if (document.getString("username").equals(username)) {
                return document.getString("password").equals(encrypt);
            } else {
                return false;
            }
        }

        return false;
    }
}
