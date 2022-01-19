package com.cswesley.MainApp.Create.Subject;

import com.cswesley.Utils.Utilities;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SubjectListener {

    JButton saveButton;
    JTextField enterSubject;
    private JFrame newSubjectFrame;

    private void insertSubjectToDatabase() {
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(Utilities.username).collection("subjects").document(enterSubject.getText());
        Map<String, Object> subject = new HashMap<>();
        subject.put("name", enterSubject.getText());
        subject.put("# of assignments", 0);
        subject.put("# of assignments completed", 0);

        docRef.set(subject);
    }

    private boolean doesExist() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query = db.collection("users").document(Utilities.username).collection("subjects").get();
        QuerySnapshot querySnapshot = query.get();

        for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
            return document.getString("name").equals(enterSubject.getText());
        }

        return false;
    }

    private void createSubjectFrame(ActionListener actionListener) {
        newSubjectFrame = new JFrame("Create New Subject");
        newSubjectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newSubjectFrame.getContentPane();
        c.setLayout(null);
        newSubjectFrame.setResizable(false);

        JLabel nameOfSubject = new JLabel("Name of Subject");
        nameOfSubject.setSize(300, 100);
        nameOfSubject.setLocation(50, -25);
        nameOfSubject.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfSubject);

        enterSubject = new JTextField();
        enterSubject.setSize(140, 35);
        enterSubject.setLocation(55, 100);
        c.add(enterSubject);

        saveButton = new JButton("Save");
        saveButton.setSize(100, 40);
        saveButton.setLocation(75, 155);
        saveButton.addActionListener(actionListener);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.add(saveButton);

        newSubjectFrame.setSize(265, 250);
        newSubjectFrame.setLocationRelativeTo(null);
        newSubjectFrame.setVisible(true);
    }

    public ActionListener createSubjectListener(JButton createSubject) {

        return e -> {
            if (e.getSource() == createSubject) {
                // here is where the subject is created.
                createSubjectFrame(al -> {
                    if (al.getSource() == saveButton) {
                        String subjectName = enterSubject.getText();
                        if (subjectName.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please enter a subject name");
                        } else {
                            try {
                                if (doesExist()) {
                                    JOptionPane.showMessageDialog(null, "Subject already exists");
                                } else {
                                    insertSubjectToDatabase();
                                    JOptionPane.showMessageDialog(null, "Subject created");
                                    newSubjectFrame.dispose();
                                }
                            } catch (ExecutionException | InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
    }
}
