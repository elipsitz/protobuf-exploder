package com.aegamesi.protobufexploder.protobuf;

public enum WireType {
	W_VARINT(0, "varint"),
	W_64BIT(1, "64-bit"),
	W_LENGTH_DELIMITED(2, "length-delimited"),
	W_GROUP_START(3, "group-start"),
	W_GROUP_END(4, "group-end"),
	W_32BIT(5, "32-bit");

	public final int value;
	public final String name;

	WireType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static WireType get(int value) {
		for (WireType entry : WireType.values())
			if (entry.value == value)
				return entry;
		return null;
	}


	public ProtobufType getDefaultType() {
		switch (this) {
			default:
			case W_VARINT:
				return ProtobufType.T_INT64;
			case W_64BIT:
				return ProtobufType.T_FIXED64;
			case W_LENGTH_DELIMITED:
				return ProtobufType.T_STRING;
			case W_GROUP_START:
				return ProtobufType.T_GROUP_START;
			case W_GROUP_END:
				return ProtobufType.T_GROUP_END;
			case W_32BIT:
				return ProtobufType.T_FIXED32;
		}
	}

	@Override
	public String toString() {
		return name + " (" + value + ")";
	}
}
