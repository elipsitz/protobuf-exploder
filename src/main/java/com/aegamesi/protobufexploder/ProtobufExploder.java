package com.aegamesi.protobufexploder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;
import hudson.plugins.timestamper.io.Varint;
import static com.aegamesi.protobufexploder.Util.println;

// https://developers.google.com/protocol-buffers/docs/encoding#simple
public class ProtobufExploder {
	static String demoBase64 = "CAEQARgBIgJlbg==";


	public static void dumpProto(byte[] bytes) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		dumpProto(inputStream);
	}

	public static void dumpProto(InputStream inputStream) throws IOException {
		LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(inputStream);
		while (dataStream.available() > 0) {
			long key = Varint.read(dataStream);
			int wire_type = (int) (key & 0x7);
			long field_number = key >> 3;

			println("Field #%d, Type: %s (%d)", field_number, getWireTypeName(wire_type), wire_type);

			switch (wire_type) {
				case 0:
					// varint -- int32, int64, uint32, uint64, sint32, sint64, bool, enum
					println("\tValue: %d", Varint.read(dataStream));
					break;
				case 1:
					// 64-bit -- fixed64, sfixed64, double
					println("\tValue: %d", dataStream.readLong());
					break;
				case 2:
					// Length delimited -- string, bytes, embedded messages, packed repeated fields
					int length = (int) Varint.read(dataStream);
					byte[] byteArray = new byte[length];
					dataStream.read(byteArray);
					String str = new String(byteArray);
					println("\tValue (%d bytes): \"%s\"", length, str);
					break;
				case 3:
					// start group
					break;
				case 4:
					// end group
					break;
				case 5:
					// 32 bit -- fixed32, sfixed32, float
					println("\tValue: %d", dataStream.readInt());
					break;
			}
		}
	}


	final static String[] wireTypeNames = new String[6];

	static {
		wireTypeNames[0] = "varint";
		wireTypeNames[1] = "64-bit";
		wireTypeNames[2] = "length-delimited";
		wireTypeNames[3] = "start-group";
		wireTypeNames[4] = "end-group";
		wireTypeNames[5] = "32-bit";
	}

	public static String getWireTypeName(int type) {
		if (type < 0 || type >= wireTypeNames.length)
			return "unknown";
		return wireTypeNames[type];
	}
}