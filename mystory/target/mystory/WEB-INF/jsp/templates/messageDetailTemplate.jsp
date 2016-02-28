<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/template" id="messageDetailTemplate">
<form action="<c:url value='/account/replyMessage' />" class="nopopup_validate message_form" id="mailbox_message_detail_form" name="message_form" method="post">
	<fieldset>
		<input type="hidden" name="title" value="{{title}}" />
		<input type="hidden" name="from" value="{{from}}" />
		<input type="hidden" name="to" value="{{to}}" />

		<legend class="title">{{title}}</legend> 		
		<div class="form-group">
			<spring:message code="label.mailbox.fromto" arguments="<a class='username' href='${request.contextPath}/user/{{from}}'>{{from}}</a>, <a class='username' href='${request.contextPath}/user/{{to}}'>{{to}}</a>"/>			
		</div>
		<div class="form-group content">{{{content}}}</div>
		<div class="post_subinfo footer_submit_time">{{submittedTime}}</div>
		<div class="divider"></div>
		<div id="reply_container" class="reply_container">
			<div class="form-group">
            	<textarea class="mys_input full_width_textarea" name="content" rows="7" id="mys-input-message-reply"></textarea>
			</div>
			<div class="form-group">
				<input type="button" class="btn btn_important" id="mailbox_reply_btn" value="<spring:message code="label.btn.message.send"/>"/>
				<label style="color:red;" id="mailbox_error_message"></label>
			</div>
		</div>
		<div class="form-group">
			<input type="button" class="btn btn_important" id="mailbox_reptoggle_btn" value="<spring:message code="label.btn.message.reply"/>"/>
		</div>
	</fieldset>
</form>
</script>
