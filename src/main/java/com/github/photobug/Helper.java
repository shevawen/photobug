package com.github.photobug;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.nutz.mvc.Mvcs;

public class Helper {
	public static String getPath(String path) {
//		for(String part : parts){
//			part.replace("${app.root}", Mvcs.getServletContext().getRealPath("/"));
//		}
//		return Strings.join("", parts);
		return path.replace("${app.root}", Mvcs.getServletContext().getRealPath("/"));
	}

	public static String MD5(String plainText) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// result = buf.toString(); //md5 32bit
			// result = buf.toString().substring(8, 24))); //md5 16bit
			result = buf.toString().substring(8, 24);
			System.out.println("mdt 16bit: " + buf.toString().substring(8, 24));
			System.out.println("md5 32bit: " + buf.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @see http://stackoverflow.com/questions/18134555/how-many-unique-jsbin-url-shortcode-combinations-can-exist
	 * @return
	 */
	public static String generateUniqueKey() {
		String vowels = "aeiou";
		String consonants = "bcdfghjklmnpqrstvwxyz";
		String word = "";
		int length = 6;
		int index = 0;
		String set;

		for (; index < length; index += 1) {
			set = (index % 2 == 0) ? consonants : vowels;
			word += set.charAt((int) Math.floor(Math.random() * set.length()));
		}

		return word;
	}
}
