package com.project.grocery.util;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 26, 2018
 * 
 */
public class DynamicAddress {
	
	
//	private static String getClientIp(HttpServletRequest request) {
//
//        String remoteAddr = "";
//
//        if (request != null) {
//            remoteAddr = request.getHeader("X-FORWARDED-FOR");
//            if (remoteAddr == null || "".equals(remoteAddr)) {
//                remoteAddr = request.getRemoteAddr();
//            }
//        }
//
//        return remoteAddr;
//    }
	
	public static String getClientIpAddress(HttpServletRequest request) {
	    String xForwardedForHeader = request.getHeader("X-Forwarded-For");
	    if (xForwardedForHeader == null) {
	        return request.getRemoteAddr();
	    } else {
	        return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
	    }
	}

}
