<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="content_wrapper">
	<div class="profile_container">
		
		<jsp:include page="/WEB-INF/jsp/snippets/profileSidePanel.jsp"></jsp:include>
		
		<div class="col-md-9 info_container">
			<form action="<c:url value='/account/update'/>" id="profile_edit_form" method="POST">
				<fieldset>
					<legend><spring:message code="label.user.personal.info"/></legend>
					<div class="profile_info_row">
						<label class="profile_info_name"><spring:message code="label.username" /></label>
						<span class="profile_info_value"><c:out value="${ profile.data.username }"/></span>
					</div>
					<div class="profile_info_row">
						<label class="profile_info_name"><spring:message code="label.user.slogan" /></label>
						<span class="profile_info_value">
							<input type="text" class="slogan" name="slogan" maxlength="200" value="${ profile.data.slogan }"/>
							<button type="submit" class="btn btn_important btn_profile_ok"></button>
						</span>
					</div>
					<div class="profile_info_row">
						<label class="profile_info_name"><spring:message code="label.user.member.since"/></label>
						<span class="profile_info_value">${ profile.data.joinedDate }</span>
					</div>
					<div class="profile_info_row">
						<label class="profile_info_name"><spring:message code="label.user.statistic"/></label>
						<span class="profile_info_value">
							<a class="profile_statistic story_count_link" href="<c:url value='/user/${profile.data.username}/story'/>" title="<spring:message code="label.user.story.yours" arguments="${ profile.data.numberOfStories }"/>" ><span>${ profile.data.numberOfStories }</span></a>
							<span class="profile_statistic unfollowable follower_count_link" title="<spring:message code="label.user.followers.yours" arguments="${ profile.data.numberOfFollowers }"/>" ><span>${ profile.data.numberOfFollowers }</span></span>
							<span class="profile_statistic user_story_points" title="<spring:message code="label.user.points.yours" arguments="${ profile.data.points }"/>" ><span>${ profile.data.points }</span></span>
						</span>
					</div>
					<div class="profile_info_row">
						<label class="profile_info_name"><spring:message code="label.password"/></label>
						<span class="profile_info_value">
							<a href="<c:url value='/account/password'/>"><spring:message code="label.user.password.change" /></a>
						</span>
					</div>
					
					<%--<div class="form-group">
						<label class="col-md-3 control-label"><spring:message code="label.user.lang"/></label>
						<div class="col-md-9">
							<label class="radio-inline">
								<input type="radio" name="lang" value="en" <c:if test="${ profile.data.lang == 'en' }">checked</c:if>><span><spring:message code="label.language.en"/></span>
							</label>
							<label class="radio-inline">
								<input type="radio" name="lang" value="vi" <c:if test="${ profile.data.lang == 'vi' }">checked</c:if>><span><spring:message code="label.language.vi"/></span>
							</label>
						</div>
					</div>--%>
					
					<c:if test="${false}">
						<div class="form-group">
							<label class="col-md-3 control-label"><spring:message code="label.user.facebook"/></label>
							<input type="text" name="facebook" value="${ profile.data.facebook }" />
						</div>
						<div class="checkbox">
							<label><input type="checkbox" name="displayToOthers" ${ profile.data.displayToOthers ? 'checked' : ''}/><spring:message code="label.user.option.show.facebook"/></label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" name="allowPostingActivities" ${ profile.data.allowPostingActivities ? 'checked' : ''}/><spring:message code="label.user.option.post.facebook"/></label>
						</div>
					</c:if>
				</fieldset>
			</form>
		</div>
	</div>
</div>