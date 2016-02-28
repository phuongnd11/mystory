<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/templates" id="commentEditTemplate">
<form id="edit_comment_{{id}}" class="nopopup_validate">
	<div class="form-group">
		<textarea class="mys_input full_width_textarea" name="content" data-comment-id="{{id}}" rows="7" id="mys-input-comment-{{id}}">{{{content}}}</textarea>
	</div>
    <div class="form-group">
		<input type="button" class="btn btn_important submit_edit_comment" id="submit_comment_{{id}}" data-comment-id="{{id}}" value="<spring:message code="label.story.send.comment" />" />
		<input type="button" class="btn submit_cancel_edit_comment" id="cancel_comment_{{id}}" data-comment-id="{{id}}" value="<spring:message code="label.btn.cancel" />" />
	</div>
</form>
</script>