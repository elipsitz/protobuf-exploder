package com.aegamesi.protobufexploder;

public class Util {
	public static void println(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	public static String representBytesAsString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(b & 0xFF0);
			builder.append(" ");
		}
		return builder.toString();
	}
}
