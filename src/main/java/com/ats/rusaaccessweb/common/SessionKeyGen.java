package com.ats.rusaaccessweb.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionKeyGen {

	
	public static void changeSessionKey(HttpServletRequest request) {
		
		try {
		UUID uuid = UUID.randomUUID();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] messageDigest = md.digest(String.valueOf(uuid).getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		 
		HttpSession session = request.getSession();
		session.setAttribute("generatedKey", hashtext);
	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
