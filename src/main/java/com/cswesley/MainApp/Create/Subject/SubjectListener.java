package com.cswesley.MainApp.Create.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;

public class SubjectListener {

    public ActionListener createSubjectListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {

            }
        };
    }

    private void insertClass() throws SQLException {

    }

    private boolean doesExist() throws SQLException {
        return false;
    }

    private void createSubjectFrame(ActionListener actionListener) {
        JFrame newSubjectFrame = new JFrame("Create New Subject");
        newSubjectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = newSubjectFrame.getContentPane();
        c.setLayout(null);
        newSubjectFrame.setResizable(false);

        JLabel nameOfSubject = new JLabel("Name of Subject");
        nameOfSubject.setSize(300, 100);
        nameOfSubject.setLocation(50, -25);
        nameOfSubject.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        c.add(nameOfSubject);

        JTextField enterSubject = new JTextField();
        enterSubject.setSize(140, 35);
        enterSubject.setLocation(55, 100);
        c.add(enterSubject);

        JButton saveButton = new JButton("Save");
        saveButton.setSize(100, 40);
        saveButton.setLocation(75, 155);
        saveButton.addActionListener(actionListener);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.add(saveButton);

        newSubjectFrame.setSize(265, 250);
        newSubjectFrame.setLocationRelativeTo(null);
        newSubjectFrame.setVisible(true);
    }
}
