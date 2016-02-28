<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="content_wrapper error_page">
	<div class="error_page_inner">
		<%--<img src="/resources/imgs/errors/404.png" alt="<spring:message code="error.title.403"/>"/> --%>
		
		<h1><spring:message code="error.title.403"/></h1>
		<div><spring:message code="error.message.403"/></div>
	</div>
</div>