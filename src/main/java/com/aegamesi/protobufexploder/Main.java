package com.aegamesi.protobufexploder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.aegamesi.protobufexploder.Util.println;

public class Main {
	static String demoBase64 = "CAEQARgBIgJlbg==";

	public static void main(String[] args) {
		println("ProtobufExploder");

		List<String> arg_flags = new ArrayList<String>();
		String arg_filename = "";
		for (String arg : args) {
			if (arg.startsWith("-")) {
				arg_flags.add(arg.substring(1));
			} else {
				arg_filename = arg.trim();
				break;
			}
		}

		if (arg_filename.length() == 0) {
			System.out.println("Missing input file, demo mode enabled");
			arg_flags.add("demo");
		}

		if (arg_flags.contains("demo")) {
			println("DEMO MODE: ");
			try {
				ProtobufExploder.dumpProto(Base64.getDecoder().decode(demoBase64));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			println("\n\nExploding %s:", arg_filename);
			File file = new File(arg_filename);
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				ProtobufExploder.dumpProto("", null, fileInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
