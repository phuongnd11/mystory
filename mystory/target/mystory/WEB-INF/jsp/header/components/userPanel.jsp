<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set scope="request" var="newStoryBtnLink" value="/story/newStory" />
<c:forEach items="${menuCategories}" var="category" begin="1">
	<c:if test="${ category.selected }">
		<c:set scope="request" var="newStoryBtnLink" value="/story/newStory?category=${category.name}" />
	</c:if>
</c:forEach>

<div class="user_panel" id="user_panel">
	<a href="<c:url value='${newStoryBtnLink}' />" class="btn btn_header btn_new_topic" title="<spring:message code="label.btn.new.story"/>" id="new_topic"><spring:message code="label.btn.new.story"/></a>
	<a id="notification_toggle" class="notification <c:if test="${ userPanel.notificationCount == 0}">nonew</c:if>">
		<span class="notification_count" id="notification_count">${ userPanel.notificationCount }</span>
	</a>
	<div class="user_avatar current_user_avatar">
		<a href="<c:url value='/account/profile' />"><img width="60" height="60" id="sidebar_avatar" class="avatar user_avatar photo" src="${ userPanel.avatar }" alt="${userPanel.name }"></a>
	</div>
	
	<div class="user_settings">
		<a href="<c:url value='/account/profile' />"><img src="/resources/imgs/icon_settings.png" /></a>
		<div class="user_settings_dialog">
			<ul class="user_settings_menu">
				<li><a href="<c:url value='/account/profile' />"><spring:message code="label.profile.title" /></a></li>
				<li><a href="<c:url value='/account/mailbox' />"><spring:message code="label.message.title" /></a></li>
				<li><a href="<c:url value='/j_spring_security_logout' />"><spring:message code="label.btn.sign_out"/></a></li>
			</ul>
		</div>
	</div>
</div>