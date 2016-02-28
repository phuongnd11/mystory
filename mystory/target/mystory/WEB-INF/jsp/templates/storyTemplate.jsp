<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/templates" id="storyTemplate">
	{{#stories}}
		<li class="story_item">
			<%--<c:if test="${ story.voteCount lt -4 }">
				<div class="bad_story_warning">
					<spring:message code="message.story.bad.warning"/><a href="#" class="bad_story_view"><spring:message code="label.comment.bad.warning"/></a>
				</div>
			</c:if>--%>
			
			<div class="author_info">
				<div class="author_upper">
					<a href="<c:url value='/user/{{author.name}}' />" class="author_avatar_container">
						{{#author.smallAvatar}}
							<img width="14" height="14" class="user_avatar" src="{{author.smallAvatar }}" alt="{{author.name }}">
						{{/author.smallAvatar}}
					</a>
					<div class="author_name_container">
						<a class="author_username" href="<c:url value='/user/{{author.name}}' />">{{author.name}}</a>
						<label class="author_story_points" title="<spring:message code="label.user.points" arguments="{{author.name}},{{author.points}}"/>">{{author.points}}</label>
					</div>
				</div>
				<div class="author_lower">
					<a href="<c:url value='/story/{{id}}#comment_list_wrapper'/>" class="btn author_btn comment_count_btn"><span>{{commentCount}}</span></a>
					<span title="{{submittedDate}}" class="btn author_btn story_submit_time"><span>{{shortTime}}</span></span>
				</div>
			</div>
			<div class="vote_panel {{#votedUpByCurrentReader}} voted_up {{/votedUpByCurrentReader}} {{#votedDownByCurrentReader}} voted_down {{/votedDownByCurrentReader}}">
				<button class="vote_btn vote_up_btn" data-vtype="story" data-vid="{{id}}"></button>
				<span class="vote_count">{{voteCount}}</span>
				<button class="vote_btn vote_down_btn" data-vtype="story" data-vid="{{id}}"></button>
			</div>
			<div class="story_item_inner">
					{{#featuredImage}}
						<img class="featured_img" src="{{featuredImage}}" />
					{{/featuredImage}}
					{{^featuredImage}}
						<img class="featured_img" src="http://localhost:8080/image/39f2c17a30dc9dc26e2f71dc82198676_medium" />
					{{/featuredImage}}
				<div class="story_item_content">
					<h2 class="topic_name">
						<a href="<c:url value='/story/{{id}}'/>"><c:out value="{{title}}"/></a>
					</h2>
					<div class="excerpt">{{content}}</div>
				</div>
			</div>
		</li>
	{{/stories}}
</script>