package com.denis.phoneguard.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	/**
	 * 把输入流转化为字符串
	 * 
	 * @throws IOException
	 */
	public static String StreamToString(InputStream is) throws IOException {
		int len = 0;
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		is.close();
		out.close();
		return out.toString();
	}
}
