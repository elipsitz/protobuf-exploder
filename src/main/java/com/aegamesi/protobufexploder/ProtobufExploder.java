package com.aegamesi.protobufexploder;

import com.aegamesi.protobufexploder.protobuf.ProtobufType;
import com.aegamesi.protobufexploder.protobuf.SchemaEntry;
import com.aegamesi.protobufexploder.protobuf.WireType;
import com.google.common.io.LittleEndianDataInputStream;
import hudson.plugins.timestamper.io.Varint;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.aegamesi.protobufexploder.Util.println;

// https://developers.google.com/protocol-buffers/docs/encoding#simple
public class ProtobufExploder {
	public static void dumpProto(String namespace, Map<String, SchemaEntry> schema, InputStream inputStream) throws IOException {
		LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(inputStream);
		while (dataStream.available() > 0) {
			long key = Varint.read(dataStream);
			WireType wire_type = WireType.get((int) (key & 0x7));
			long field_number = key >> 3;

			SchemaEntry schemaEntry = schema != null ? schema.get(namespace + "." + field_number) : null;
			ProtobufType protobufType = (schemaEntry != null && schemaEntry.type != null) ? schemaEntry.type : wire_type.getDefaultType();

			println("Field #%d, Type: %s", field_number, wire_type);

			switch (wire_type) {
				case W_VARINT: {
					// varint -- int32, int64, uint32, uint64, sint32, sint64, bool, enum
					long value = Varint.read(dataStream);
					switch (protobufType) {
						case T_BOOL:
							println("\tValue: %b", value != 0);
							break;
						default:
							println("\tValue: %d", value);
							break;
					}
					break;
				}
				case W_64BIT: {
					// 64-bit -- fixed64, sfixed64, double
					switch (protobufType) {
						case T_DOUBLE:
							double double_value = dataStream.readDouble();
							println("\tValue: %f", double_value);
							break;
						default:
							long long_value = dataStream.readLong();
							println("\tValue: %d", long_value);
							break;
					}
					break;
				}
				case W_LENGTH_DELIMITED:
					// Length delimited -- string, bytes, embedded messages, packed repeated fields
					int length = (int) Varint.read(dataStream);
					byte[] bytes = new byte[length];
					length = dataStream.read(bytes);
					switch (protobufType) {
						case T_STRING:
							println("\tValue (%d bytes): \"%s\"", length, new String(bytes));
							break;
						case T_MESSAGE:
							ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
							println("\tValue: ====== (%d bytes)", length);
							dumpProto(namespace + "." + field_number, schema, stream);
							println("\t=============");
							break;
						default:
						case T_BYTES:
							println("\tValue (%d bytes): \"%s\"", length, Util.representBytesAsString(bytes));
							break;
					}
					break;
				case W_GROUP_START:
					// start group
					break;
				case W_GROUP_END:
					// end group
					break;
				case W_32BIT:
					// 32 bit -- fixed32, sfixed32, float
					switch (protobufType) {
						case T_FLOAT:
							float float_value = dataStream.readFloat();
							println("\tValue: %f", float_value);
							break;
						default:
							int int_value = dataStream.readInt();
							println("\tValue: %d", int_value);
							break;
					}
					break;
			}
		}
	}
}