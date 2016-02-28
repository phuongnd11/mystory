<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="sidebar_controls top_tellers">
	<fieldset>
		<legend><spring:message code="label.top.tellers.title"/></legend>
	</fieldset>
	<div>
		<c:forEach items="${topTellers }" var="topTeller" varStatus="status">
			<div class="author_upper">
				<a href="<c:url value='/user/${ topTeller.name}' />" class="author_avatar_container">
					<c:if test="${ not empty topTeller.avatar }">
						<img width="60" height="60" class="user_avatar" src="${topTeller.avatar }" alt="${ topTeller.name}">
					</c:if>
				</a>
				<div class="author_name_container">
					<a class="author_username" href="<c:url value='/user/${ topTeller.name}' />">${ topTeller.name }</a>
					<label class="author_story_points" title="<spring:message code="label.user.points" arguments="${ topTeller.name },${ topTeller.points }"/>">${ topTeller.points }</label>
				</div>
			</div>
		</c:forEach>
	</div>
</div>