<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%--

<c:if test="${ not empty userPanel }">
	<div class="sidebar_controls">
		<form action="<c:url value='/user/invite'/>" id="email_invitation" method="post" >
			<fieldset>
				<legend>
					<spring:message code="label.beta.key.description"/>
				</legend>
				<div id="email_invite_status"></div>
				<div>
					<button type="submit" id="email_invite_btn" class="btn email_invite_btn"><spring:message code="label.btn.send"/></button>
					<input type="text" name="email" id="email_invite_textfield" class="full_width_input" placeholder="<spring:message code="label.beta.key.receiver.email"/>">
				</div>
				<p><a href="<c:url value='/user/invitecodes' />"><spring:message code="label.beta.key.status.mine"/></a></p>
			</fieldset>
		</form>
	</div>
</c:if>

--%>