package com.wuzx.weixin.qyweixin.sdk.kit;

import java.util.HashMap;
import java.util.Map;

public class ParaMap {
	
	private Map<String, Object> data = new HashMap<String, Object>();
	private ParaMap() {}
	
	public static ParaMap create() {
		return new ParaMap();
	}
	
	public static ParaMap create(String key, String value) {
		return create().put(key, value);
	}
	
	public ParaMap put(String key, String value) {
		data.put(key, value);
		return this;
	}
	
	public Map<String, Object> getData() {
		return data;
	}
}
