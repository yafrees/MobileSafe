package com.yafrees.mobilesafe.utils;

import java.security.MessageDigest;

public class MD5Utils {
	/**
	 * MD5加密方法
	 * */
	public static String ecoder(String password){
		//MD5加密
		StringBuffer buffer;
		try {
			//1.信息摘要
			MessageDigest digest = MessageDigest.getInstance("md5");
			//2.转化成byte数组
			byte[] bytes = digest.digest(password.getBytes());
			buffer = new StringBuffer();
			//3每一个byte转化成8个二进制位与运算
			for (byte b : bytes) {
				int number = b & 0xff;
				//把int转化成16进制
				String numStr = Integer.toHexString(number);
				System.out.println(numStr);

				//5.不足位的不全
				if (numStr.length() == 1) {
					buffer.append("0");
				}
				buffer.append(numStr);
			}
			//标准的MD5加密后的结果
			//		System.out.println(buffer.toString());
			
			return buffer.toString();
		} 
		catch (Exception e) {
			// TODO: handle exception
			return "";
			
		}

	}


}
