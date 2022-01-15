package com.cswesley.MainApp.Create.Assignment;

import com.cswesley.MainApp.Create.Subject.SubjectDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;

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
                // Create assignment

                // This creates a connection to SQL in which user must specify several things to signify an assignment
                // like assignment title, teacher, due date, etc.

                // Step 1: Display GUI to specify options like the above (title, teacher, etc.).
                try {
                    assignmentGUI();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        };
    }

    private void assignmentGUI() throws SQLException {
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

    private void addSubjects(JComboBox<Object> chooseSubject) throws SQLException {
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

            }
        };
    }

    private void addAssignment(String username, String classS, String assignmentTitle) throws SQLException {

    }
}
