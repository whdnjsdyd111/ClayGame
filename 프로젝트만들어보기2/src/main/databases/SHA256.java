package main.databases;

import java.security.MessageDigest;

public class SHA256 {
	private static SHA256 instance = new SHA256();

	public static SHA256 getInstance() {
		return instance;
	}

	public String getSha256(String str) {
		StringBuffer hexString = new StringBuffer();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(str.getBytes());

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xFF & hash[i]);

				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}
}