package com.project.grocery.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 15, 2018
 * 
 */
public class TokenGenerator {
	
	 protected static SecureRandom random = new SecureRandom();
     
     public synchronized String generateToken( String username ) {
             long longToken = Math.abs( random.nextLong() );
             String random = Long.toString( longToken, 16 );
             return (random);
     }
     
     
     public static String getToken() {
         String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
         StringBuilder salt = new StringBuilder();
         Random rnd = new Random();
         while (salt.length() < 18) { // length of the random string.
             int index = (int) (rnd.nextFloat() * SALTCHARS.length());
             salt.append(SALTCHARS.charAt(index));
         }
         String saltStr = salt.toString();
         return saltStr;

     }

}
