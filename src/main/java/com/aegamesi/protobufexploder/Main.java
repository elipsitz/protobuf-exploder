package com.aegamesi.protobufexploder;

import com.aegamesi.protobufexploder.protobuf.ProtobufType;
import com.aegamesi.protobufexploder.protobuf.Schema;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import static com.aegamesi.protobufexploder.Util.println;

public class Main {
	final static String demoBase64 = "CAEQARgBIgJlbg==";

	public static void main(String[] args) {
		byte[] loadedProtobuf = null;
		Schema schema = new Schema();

		System.out.println("protobuf-exploder");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			if (!scanner.hasNextLine())
				break;
			String inputLine = scanner.nextLine().trim();
			String[] input = inputLine.split(" ");
			if (input.length == 0)
				continue;

			if (input[0].equalsIgnoreCase("exit") || input[0].equalsIgnoreCase("quit") || input[0].equalsIgnoreCase("done")) {
				println("Stopping...");
				System.exit(0);
			}
			if (input[0].equalsIgnoreCase("help") || input[0].equalsIgnoreCase("?")) {
				println("Unimplemented");
				continue;
			}
			if (input[0].equalsIgnoreCase("demo")) {
				loadedProtobuf = Base64.getDecoder().decode(demoBase64);
				println("Loaded demo protobuf.");
				continue;
			}
			if (input[0].equalsIgnoreCase("dump") || input[0].equalsIgnoreCase("explode") || input[0].equalsIgnoreCase("inspect")) {
				if (loadedProtobuf == null) {
					println("No protobuf loaded.");
				} else {
					println("Dumping protobuf...");
					ByteArrayInputStream inputStream = new ByteArrayInputStream(loadedProtobuf);
					try {
						ProtobufExploder.dumpProto("", schema, inputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				continue;
			}
			if (input[0].equalsIgnoreCase("types")) {
				for (ProtobufType type : ProtobufType.values()) {
					System.out.print(type.name + " ");
				}
				println("");
				continue;
			}

			//
			if (input[0].equalsIgnoreCase("name")) {
				if (input.length < 3) {
					println("name [field number] [name]");
				} else {
					String key = input[1];
					String name = input[2];
					schema.get(key).name = name;
					println("set name of '%s' to %s", key, name);
				}
				continue;
			}
			if (input[0].equalsIgnoreCase("type")) {
				if (input.length < 3) {
					println("type [field number] [type]");
				} else {
					String key = input[1];
					String type = input[2];
					schema.get(key).type = ProtobufType.fromName(type);
					println("set type of '%s' to %s", key, schema.get(key).type.name);
				}
				continue;
			}

			println("Unrecognized command, '%s'", input);
		}
	}
}
