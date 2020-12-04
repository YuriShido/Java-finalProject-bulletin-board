package ca.java.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
	public static boolean checkEmail(String email) {
		String emailRegex = "^[\\w!#$%&Åe*+/=?`{|}~^-]+(?:\\.[\\w!#$%&Åe*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
