package com.xingqi.code.commonlib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean isMobilePhone(String phoneNumber){

        Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean isMatch = matcher.matches();
        return isMatch;
    }
}
