package com.cswesley.Main;

import com.cswesley.Utils.EncryptDecrypt;
import com.cswesley.Validation.LoginAndSignup;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HomeworkPlanner {

    private final Font mainTitleFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    private final Font subTitleFont = new Font("Arial", Font.PLAIN, 20);
    private final LoginAndSignup loginAndSignup = new LoginAndSignup();

    public static void main(String[] args) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        HomeworkPlanner hwp = new HomeworkPlanner();
        hwp.openLoginPage();
    }

    private void openLoginPage() {
        JFrame loginAndSignupFrame = new JFrame("Login and Signup");
        loginAndSignupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginAndSignupFrame.setResizable(false);
        Container c = loginAndSignupFrame.getContentPane();
        c.setLayout(null);

        // Main Title
        JLabel mainTitle = new JLabel("Homework Planner");
        mainTitle.setFont(this.mainTitleFont);
        mainTitle.setSize(300, 30);
        mainTitle.setLocation(95, 10);
        c.add(mainTitle);

        // Sub-Title
        JLabel subTitle = new JLabel("Login or Signup");
        subTitle.setFont(this.subTitleFont);
        subTitle.setSize(200, 25);
        subTitle.setLocation(170, 60);
        c.add(subTitle);

        // Handles the login and signup protocols and adds components to frame.
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        loginAndSignup.loginAndSignup(tabbedPane, loginAndSignupFrame);

        loginAndSignupFrame.setSize(500, 500);
        loginAndSignupFrame.setLocationRelativeTo(null);
        loginAndSignupFrame.setVisible(true);
    }


}
