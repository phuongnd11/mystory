<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="content_wrapper">
	<div class="profile_container">
		<jsp:include page="/WEB-INF/jsp/snippets/profileSidePanel.jsp"></jsp:include>
		<div class="col-md-9 info_container">
			<fieldset><legend><spring:message code="label.notification.title"/></legend></fieldset>
			<ul class="notification_detail list_all_notification">
				<c:if test="${empty userPanel.notifications}">
					<li><spring:message code="label.notification.no.new" /></li>
				</c:if>
				
				<c:forEach items="${userPanel.notifications}" var="notif">
					<li>
						<p>${ notif.message }: <a href="<c:url value='${ notif.url }'/>">${ notif.sourceTitle }</a></p>
						<p class="notif_action_date"><i class="icon icon_bubbles-2"></i><span>${ notif.actionDate }</span></p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>