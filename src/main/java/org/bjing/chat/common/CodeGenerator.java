package org.bjing.chat.common;

import java.util.Random;

public class CodeGenerator {

    public static String generateOtpCode() {
        Random random = new Random();
        int[] values = random.ints(2, 100, 900).toArray();

        return values[0] + "-" + values[1];
    }
}
