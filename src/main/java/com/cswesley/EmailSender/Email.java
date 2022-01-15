package com.cswesley.EmailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.PasswordAuthentication;
import java.util.Map;
import java.util.Properties;

public class Email {

    public void send(String toEmail, String subject, String htmlContent) {
        String username = "";
        String pass = "";

        Map<String, String> env = System.getenv();

        for (String envName : env.keySet()) {
            if (envName.equalsIgnoreCase("USER")) {
                username = env.get(envName);
            } else if (envName.equalsIgnoreCase("PASS")) {
                pass = env.get(envName);
            }
        }

        Properties var3 = new Properties();
        var3.setProperty("mail.smtp.host", "smtp.gmail.com");
        var3.setProperty("mail.smtp.port", "465");
        var3.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        var3.setProperty("mail.smtp.socketFactory.port", "465");
        var3.setProperty("mail.smtp.auth", "true");
        var3.setProperty("mail.smtp.starttls.enable", "true");

        String finalUsername = username;
        String finalPass = pass;
        Session session = Session.getDefaultInstance(var3, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(finalUsername, finalPass);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject(subject);
            message.setContent(htmlContent, "text/html");

            // send message
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
