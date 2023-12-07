package com.bci.utils;

import java.util.Properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PasswordValidator {
    
	private final static Properties props = ApplicationProperties.loadProperties();
	private final static String regex = props.getProperty("passRegex");

	public static boolean isValid(String password) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
