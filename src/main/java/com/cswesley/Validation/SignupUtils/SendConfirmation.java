package com.cswesley.Validation.SignupUtils;

import com.cswesley.EmailSender.Email;

public class SendConfirmation {
    String code;

    public void send(String email) {
        Email emailer = new Email();
        RandomCode randomCode = new RandomCode();
        code = randomCode.createCode();

        emailer.send(email, "Account Activation Code", "" +
                "<html><center><font size=+4>Hello! Here is your code and please input it into the box that popped up! If you are not sure what this is, please ignore it. Thank you!" +
                "<br/><br/>Code: <b>" + code + "</b></center></html>");
    }

    public String getCode() {
        return code;
    }
}
