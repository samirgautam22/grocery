package com.project.grocery.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 7, 2018
 * 
 */
public class Md5Hashing {
	
	
	public static String  getPw(String password) throws NoSuchAlgorithmException {
		String pw = null;  
		MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	     
//	        System.out.println("Digest(in hex format):: " + sb.toString());
	        
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	   	     	 pw=hexString.toString();
	    	}
//	    	System.out.println(password);
			
			return pw;
	}

}
