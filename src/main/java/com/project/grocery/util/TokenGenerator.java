package com.project.grocery.util;

import java.security.SecureRandom;

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

}
