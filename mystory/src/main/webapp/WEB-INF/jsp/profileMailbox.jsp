<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="content_wrapper">
	<div class="profile_container">
		<jsp:include page="/WEB-INF/jsp/snippets/profileSidePanel.jsp"></jsp:include>
			
		<div class="info_container col-md-9">
			<button id="mailbox_compose_btn" class="btn btn_important btn_compose_mailbox"><spring:message code="label.mailbox.compose"/></button>
			<ul class="message_tab">
				<li class="selected"><a id="mailbox_inbox_btn" href="#"><spring:message code="label.mailbox.inbox"/></a></li>
				<li><a id="mailbox_sent_btn" href="#"><spring:message code="label.mailbox.sent"/></a></li>
			</ul>
			
			<div id="messagebox" class="messagebox"></div>
		</div>
	</div>
</div>