package com.aegamesi.protobufexploder.protobuf;

public enum ProtobufType {
	T_INT32(WireType.W_VARINT, "int32"),
	T_INT64(WireType.W_VARINT, "int64"),
	T_UINT32(WireType.W_VARINT, "uint32"),
	T_UINT64(WireType.W_VARINT, "uint64"),
	T_SINT32(WireType.W_VARINT, "sint32"),
	T_SINT64(WireType.W_VARINT, "sint64"),
	T_BOOL(WireType.W_VARINT, "bool"),
	T_ENUM(WireType.W_VARINT, "enum"),

	T_FIXED64(WireType.W_64BIT, "fixed64"),
	T_SFIXED64(WireType.W_64BIT, "sfixed64"),
	T_DOUBLE(WireType.W_64BIT, "double"),

	T_STRING(WireType.W_LENGTH_DELIMITED, "string"),
	T_BYTES(WireType.W_LENGTH_DELIMITED, "bytes"),
	T_MESSAGE(WireType.W_LENGTH_DELIMITED, "message"),
	T_PACKED_REPEATED(WireType.W_LENGTH_DELIMITED, "packed"),

	T_GROUP_START(WireType.W_GROUP_START, "group-start"),

	T_GROUP_END(WireType.W_GROUP_END, "group-end"),

	T_FIXED32(WireType.W_32BIT, "fixed32"),
	T_SFIXED32(WireType.W_32BIT, "sfixed32"),
	T_FLOAT(WireType.W_32BIT, "float");

	public final WireType wire_type;
	public final String name;

	ProtobufType(WireType wire_type, String name) {
		this.wire_type = wire_type;
		this.name = name;
	}

	public static ProtobufType fromName(String name) {
		name = name.trim();
		for (ProtobufType type : ProtobufType.values())
			if (type.name.equalsIgnoreCase(name))
				return type;
		return null;
	}
}

/*
0	Varint	int32, int64, uint32, uint64, sint32, sint64, bool, enum
1	64-bit	fixed64, sfixed64, double
2	Length-delimited	string, bytes, embedded messages, packed repeated fields
3	Start group	groups (deprecated)
4	End group	groups (deprecated)
5	32-bit	fixed32, sfixed32, float
 */