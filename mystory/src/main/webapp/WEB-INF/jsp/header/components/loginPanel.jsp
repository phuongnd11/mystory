<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="user_panel" id="user_panel">
	<a href="<c:url value='/login'/>" class="btn btn_header"><spring:message code="label.btn.sign_in"/></a>
	<a href="<c:url value='/social/facebooklogin'/>" class="btn btn_header btn_facebook"><spring:message code="label.facebook.login"/></a>
</div>