package com.inspireon.mystory.web.rest.base;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.inspireon.mystory.web.rest.shared.i18n.I18NUtils;

@Controller
public abstract class AbstractBaseController {

	public static final String BLANK = StringUtils.EMPTY;
	public static final String REDIRECT_LOGIN = "redirect:/login";
	public static final String REDIRECT_HOME = "redirect:/home";
	public static final String REDIRECT_NOT_FOUND = "redirect:/notfound";
	public static final String REDIRECT_REGISTER = "redirect:/register";

	@Autowired
	public I18NUtils i18nUtils;

	public Response success() {
		return success(BLANK, null);
	}
	
	public Response success(Object data) {
		return success(BLANK, data);
	}
	
	public Response success(String messageCode) {
		return success(messageCode, null);
	}

	public Response success(String messageCode, Object data) {
		String message = StringUtils.isEmpty(messageCode) ? BLANK : i18nUtils.getMessage(messageCode);
		return new Response(Status.SUCCESS, message, data);
//		if (data instanceof DTO) {
//			return new Response(Status.SUCCESS, message, (DTO)data);
//		} else {
//			if (data instanceof String) {
//				return new Response(Status.SUCCESS, message, new StringDTO((String)data));
//			} else if (data instanceof List) {
//				return new Response(Status.SUCCESS, message, new ListDTO((List)data));
//			} else {
//				return new Response(Status.SUCCESS, message, null);
//			}
//		}		
	}
	
	public Response failure() {
		return failure(BLANK, null);
	}
	
	public Response failure(Object data) {
		return failure(BLANK, data);
	}
	
	public Response failure(String messageCode) {
		return failure(messageCode, null);
	}

	public Response failure(String messageCode, Object data) {
		String message = StringUtils.isEmpty(messageCode) ? BLANK : i18nUtils.getMessage(messageCode);
		return new Response(Status.FAILURE, message, data);
	}
	
	public static class Response implements Serializable {

		private static final long serialVersionUID = 1L;

		private Status status;

		private String message;
		
		private Object data;
		
		public Response(Status status, String message, Object data) {
			this.status = status;
			this.message = message;
			this.data = data;
		}		

		public Status getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}
		
		public Object getData() {
			return data;
		}		
	}
	
	
	public static class ValidateResponse implements Serializable {

		private static final long serialVersionUID = 1L;
		
		
		
		/*public Response(Status status, String message) {
			this(status, message, null);
		}
		
		public Response(Status status, String message, DTO data) {
			this.status = status;
			this.message = message;
			this.data = data;
		}		

		public Status getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}
		
		public DTO getData() {
			return data;
		}		*/
	}
}

enum Status {
	SUCCESS, FAILURE;
}