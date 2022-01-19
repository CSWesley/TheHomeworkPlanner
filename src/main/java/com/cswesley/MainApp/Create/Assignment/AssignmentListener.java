package com.cswesley.MainApp.Create.Assignment;

import com.cswesley.MainApp.Create.Subject.SubjectDetails;
import com.cswesley.Utils.Utilities;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AssignmentListener {

    private JTextField teacher;
    private JTextField link;
    private JTextField enterAssignment;
    private JTextField dueDate;
    private JTextField estimateAmountTime;
    private JComboBox<Object> chooseSubject;
    private JFrame newAssignmentFrame;

    public ActionListener createAssignmentListener(JButton button) {
        return e -> {
            if (e.getSource() == button) {
                try {
                    assignmentGUI();
                } catch (ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    private void assignmentGUI() throws ExecutionException, InterruptedException {
        newAssignmentFrame = new JFrame("Create New Assignment");
        newAssignmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newAssignmentFrame.getContentPane();
        c.setLayout(null);
        newAssignmentFrame.setResizable(false);

        JLabel nameOfAssignment = new JLabel("Create a new assignment");
        nameOfAssignment.setSize(300, 100);
        nameOfAssignment.setLocation(125, -25);
        nameOfAssignment.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfAssignment);

        chooseSubject = new JComboBox<>();
        chooseSubject.setSize(140, 35);
        chooseSubject.setLocation(255, 200);
        addSubjects(chooseSubject);
        c.add(chooseSubject);

        teacher = new JTextField();
        teacher.setSize(140, 35);
        teacher.setLocation(100, 150);
        teacher.setText("Teacher");
        c.add(teacher);

        link = new JTextField();
        link.setSize(140, 35);
        link.setLocation(100, 200);
        link.setText("Link");
        c.add(link);

        enterAssignment = new JTextField();
        enterAssignment.setSize(140, 35);
        enterAssignment.setLocation(100, 100);
        enterAssignment.setText("Name");
        c.add(enterAssignment);

        dueDate = new JTextField();
        dueDate.setSize(140, 35);
        dueDate.setLocation(255, 100);
        dueDate.setText("Due Date");
        c.add(dueDate);

        estimateAmountTime = new JTextField();
        estimateAmountTime.setSize(140, 35);
        estimateAmountTime.setLocation(255, 150);
        estimateAmountTime.setText("Estimated Time");
        c.add(estimateAmountTime);

        JButton saveNewAssignment = new JButton("Save");
        saveNewAssignment.setSize(110, 40);
        saveNewAssignment.setLocation(195, 275);
        saveNewAssignment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveNewAssignment.addActionListener(saveListener(saveNewAssignment));
        c.add(saveNewAssignment);

        newAssignmentFrame.setSize(500, 369);
        newAssignmentFrame.setLocationRelativeTo(null);
        newAssignmentFrame.setVisible(true);
    }

    private void addSubjects(JComboBox<Object> chooseSubject) throws ExecutionException, InterruptedException {
        // Set contains unique elements only.
        // Add items to this combo box using a HashSet

        SubjectDetails sd = new SubjectDetails();
        HashSet<String> subjects = sd.getSubjects();

        for (String e : subjects) {
            chooseSubject.addItem(e);
        }
    }

    private ActionListener saveListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                String teacherText = teacher.getText();
                String linkText = link.getText();
                String assignmentText = enterAssignment.getText();
                String dueDateText = dueDate.getText();
                String estimateAmountTimeText = estimateAmountTime.getText();
                String subjectText = chooseSubject.getSelectedItem().toString();

                if (teacherText.equals("") || linkText.equals("") || assignmentText.equals("") || dueDateText.equals("") || estimateAmountTimeText.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields. If you don't know one of the options, please enter 'none'.");
                } else {
                    // this inserts the new assignment into the database
                    try {
                        if (doesExist(subjectText, assignmentText)) {
                            JOptionPane.showMessageDialog(null, "Assignment already exists in this subject.");
                        } else {
                            addAssignment(Utilities.username, subjectText, assignmentText, dueDateText, estimateAmountTimeText, teacherText, linkText);
                            JOptionPane.showMessageDialog(null, "Assignment added successfully.");
                            newAssignmentFrame.dispose();
                        }
                    } catch (ExecutionException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }

    private boolean doesExist(String subject, String assignment) throws ExecutionException, InterruptedException {
        // checks if the assignment already exists in the database
        // returns true if it does, false if it doesn't
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<DocumentSnapshot> query = db.collection("users").document(Utilities.username).collection("subjects").document(subject).collection("assignments").document(assignment).get();
        DocumentSnapshot document = query.get();

        return document.exists();
    }

    private void addAssignment(String username, String subject, String assignment, String dueDate, String estimateAmountTime, String teacher, String link) throws ExecutionException, InterruptedException {
        // adds the assignment to the database
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document(username).collection("subjects").document(subject).collection("assignments").document(assignment);
        Map<String, Object> assignmentMap = new HashMap<>();
        assignmentMap.put("assignment name", assignment);
        assignmentMap.put("due date", dueDate);
        assignmentMap.put("estimated amount time", estimateAmountTime);
        assignmentMap.put("teacher", teacher);
        assignmentMap.put("link", link);
        assignmentMap.put("completed", false);
        assignmentMap.put("completed date", "none");
        assignmentMap.put("subject", subject);

        docRef.set(assignmentMap);
    }
}
