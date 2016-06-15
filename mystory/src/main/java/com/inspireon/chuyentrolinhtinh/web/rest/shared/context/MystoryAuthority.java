package com.inspireon.chuyentrolinhtinh.web.rest.shared.context;

import org.springframework.security.core.GrantedAuthority;
 
public class MystoryAuthority implements GrantedAuthority {
 
    private static final long serialVersionUID = 1L;
    private String permission;
    
    public MystoryAuthority(String permission){
    	this.permission = permission;
    }
   

	@Override
	public String toString() {
		return "MystoryAuthority [permission=" + permission + "]";
	}

	@Override
    public String getAuthority() {
        return this.permission;
    }
 
    
}