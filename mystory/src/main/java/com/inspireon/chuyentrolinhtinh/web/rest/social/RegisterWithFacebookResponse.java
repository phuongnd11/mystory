package com.inspireon.chuyentrolinhtinh.web.rest.social;

public class RegisterWithFacebookResponse {
	
	private String username;
	
	private String email;
	
	private String facebook;
	
	private String accessToken;
	

	public RegisterWithFacebookResponse(String username, String email,
			String facebook, String accessToken) {
		super();
		this.username = username;
		this.email = email;
		this.facebook = facebook;
		this.accessToken = accessToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
