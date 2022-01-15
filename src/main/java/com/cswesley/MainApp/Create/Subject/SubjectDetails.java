package com.cswesley.MainApp.Create.Subject;

import java.sql.*;
import java.util.HashSet;

public class SubjectDetails {

    public HashSet<String> getSubjects() throws SQLException {
        return null;
    }

    public int getNumberOfSubjects() throws SQLException {
        return getSubjects().size();
    }
}
