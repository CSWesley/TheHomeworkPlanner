package com.cswesley.MainApp.Create.Subject;

import com.cswesley.Utils.Utilities;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;

public class SubjectDetails {

    public HashSet<String> getSubjects() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query = db.collection("users").document(Utilities.username).collection("subjects").get();
        QuerySnapshot querySnapshot = query.get();

        HashSet<String> subjects = new HashSet<>();
        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            subjects.add(document.getString("name"));
        }

        return subjects;
    }

    public int getNumberOfSubjects() throws ExecutionException, InterruptedException {
        return getSubjects().size();
    }
}
