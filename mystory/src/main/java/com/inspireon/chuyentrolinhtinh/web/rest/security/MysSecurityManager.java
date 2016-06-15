package com.inspireon.chuyentrolinhtinh.web.rest.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.inspireon.chuyentrolinhtinh.model.domain.user.Role;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MysAction;
import com.inspireon.chuyentrolinhtinh.web.rest.shared.context.MysObject;

public class MysSecurityManager {
	// Role : admin; Object: story; action: create|update|delete.
	private Map<Role, Map<MysObject, Set<MysAction>>> rolePermissons = new HashMap<Role, Map<MysObject, Set<MysAction>>>();;

	public MysSecurityManager() {		
	}
	
	public MysSecurityManager(String resource) {
		
		Properties properties = new Properties();
		InputStream in = MysSecurityManager.class.getClassLoader().getResourceAsStream(resource);
		try {
			properties.load(in);
			in.close();
		} catch (IOException e) {
			//logger.error("Can not load property file: " + FILE_CONFIG, e);
		}
				
		//admin.story=READ|UPDATE
		for(String key : properties.stringPropertyNames()) {
			String[] keyToks = key.split("\\.");
			String[] actToks = properties.getProperty(key).split("\\|");
			
			Role role = Role.getByKey(keyToks[0]);
			MysObject mysObj = MysObject.getByValue(keyToks[1]);
			Map<MysObject, Set<MysAction>> permissions = rolePermissons.get(role);
				
			
			if (permissions == null) {
				permissions = new HashMap<MysObject, Set<MysAction>>();
				rolePermissons.put(role, permissions);
			}
			
			Set<MysAction> actions = permissions.get(mysObj);
			
			if (actions == null) {
				actions = new HashSet<MysAction>();
				permissions.put(mysObj, actions);
			}			
				
			for (String action : actToks) {				 			
				MysAction enumAct = MysAction.getByValue(action);
				if (enumAct != null) {
					actions.add(enumAct);
				}
			}			
		}
				
	}
	
	public boolean hasPermis(Role mysRole, MysObject mysObj, MysAction mysAct) {
		
		if (!rolePermissons.containsKey(mysRole)) return false;
		if (!rolePermissons.get(mysRole).containsKey(mysObj)) return false;
		if (!rolePermissons.get(mysRole).get(mysObj).contains(mysAct)) return false;
		
		return true;		
	}
	
//	public static void main(String[] ass) {
//		MysSecurityManager mmm = new MysSecurityManager("role_permission.properties");
//		System.out.println(mmm.hasPermis(Role.GUEST, MysObject.STORY, MysAction.CREATE));
//		System.out.println(mmm.hasPermis(Role.GUEST, MysObject.STORY, MysAction.READ));
//	}
}
