package com.inspireon.mystory.web.rest.social;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.model.domain.user.User;
import com.inspireon.mystory.model.domain.user.UserRepo;
import com.inspireon.mystory.model.manager.UserManager;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.image.FileMeta;
import com.inspireon.mystory.web.rest.shared.context.MystoryAuthority;
import com.inspireon.mystory.web.rest.shared.context.MystoryUser;

/**
 * 
 * @author Phuong
 *
 */

@Controller
@RequestMapping("/social/")
public class SocialConnectController extends AbstractBaseController{
	
		private static final Logger logger = Logger.getLogger(SocialConnectController.class);

		@Value(value = "${facebook.app.id}")
		private String facebookAppId;
		
		@Value(value= "${facebook.app.secret}")
		private String facebookAppSecret;
		
		@Value(value = "${facebook.app.exchangekey}")
		private String exchangeKey;
		
		private String facebookRedirect = "http://chuyentrolinhtinh.vn/social/facebookauthentication?_spring_security_remember_me=true";
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private UserManager userManager;
		
		@Autowired
		private UserRepo userRepo;
		
		@Autowired
		@Qualifier("authenticationManager")
		protected AuthenticationManager authenticationManager;
		
		@Autowired
		private RememberMeServices rememberMeServices;
		
		@RequestMapping(value = "/facebooklogin")
		public void getFacebookLogin(HttpServletRequest request,HttpServletResponse response) {
			String url="https://www.facebook.com/dialog/oauth/?"
					+ "client_id=" + facebookAppId
					+ "&redirect_uri=" + facebookRedirect
					+ "&scope=email,publish_stream,user_about_me,friends_about_me"
					+ "&state=" + exchangeKey
					+ "&display=page"
					+ "&response_type=code";
			
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("error redirect to facebook authentication");
			}
		}

		
		@RequestMapping(value = "/facebookauthentication", method=RequestMethod.GET)
		public ModelAndView facebookAuthentication(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
			
			String code = request.getParameter("code");
			String accessToken = getAccessToken(code);
			logger.info("------------ FaceBook access token: " + accessToken);
			User user = null;
				
			try{
			
				Facebook facebook = new FacebookTemplate(accessToken);
				
				String email = facebook.userOperations().getUserProfile().getEmail();
				String username = facebook.userOperations().getUserProfile().getName();
				String facebookUsername = facebook.userOperations().getUserProfile().getUsername();
				
				User existingUser = userRepo.findByUsername(username);
				
				if(existingUser == null){
					
					User userWithEmail = userRepo.findByEmail(email);
					
					if(userWithEmail == null){
					
						String password = userManager.generateRandomPassword();
						
						FileMeta fm = new FileMeta("jpg", facebook.userOperations().getUserProfileImage(ImageType.LARGE));
						
						user = userService.registerWithFacebook(username, password, email, facebookUsername, fm);
						
						UsernamePasswordAuthenticationToken token = new 
								UsernamePasswordAuthenticationToken(user.username(), password);
						
						Authentication authentication = authenticationManager.authenticate(token);
						
						SecurityContextHolder.getContext().setAuthentication(authentication);
					
						request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
						return new ModelAndView(REDIRECT_HOME, "resfb", true);
					}
					else{
						RegisterWithFacebookResponse reFBResponse = new RegisterWithFacebookResponse(username, email, facebookUsername, "");
						redirectAttributes.addFlashAttribute("fbresponse", reFBResponse);
						return new ModelAndView(REDIRECT_REGISTER);
					}
				}
				
				else if(existingUser.email().equalsIgnoreCase(email)){
					if(facebookUsername.equalsIgnoreCase(existingUser.setting().facebookSetting().facebook())){
						List<MystoryAuthority> authorities  = new ArrayList<MystoryAuthority>();
						
						authorities.add(new MystoryAuthority(existingUser.role().toString()));
						Authentication authentication = new 
								UsernamePasswordAuthenticationToken(new MystoryUser(existingUser.id(), existingUser.username(), existingUser.password(), existingUser.setting().lang(), 
										existingUser.role().toString(), existingUser.status(), authorities), 
										null, AuthorityUtils.createAuthorityList("USER"));
						
						SecurityContextHolder.getContext().setAuthentication(authentication);
					
						request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
						
						rememberMeServices.loginSuccess(request, response, authentication);
						
						return new ModelAndView(REDIRECT_HOME);
					}
					
				}
				
				else{
					// username existed
					RegisterWithFacebookResponse reFBResponse = new RegisterWithFacebookResponse(username, email, facebookUsername, "");
					redirectAttributes.addFlashAttribute("fbresponse", reFBResponse);
					return new ModelAndView(REDIRECT_REGISTER);
				}
						
				
			}
			catch (BadCredentialsException e) {
				 logger.error("error logging in with facebook");
			     logger.error(e.getMessage());
			} catch (LockedException e) {
				 logger.error("error logging in with facebook");
			     logger.error(e.getMessage());
			} catch (DisabledException e) {
				 logger.error("error logging in with facebook");
			     logger.error(e.getMessage());
			} 
			
			return null;
		}
		
		

		private String getAccessToken(String code){
			String accessToken = "";
			
			if(code != null && code.length() > 0){
				//If we received a valid code, we can continue to the next step
	            //Next we want to get the access_token from Facebook using the code we got, 
				//use the following url for that, in this url,
				//client_id-our app id(same as above), redirect_uri-same as above, client_secret-same as above
				
				String url = "https://graph.facebook.com/oauth/access_token?"
		                 + "client_id=" + facebookAppId
		                 + "&redirect_uri=" + facebookRedirect
		                 + "&client_secret=" + facebookAppSecret
		                 + "&code=" + code;
				 
				HttpClient httpClient = HttpClientBuilder.create().build();
					
				HttpGet httpGet = new HttpGet(url);
				
				
				try{
					
					// Execute the method.
				    HttpResponse httpResponse = httpClient.execute(httpGet);
				    
				    if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				       logger.error(httpResponse.getStatusLine());
					else{
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(httpResponse.getEntity().getContent()));
						 
							String line = "";
							while ((line = rd.readLine()) != null) {
								for(String lineSplit : line.split("&")){
									if(lineSplit!=null && lineSplit.contains("access_token")){
										accessToken = lineSplit.replace("access_token=", "").trim();
									}
								}
							}
						
					}
				}  catch (IOException e) {
				     logger.error("error logging in with facebook");
				     logger.error(e.getMessage());
				} 
				
				finally {
				     // Release the connection.
				     httpGet.releaseConnection();
			    } 
					
					
			}
			return accessToken;
		}
}
