package com.xiaoss.starter.utils.lang;

import java.util.regex.Pattern;

public final class RegexUtils {

    private RegexUtils() {
    }

    public static boolean match(String regex, String content) {
        return Pattern.compile(regex).matcher(content).matches();
    }
}
