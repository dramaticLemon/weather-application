package com.dch.compilers.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

	public static String hashPassword(String passwod) {
		return BCrypt.hashpw(passwod, BCrypt.gensalt());
	}

	public static boolean checkPassword(String password, String hashed) {
		return BCrypt.checkpw(password, hashed);
	}
}