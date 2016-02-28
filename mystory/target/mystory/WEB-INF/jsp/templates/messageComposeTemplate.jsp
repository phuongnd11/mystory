<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/template" id="messageComposeTemplate">
<form action="<c:url value='/account/sendMessage' />" class="nopopup_validate message_form" id="mailbox_send_message_form" name="message_form" method="post">
	<div class="form-group">
		<input type="text" class="form-control" placeholder="<spring:message code='label.mailbox.to'/>" name="to" value="{{{ msgTo }}}"/>
	</div>
	<div class="form-group">
		<input type="text" class="form-control" placeholder="<spring:message code='label.mailbox.subject'/>" name="title" value=""/>
	</div>
	<div class="form-group">
       	<textarea class="mys_input full_width_textarea" name="content" rows="7" id="mys-input-message"></textarea>
	</div>
	<div class="form-group">
		<input type="submit" class="btn btn_important" id="mailbox_send_btn" value="<spring:message code="label.btn.message.send"/>"/>
		<label class="color:red;" id="mailbox_error_message"></label>
	</div>
</form>
</script>