package com.aegamesi.protobufexploder.protobuf;

public enum ProtobufType {
	T_INT32(WireType.W_VARINT),
	T_INT64(WireType.W_VARINT),
	T_UINT32(WireType.W_VARINT),
	T_UINT64(WireType.W_VARINT),
	T_SINT32(WireType.W_VARINT),
	T_SINT64(WireType.W_VARINT),
	T_BOOL(WireType.W_VARINT),
	T_ENUM(WireType.W_VARINT),

	T_FIXED64(WireType.W_64BIT),
	T_SFIXED64(WireType.W_64BIT),
	T_DOUBLE(WireType.W_64BIT),

	T_STRING(WireType.W_LENGTH_DELIMITED),
	T_BYTES(WireType.W_LENGTH_DELIMITED),
	T_MESSAGE(WireType.W_LENGTH_DELIMITED),
	T_PACKED_REPEATED(WireType.W_LENGTH_DELIMITED),

	T_GROUP_START(WireType.W_GROUP_START),

	T_GROUP_END(WireType.W_GROUP_END),

	T_FIXED32(WireType.W_32BIT),
	T_SFIXED32(WireType.W_32BIT),
	T_FLOAT(WireType.W_32BIT);

	public final WireType wire_type;

	ProtobufType(WireType wire_type) {
		this.wire_type = wire_type;
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