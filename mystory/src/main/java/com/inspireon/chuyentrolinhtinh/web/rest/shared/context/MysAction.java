package com.inspireon.chuyentrolinhtinh.web.rest.shared.context;

public enum MysAction {
	CREATE("create"), READ("read"), UPDATE("update"), DELETE("delete");
	
	private String value;
	
	private MysAction(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static MysAction getByValue(String value) {
		for (MysAction action : values()) {
			if (action.value().equalsIgnoreCase(value))
				return action;
		}
		
		return null;
	}
}