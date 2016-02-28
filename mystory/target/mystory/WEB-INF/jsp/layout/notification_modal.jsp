<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="modal fade" id="notification_overlay" tabindex="-1">
	<div class="modal-dialog modal_notification">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModalLabel"><spring:message code="label.notification.title" /></h4>
			</div>
			<div class="modal-body">
				<ul id="notification_detail" class="notification_detail">
					<c:if test="${empty userPanel.notifications}">
						<li><spring:message code="label.notification.no.new" /></li>
					</c:if>
					
					<c:forEach items="${userPanel.notifications}" var="notif" begin="0" end="9">
						<li <c:if test="${ not notif.read }">class="not_read"</c:if>>
							<p>${ notif.message }: <a href="<c:url value='${ notif.url }'/>">${ notif.sourceTitle }</a></p>
							<p class="notif_action_date"><i class="icon icon_bubbles-2"></i><span>${ notif.actionDate }</span></p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<c:if test="${not empty userPanel.notifications }">
				<div class="modal-footer all_notification">
					<a href="<c:url value='/account/notification'/>"><spring:message code="label.notification.see.all"/></a>
				</div>
			</c:if>
		</div>
	</div>
</div>