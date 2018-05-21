package com.project.grocery.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 20, 2018
 * 
 */
public class RandomPassword {
	
	private static SecureRandom random = new SecureRandom();

	public static String newPassword() {
		return new BigInteger(50, random).toString(32);
	}

}
