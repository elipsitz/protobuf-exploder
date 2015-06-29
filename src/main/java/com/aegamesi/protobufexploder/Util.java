package com.aegamesi.protobufexploder;

import com.google.common.base.Joiner;

import java.util.Arrays;

public class Util {
	public static void println(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	public static String representBytesAsString(byte[] bytes) {
		return Joiner.on(", ").join(Arrays.asList(bytes));
	}
}
