package com.cswesley.MainApp.View.Subject;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class VSListener {

    public ActionListener listen(JButton button, JButton subjButton) {
        return e -> {
            if (e.getSource() == button) {

            }
        };
    }

    private void deleteClass(String subjectName) throws SQLException {

    }
}
