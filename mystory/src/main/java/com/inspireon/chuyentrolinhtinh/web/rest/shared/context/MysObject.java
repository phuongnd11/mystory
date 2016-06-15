package com.inspireon.chuyentrolinhtinh.web.rest.shared.context;

public enum MysObject {
	STORY("story"), COMMENT("comment");
	
	private String value;
	
	private MysObject(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
	public static MysObject getByValue(String value) {
		for (MysObject action : values()) {
			if (action.value().equalsIgnoreCase(value))
				return action;
		}
		
		return null;
	}	
//	//Four actions		
//	MysAction CREATE = MysAction.CREATE;
//	MysAction READ = MysAction.READ;
//	MysAction UPDATE = MysAction.UPDATE;
//	MysAction DELETE = MysAction.DELETE;
}