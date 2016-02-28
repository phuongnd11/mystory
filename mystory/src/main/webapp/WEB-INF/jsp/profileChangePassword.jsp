<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="content_wrapper">
	<div class="profile_container">
		<div class="col-md-12">
			<form action="" id="form-update-password" class="form-horizontal nopopup_validate" method="POST">
				<fieldset>
					<legend><spring:message code="label.user.password.change"/></legend>
					<div id="change_password_errors">
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label" for="oldPassword"><spring:message code="label.user.password.old"/></label>
						<div class="col-md-9">
							<input type="password" class="form-control" name="oldPassword"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label" for="newPassword"><spring:message code="label.user.password.new"/></label>
						<div class="col-md-9">
							<input type="password" class="form-control" name="newPassword"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-3 col-md-9">
							<input type="button" id="profile_update_password" class="btn btn_important" value="<spring:message code="label.user.option.save"/>"/>
							<input type="button" id="profile_back_password" class="btn" value="<spring:message code="label.btn.cancel"/>"/>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>