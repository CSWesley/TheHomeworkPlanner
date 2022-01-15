package com.cswesley.Validation.SignupUtils;

import java.util.Random;

public class RandomCode {

    public String createCode() {
        Random random = new Random();
        int randomInt = random.nextInt(9);
        char randomChar = (char) (random.nextInt(26) + 'a');

        int randomInt2 = random.nextInt(9);
        char randomChar2 = (char) (random.nextInt(26) + 'a');

        int randomInt3 = random.nextInt(9);
        char randomChar3 = (char) (random.nextInt(26) + 'a');

        return Integer.toString(randomInt) + randomChar + randomInt2 + randomChar2 + randomInt3 + randomChar3;
    }
}
