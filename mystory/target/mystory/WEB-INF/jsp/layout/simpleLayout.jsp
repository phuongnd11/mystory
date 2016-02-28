<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="<spring:message code='lang.current'/>">
	<spring:eval expression="@applicationProperties.getProperty('environment')" var="serverStatus" scope="application"/>
	
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<tiles:importAttribute name="title" toName="pageTitle" scope="request" />
		
		<title><spring:message code="page.title.${pageTitle}" /></title>
		
		<jsp:include page="css-head.jsp" />		
		<jsp:include page="js-head.jsp" />
	</head>
	
	<tiles:insertAttribute name="body" />
</html>