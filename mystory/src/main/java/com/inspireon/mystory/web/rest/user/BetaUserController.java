package com.inspireon.mystory.web.rest.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.inspireon.mystory.application.BetaInvitationService;
import com.inspireon.mystory.application.UserService;
import com.inspireon.mystory.exception.DuplicateUsernameException;
import com.inspireon.mystory.exception.EmailAlreadyInUseException;
import com.inspireon.mystory.web.rest.base.AbstractBaseController;
import com.inspireon.mystory.web.rest.shared.i18n.I18NCode;

/**
 * 
 * @author Phuong
 *
 */
@Controller
@RequestMapping("/user")
public class BetaUserController extends AbstractBaseController{
	
	private static final Logger logger = Logger.getLogger(BetaUserController.class);
	
	@Autowired
	private BetaInvitationService betaInvitationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;
	
	/*@RequestMapping(method = RequestMethod.POST, value = "/invite")
	public @ResponseBody 
	Response sendInvitation(@RequestBody InviteCommand inviteCommand){
		
		String username = null;
		
		if(MystoryUserReference.getLoggedInUser() != null)
			username = MystoryUserReference.getLoggedInUser().getUsername(); 
		
		if(!StringUtils.isEmpty(username)){
			try {
				betaInvitationService.sendInvitation(username, inviteCommand.getEmail());
			} catch (AddressException e) {
				e.printStackTrace();
				return failure(I18NCode.MESSAGE_USER_INVITE_FAIL);
			} catch (MessagingException e) {
				e.printStackTrace();
				return failure(I18NCode.MESSAGE_USER_INVITE_FAIL);
			}
			return success(I18NCode.MESSAGE_USER_INVITE_SUCCESS);
		}
		
		return failure(I18NCode.MESSAGE_USER_INVITE_FAIL); 
	}*/
	

	/*@RequestMapping(method = RequestMethod.POST, value = "/invitecode/generate")
	public @ResponseBody 
	Response generateInvitationCode(){
		
		String username = null;
		
		if(MystoryUserReference.getLoggedInUser() != null)
			username = MystoryUserReference.getLoggedInUser().getUsername(); 
		
		if(!StringUtils.isEmpty(username)){
			InvitationCode invitationCode = betaInvitationService.generateInvitationCode(username);
			InvitationCodeViewAdapter invitationCodeViewAdapter = new InvitationCodeViewAdapter(invitationCode);
			
			return success(BLANK, invitationCodeViewAdapter);
		}
		
		return failure(I18NCode.MESSAGE_USER_INVITE_FAIL); 
	}*/
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/invitecodes")
	public ModelAndView findAllInvitationCodes(){
		
		String username = null;
		
		if(MystoryUserReference.getLoggedInUser() != null)
			username = MystoryUserReference.getLoggedInUser().getUsername(); 
		
		if(!StringUtils.isEmpty(username)){
			Collection<InvitationCode> invitationCodes = betaInvitationService.findByUser(username);
			
			List<InvitationCodeViewAdapter> inviteCodes = new ArrayList<InvitationCodeViewAdapter>();
			
			for(InvitationCode iCode : invitationCodes){
				inviteCodes.add(new InvitationCodeViewAdapter(iCode));
			}
			
			return new ModelAndView("inviteCodes", "codes", inviteCodes);
		}
		
		return new ModelAndView("inviteCodes", "codes", null);
	}*/
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ModelAndView register(@ModelAttribute RegisterCommand registerCommand, HttpServletRequest request){
		
		try {
			if(userService.betaRegister(registerCommand.getUsername(), registerCommand.getPassword(),
					registerCommand.getEmail())){
				
				UsernamePasswordAuthenticationToken token = new 
						UsernamePasswordAuthenticationToken(registerCommand.getUsername(), registerCommand.getPassword());
				
				Authentication authentication = authenticationManager.authenticate(token);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);

				request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
				
				return new ModelAndView("redirect:/home", "register", true);
			}
			else{
				return new ModelAndView("redirect:/home", "register", false);
			}
		} catch (DuplicateUsernameException e) {
			return new ModelAndView("register", "response", failure(I18NCode.MESSAGE_USER_REGISTER_FAIL));
		} catch (EmailAlreadyInUseException e) {
			return new ModelAndView("register", "response", failure(I18NCode.MESSAGE_USER_REGISTER_FAIL));
		}
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/check-valid-code/{code}")
	public @ResponseBody Response checkValidCode(@PathVariable("code") String code){
		try{
			if(betaInvitationService.checkValidCode(code))
				return success();
		} catch(Exception e){
			return failure();
		}
		
		return failure();
	}*/
}
