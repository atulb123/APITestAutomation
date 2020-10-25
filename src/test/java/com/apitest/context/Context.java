package com.apitest.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Context {
	public static Map<String, String> tempValues = new LinkedHashMap<String, String>();

	public String getContextParam(String key) {
		return tempValues.get(key);
	}

	public void setContextParam(String key, String value) {
		tempValues.put(key, value);
	}
}
