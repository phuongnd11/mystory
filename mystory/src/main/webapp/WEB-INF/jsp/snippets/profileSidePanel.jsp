<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-md-3 profile_side_panel">
	<div class="user_avatar profile_avatar">
		<img id="avatar" width="150" height="150" src="${ profile.avatarImage }" alt="${ profile.data.username }"/>
		<div class="action_panel">
			<span class="btn btn_edit_avatar btn_important" id="btn_edit_avatar">
				<spring:message code="label.user.change.avatar"/>
				<input id="fileupload_avatar" type="file" class="hidden_upload" name="files[]" accept="image/*" data-url="/account/updateAvatar">
			</span>
		</div>
	</div>
	<div class="progress_bar hide" id="fileupload_avatar_progress_bar">
		<div class="progress_container">
			<div class="progress"></div>
		</div>
		<span class="progress_percent"></span>
	</div>
	<ul class="profile_nav">
		<li <c:if test="${ profile.currentTab == 'PROFILE' }"> class="selected"</c:if> ><a id="user_profile_tab" href="<c:url value='/account/profile' />"><spring:message code="label.profile.title" /></a></li>
		<li <c:if test="${ profile.currentTab == 'MAILBOX' }"> class="selected"</c:if> ><a id="user_mailbox_tab" href="<c:url value='/account/mailbox' />"><spring:message code="label.message.title" /></a></li>
		<li <c:if test="${ profile.currentTab == 'NOTIFICATION' }"> class="selected"</c:if> ><a id="user_notification_tab" href="<c:url value='/account/notification' />"><spring:message code="label.notification.title" /></a></li>
		<li><a href="<c:url value='/j_spring_security_logout' />"><spring:message code="label.btn.sign_out"/></a></li>
	</ul>
</div>