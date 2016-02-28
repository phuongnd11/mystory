<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul class="top_tab">
	<li <c:if test="${ profile.currentTab == 'PROFILE' }"> class="selected"</c:if> ><a id="user_profile_tab" href="<c:url value='/account/profile' />"><spring:message code="label.profile.title" /></a></li>
	<li <c:if test="${ profile.currentTab == 'MAILBOX' }"> class="selected"</c:if> ><a id="user_mailbox_tab" href="<c:url value='/account/mailbox' />"><spring:message code="label.message.title" /></a></li>
	<li <c:if test="${ profile.currentTab == 'NOTIFICATION' }"> class="selected"</c:if> ><a id="user_notification_tab" href="<c:url value='/account/notification' />"><spring:message code="label.notification.title" /></a></li>
</ul>