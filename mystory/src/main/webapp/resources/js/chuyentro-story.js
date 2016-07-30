MYS.story = (function (w, doc, $, undefined){
	
	var opts = {
		allCommentId				: "#all_comment",
		badCommentShowBtnClass 		: ".bad_comment_view",
		badCommentWarningClass		: ".bad_comment_warning",
		badCommentcontainerClass	: "bad_comment",
		
		storiesListId				: "#story_list",
		badStoryShowBtnClass		: ".bad_story_view",
		badStorycontainerClass		: "bad_story",
		badStoryWarningClass		: ".bad_story_warning"
	};
	
	var init = function(){
		if($(opts.allCommentId).length > 0){
			$("body").on('click', opts.badCommentShowBtnClass, function(e){
				e.preventDefault();
				$(e.target).parents("." + opts.badCommentcontainerClass).first().removeClass(opts.badCommentcontainerClass).find(opts.badCommentWarningClass).remove();
			});
		}
		
		if($(opts.storiesListId).length > 0){
			$("body").on('click', opts.badStoryShowBtnClass, function(e){
				e.preventDefault();
				$(e.target).parents("." + opts.badStorycontainerClass).first().removeClass(opts.badStorycontainerClass).find(opts.badStoryWarningClass).remove();
			});
		}
	};
	
	// return public functions here.
	return {
		init: init
	};
	
}(window, document, jQuery));