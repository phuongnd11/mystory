<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body class="register">
	<div class="ui_block_element simple_page_element">
		<ul class="simple_page_tab">
			<li>
				<a href="<c:url value='/login'/>" class="login_tab"><spring:message code= "label.login"/></a>
			</li>
			<li>
				<span class="register_tab"><spring:message code="label.btn.sign_up"/></span>
			</li>
		</ul>
		<form id="form-signup" class="form" action="<c:url value='/user/register' /> " method="post">
			<c:if test="${not empty param.error}">
				<div id="register-error" class="alert">
					<a class="close" data-dismiss="alert">&times;</a>${response.message}
				</div>
			</c:if>
			<fieldset>
				<div class="form-group">
					<input type="text" id="username" name="username" maxlength="20" class="form-control" placeholder="<spring:message code="label.username"/>" <c:if test="${not empty fbresponse}">value="${fbresponse.username }"</c:if>>
				</div>
				<div class="form-group">
					<input type="password" id="password" name="password" class="form-control" placeholder="<spring:message code="label.password"/>">
				</div>
				<div class="form-group">
					<input type="email" id="email" name="email" class="form-control" placeholder="<spring:message code="label.email"/>" <c:if test="${not empty fbresponse}">value="${fbresponse.email }"</c:if>>
				</div>
				<div class="form-group">
					<p class="help-block"><spring:message code="label.register.tnc" /></p>
				</div>
				<div class="form-group submit_box">
					<a href="<c:url value='/login'/>" class="btn"><spring:message code="label.btn.cancel"/></a>
					<button class="btn btn_important" type="submit"><spring:message code="label.btn.sign_up"/></button>
				</div>
			</fieldset>
		</form>
	</div>
	<div class="modal fade" id="tnc_overlay" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="tnc_modal_label">
						<spring:message code="label.tnc" />
					</h4>
				</div>
				<div class="modal-body" id="tnc_modal_message">
					<jsp:include page="/WEB-INF/jsp/snippets/termsandconditions.jsp" />
				</div>
			</div>
		</div>
	</div>
</body>