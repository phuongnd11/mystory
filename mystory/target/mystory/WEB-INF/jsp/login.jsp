<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<body class="login">
	<div class="ui_block_element simple_page_element">
		<ul class="simple_page_tab">
			<li>
				<span class="login_tab"><spring:message code= "label.login"/></span>
			</li>
			<li>
				<a href="<c:url value='/register'/>" class="register_tab"><spring:message code="label.btn.sign_up"/></a>
			</li>
		</ul>
		<form id="form-signin" class="form" action="<c:url value='/j_spring_security_check'/>" method="post">
			<fieldset>
				<c:if test="${not empty param.error}">
					<div class="form-group">
						<label class="error nopopup"><spring:message code="login.wrong"/></label>
					</div>
				</c:if>
				<div class="form-group">
					<input type="text" id="j_username" name="j_username" class="form-control" maxlength="20" placeholder="<spring:message code="label.username"/>" autofocus>
				</div>

				<div class="form-group">
					<input type="password" id="j_password" name="j_password" class="form-control" placeholder="<spring:message code="label.password"/>">
				</div>
				<div class="row">
					<div class="col-md-7">
						<div class="checkbox">
							<label>
								<input type="checkbox" id="remember" name="_spring_security_remember_me" checked="checked">
								<spring:message code="label.remember"/>
							</label>
						</div>
						<div class="checkbox">
							<label>
								<a href="<c:url value='/resetpassword'/>"><spring:message code="label.forgot.password"/></a>
							</label>
						</div>
					</div>
					<div class="col-md-5 submit_box">
						<button class="btn btn_important" type="submit"><spring:message code="label.btn.sign_in"/></button>
					</div>
				</div>
				<div class="divider in_form"></div>
				<div class="form-group">
					<a href="<c:url value='/social/facebooklogin'/>" class="btn btn_facebook full_width_input"><spring:message code="label.facebook.login"/></a>
				</div>
			</fieldset>
		</form>
	</div>
	<jsp:include page="/WEB-INF/jsp/layout/helper_modal.jsp" />
</body>
