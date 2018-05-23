package com.project.grocery.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 11, 2018
 * 
 */
public class RandomStrings {
	
	private static SecureRandom random = new SecureRandom();

	public static String getToken() {
		return new BigInteger(50, random).toString(64);
}

}
