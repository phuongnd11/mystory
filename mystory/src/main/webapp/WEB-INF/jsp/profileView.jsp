<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="content_wrapper">
	<div class="profile_container">
		<div class="col-md-3 profile_side_panel">
			<div class="user_avatar profile_avatar">
				<img width="150" height="150" src="${ profile.avatarImage }" alt="${ profile.username }"/>
			</div>
			<ul class="profile_nav">
				<li><a href="<c:url value='/account/mailbox' />?to=${ profile.username }" id="profile_compose_msg_btn"><spring:message code="label.user.send.message"/></a></li>
			</ul>
			
		</div>
		
		<div class="col-md-9 info_container">
			<form class="form-horizontal">
				<fieldset>
					<legend><spring:message code="label.user.personal.info"/></legend>
				</fieldset>
				<div class="profile_info_row">
					<label class="profile_info_name"><spring:message code="label.username" /></label>
					<span class="profile_info_value" id="profile_view_username" ><c:out value="${ profile.username }"/></span>
				</div>
				<c:if test="${ not empty profile.slogan }">
					<div class="profile_info_row">
						<label class="profile_info_name"><spring:message code="label.user.slogan" /></label>
						<span class="profile_info_value">${ profile.slogan }</span>
					</div>
				</c:if>
				<div class="profile_info_row">
					<label class="profile_info_name"><spring:message code="label.user.member.since"/></label>
					<span class="profile_info_value">${ profile.joinedDate }</span>
				</div>
				<div class="profile_info_row">
					<label class="profile_info_name"><spring:message code="label.user.statistic"/></label>
					<span class="profile_info_value">
						<a class="profile_statistic story_count_link" href="<c:url value='/user/${profile.username}/story'/>" title="<spring:message code="label.user.story" arguments="${profile.username},${ profile.numberOfStories }"/>" ><span>${ profile.numberOfStories }</span></a>
						<c:choose>
							<c:when test="${ userPanel.name eq profile.username }">
								<span class="profile_statistic follower_count_link" title="<spring:message code="label.user.followers" arguments="${profile.username},${ profile.numberOfFollowers }"/>" ><span>${ profile.numberOfFollowers }</span></span>
							</c:when>
							<c:otherwise>
								<button type="button" class="profile_statistic follower_count_link btn_follow ${ profile.followed ? 'is_followed' : '' }" data-username="${profile.username}" title="<spring:message code="label.user.followers" arguments="${profile.username},${ profile.numberOfFollowers }"/>" ><span>${ profile.numberOfFollowers }</span></button>
							</c:otherwise>
						</c:choose>
						
						<span class="profile_statistic user_story_points" title="<spring:message code="label.user.points" arguments="${profile.username},${ profile.points }"/>" ><span>${ profile.points }</span></span>
					</span>
				</div>
			</form>
		</div>
	</div>
</div>