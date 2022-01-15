package com.cswesley.MainApp.View.Subject;

import com.cswesley.MainApp.Create.Subject.SubjectDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public class ViewSubjectListener {
    private final Font titleFont = new Font("Times New Roman", Font.PLAIN, 40);

    public ActionListener createSubjectListener(JButton button) {

        return e -> {
            if (e.getSource() == button) {
                try {
                    createViewFrame();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public void createViewFrame() throws SQLException {
        JFrame viewFrame = new JFrame("View Subjects");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = viewFrame.getContentPane();
        c.setLayout(new FlowLayout());
        viewFrame.setResizable(false);

        SubjectDetails sd = new SubjectDetails();
        Set<String> classes = sd.getSubjects();

        int classesNum = classes.size();
        ArrayList<String> ar = new ArrayList<>(classesNum);
        ar.addAll(classes);

        for (int i = 0; i < classes.size(); i++) {
            JButton subjectName = new JButton(ar.get(i));
            subjectName.addActionListener(classActionListener(subjectName, viewFrame));
            c.add(subjectName);
        }

        viewFrame.setSize(450, 450);
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);
    }

    private ActionListener classActionListener(JButton button, JFrame subjectsFrame) {
        return e -> {
            if (e.getSource() == button) {
            }
        };
    }
}
