package com.cswesley.Validation;

import com.cswesley.MainApp.Application;
import com.cswesley.Utils.EncryptDecrypt;
import com.cswesley.Utils.GhostText;
import com.cswesley.Validation.LoginUtils.AccountLogin;
import com.cswesley.Validation.SignupUtils.CreateAccount;
import com.cswesley.Validation.SignupUtils.SendConfirmation;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This class handles the logging in and signing up of the user. It also adds the components to the frame.
 */

public class LoginAndSignup {

    public static JTextField enterUsername;

    public void loginAndSignup(JTabbedPane tabbedPane, JFrame loginAndSignupFrame) {
        // Tabbed pane for login and signup

        /** Start handling signup */

        JPanel signup = new JPanel();

        JTextField createUsername = new JTextField("", 13);
        GhostText ghostText = new GhostText(createUsername, "Username...");
        signup.add(createUsername);

        JPasswordField createPassword = new JPasswordField("", 13);
        GhostText ghostText2 = new GhostText(createPassword, "Password");
        signup.add(createPassword);

        JPasswordField confirmPassword = new JPasswordField("", 13);
        GhostText ghostText3 = new GhostText(confirmPassword, "Password");
        signup.add(confirmPassword);

        JTextField setEmail = new JTextField("", 15);
        GhostText ghostText4 = new GhostText(setEmail, "Email...");
        signup.add(setEmail);

        JButton confirmSignup = new JButton("Confirm Signup");
        confirmSignup.addActionListener(e -> {
            if (e.getSource() == confirmSignup) {
                // Send email to confirm.
                CreateAccount ca = new CreateAccount();
                boolean exists = false;

                try {
                    exists = ca.checkIfExists(createUsername.getText(), setEmail.getText());
                } catch (IOException | ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                }

                if (exists) {
                    JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "An account already exists with this username or email!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Email sending
                    if (!setEmail.getText().contains("@") || createUsername.getText().isEmpty() || createPassword.getText().isEmpty() || confirmPassword.getText().isEmpty() || !createPassword.getText().equals(confirmPassword.getText())) {
                        JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "One or more fields are filled out incorrectly!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        SendConfirmation sendConfirmation = new SendConfirmation();
                        sendConfirmation.send(setEmail.getText());
                        String code = sendConfirmation.getCode();

                        try {
                            // only create account if code is correct
                            String input = JOptionPane.showInputDialog(loginAndSignupFrame.getContentPane(), "Enter the code sent to your email: ", "Confirmation Code", JOptionPane.PLAIN_MESSAGE);
                            if (input.equals(code)) {
                                ca.createAccount(createUsername.getText(), createPassword.getText(), setEmail.getText());
                                JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                String input2 = JOptionPane.showInputDialog(loginAndSignupFrame.getContentPane(), "The code you entered is incorrect! Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                                if (input2.equals(code)) {
                                    ca.createAccount(createUsername.getText(), createPassword.getText(), setEmail.getText());
                                    JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    String input3 = JOptionPane.showInputDialog(loginAndSignupFrame.getContentPane(), "The code you entered is incorrect! This is your last attempt.", "Error", JOptionPane.ERROR_MESSAGE);
                                    if (input3.equals(code)) {
                                        ca.createAccount(createUsername.getText(), createPassword.getText(), setEmail.getText());
                                        JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(loginAndSignupFrame.getContentPane(), "The code you entered is incorrect. Try again soon.", "Error", JOptionPane.ERROR_MESSAGE);
                                        System.exit(0);
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        signup.add(confirmSignup);

        /** End handling signup */

        // ******** //

        /** Start handling login */

        JPanel login = new JPanel();

        enterUsername = new JTextField("", 13);
        GhostText ghostText5 = new GhostText(enterUsername, "Username...");
        login.add(enterUsername);

        JPasswordField enterPass = new JPasswordField("", 13);
        GhostText ghostText6 = new GhostText(enterPass, "Password");
        login.add(enterPass);

        JButton confirmLogin = new JButton("Confirm Login");
        confirmLogin.addActionListener(e -> {
            if (e.getSource() == confirmLogin) {
                // Confirm login and show home page.
                // Check if password matches username. If yes, continue to app. If not, say "password does not match" or something.

                AccountLogin accountLogin = new AccountLogin();
                boolean matches = false;
                try {
                    matches = accountLogin.matches(enterUsername.getText(), enterPass.getText());
                } catch (ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                }

                if (matches) {
                    // Continue to main application.
                    try {
                        loginAndSignupFrame.dispose();

                        Application application = new Application();
                        application.openMainApplication();

                    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The username and password do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        login.add(confirmLogin);

        /** End handling login */

        // Add components to MainFrame
        tabbedPane.setSize(300, 300);
        tabbedPane.setLocation(90, 100);
        tabbedPane.addTab("Login", login);
        tabbedPane.addTab("Signup", signup);

        // Add the signup and login tabbed pane to the frame.
        loginAndSignupFrame.getContentPane().add(tabbedPane);
    }
}
