package com.inspireon.chuyentrolinhtinh.web.rest.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class MyStoryLogInSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger logger = Logger
			.getLogger(MyStoryLogInSuccessHandler.class);

	 private RequestCache requestCache = new HttpSessionRequestCache();
	
/*	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		Collection<? extends GrantedAuthority> authorities = authentication
				.getAuthorities();

		if (isRolePresent(authorities, "USER")) {
			setDefaultTargetUrl("/home/");
		} else if (isRolePresent(authorities, "ADMIN")) {
			setDefaultTargetUrl("/admin");
			
			logger.info("Administrator is logged in");

		}
		 
		request.getSession().setAttribute("currentUser", authentication.getPrincipal());
		
		super.onAuthenticationSuccess(request, response, authentication);
	}*/
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
		
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        if (isAlwaysUseDefaultTargetUrl() || StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

	/**
	 * Check if a role is present in the authorities of current user
	 * 
	 * @param authorities
	 *            all authorities assigned to current user
	 * @param role
	 *            required authority
	 * @return true if role is present in list of authorities assigned to
	 *         current user, false otherwise
	 */
	private boolean isRolePresent(
			Collection<? extends GrantedAuthority> authorities, String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equalsIgnoreCase(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}
	
	public void setRequestCache(RequestCache requestCache) {
	      this.requestCache = requestCache;
	}
}
