<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="sidebar_controls recommended_stories" id="recommended_stories">
	<fieldset>
		<legend><spring:message code="label.recommended.title"/></legend>
	</fieldset>
	<c:forEach items="${recommendedStories }" var="recommendedStory" varStatus="recommendedStatus">
		<div class="recommended_item">
			<a href="<c:url value='${recommendedStory.url}'/>"><c:out value="${recommendedStory.shortTitle}"/></a>
		</div>
	</c:forEach>
</div>