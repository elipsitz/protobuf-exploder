package com.aegamesi.protobufexploder.protobuf;

import java.util.HashMap;
import java.util.Map;

public class Schema {
	private Map<String, SchemaEntry> map;

	public Schema() {
		map = new HashMap<String, SchemaEntry>();
	}

	public SchemaEntry get(String key) {
		if (map.containsKey(key))
			return map.get(key);

		SchemaEntry entry = new SchemaEntry();
		map.put(key, entry);
		return entry;
	}
}
