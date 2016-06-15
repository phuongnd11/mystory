package com.inspireon.chuyentrolinhtinh.web.rest.shared.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	/*private static final String CONTENT_SKIP [] = {"content", "email"};
	
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
    	boolean skippable = false;
    	for(int i=0;i<CONTENT_SKIP.length;i++){
    		if(CONTENT_SKIP[i].equalsIgnoreCase(parameter)){
    			skippable = true;
    			break;
    		}
    	}
    	
    	if(!skippable){
    	
	        String[] values = super.getParameterValues(parameter);
	
	        if (values == null) {
	            return null;
	        }
	
	        int count = values.length;
	        String[] encodedValues = new String[count];
	        for (int i = 0; i < count; i++) {
	            encodedValues[i] = stripXSS(values[i]);
	        }
	    	
	        return encodedValues;
    	}
    	else 
    		return super.getParameterValues(parameter);
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);

        //return stripXSS(value);
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    	return super.getHeader(name);
    }

    private String stripXSS(String value) {
        if (value != null && value.length() > 0) {
        	value = HTMLUtils.sanitize(value);
        }
        
        return value;
    }*/
    
}