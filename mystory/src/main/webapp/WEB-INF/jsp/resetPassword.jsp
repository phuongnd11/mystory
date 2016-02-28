<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body class="reset">
	<div class="ui_block_element simple_page_element">
		<form id="form-reset-password" class="form" action="<c:url value='/user/password-reset'/>" method="post">
			<fieldset>
				<legend class="form-signin-heading"><spring:message code="label.password.reset.title"/></legend>
				<c:if test="${ param.error == 1 }">
					<div class="alert">
						<a class="close" data-dismiss="alert">&times;</a><spring:message code="message.user.resetpassword.fail"/>
					</div>
				</c:if>
				<div class="form-group">
					<input type="email" id="email" name="email" class="form-control" placeholder="<spring:message code="label.email"/>">
				</div>
				<div class="submit_box">
					<a href="<c:url value='/login'/>" class="btn"><spring:message code="label.btn.cancel"/></a>
					<button class="btn btn_important" type="submit"><spring:message code="label.password.reset.btn"/></button>
				</div>
				
			</fieldset>			
		</form>
	</div>
</body>