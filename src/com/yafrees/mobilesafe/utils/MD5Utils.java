package com.yafrees.mobilesafe.utils;

import java.security.MessageDigest;

public class MD5Utils {
	/**
	 * MD5���ܷ���
	 * */
	public static String ecoder(String password){
		//MD5����
		StringBuffer buffer;
		try {
			//1.��ϢժҪ
			MessageDigest digest = MessageDigest.getInstance("md5");
			//2.ת����byte����
			byte[] bytes = digest.digest(password.getBytes());
			buffer = new StringBuffer();
			//3ÿһ��byteת����8��������λ������
			for (byte b : bytes) {
				int number = b & 0xff;
				//��intת����16����
				String numStr = Integer.toHexString(number);
				System.out.println(numStr);

				//5.����λ�Ĳ�ȫ
				if (numStr.length() == 1) {
					buffer.append("0");
				}
				buffer.append(numStr);
			}
			//��׼��MD5���ܺ�Ľ��
			//		System.out.println(buffer.toString());
			
			return buffer.toString();
		} 
		catch (Exception e) {
			// TODO: handle exception
			return "";
			
		}

	}


}
