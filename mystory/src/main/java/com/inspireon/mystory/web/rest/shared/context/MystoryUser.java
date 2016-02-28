package com.inspireon.mystory.web.rest.shared.context;
 
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inspireon.mystory.model.domain.user.Status;
 
public class MystoryUser implements UserDetails {
    private static final long serialVersionUID = 1L;
 
    private String username;
    private String password;
    private String lang;
    private String currentLang; // to keep current language in session that user selected
    private String role;
    private String userId;
    private Status status;
 
    /* Spring Security fields*/
    private List<MystoryAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
 
    public MystoryUser(String userId, String username, String password, String lang, 
    					String role, Status status, List<MystoryAuthority> authorities){
    	  this.userId = userId;
    	  this.username = username; 
    	  this.password = password;
    	  this.userId = userId;
    	  this.lang = lang;
    	  this.role = role;
    	  this.currentLang = lang;
    	  this.authorities = authorities;
    	  this.status = status;
    }    	 
    
    @Override
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
     
    @Override
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getCurrentLang() {
		return currentLang;
	}

	public void setCurrentLang(String currentLang) {
		this.currentLang = currentLang;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
 
    public void setAuthorities(List<MystoryAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
     
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
     
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
     
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
 
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
    public boolean isEnabled() {
        return this.enabled;
    }
 
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Status getStatus() {
		return status;
	}
    
	@Override
	public String toString() {
		return "MystoryUser [username=" + username + ", password=" + password
				+ ", locale=" + lang + ", currentLang=" + lang
				+ ", authorities=" + authorities + ", accountNonExpired="
				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + "]";
	}    
}