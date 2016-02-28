MYS.endlessScrolling = (function (w, doc, $, Mustache, undefined){
	
	var opts = {
		listId			: "#story_list",
		pageNumId		: "#paged",
		loadingImgId	: "endless_loading",
		tempateId		: "#storyTemplate",
			
		pagePrefix 		: "/p/",
		loadingImg		: "ajax-loader.gif",
			
		recommendedId	: "#recommended_stories"
	};
	
	var paged = 2;
	var recommededOriginalTop = 486;
	var checkScrollRecommened = true;
	
	var init = function(){
		if($(opts.listId).length && $(opts.tempateId).length){
			if($(opts.pageNumId).val()) paged =  parseInt($(opts.pageNumId).val()) + 1;
			$(w).bind('scroll', loadPage);
		}
		
		if($(opts.recommendedId).length){
			$(w).bind('scroll', scrollRecommended);
		}
	};
	
	var scrollRecommended = function(){
		if(checkScrollRecommened){
			checkScrollRecommended = false;
			if($( window ).width() > 900 && $(w).scrollTop() > recommededOriginalTop){
				$(opts.recommendedId).css({"position" : "fixed", "top" : "10px" , "width": $(opts.recommendedId).css("width")});
			}
			else{
				$(opts.recommendedId).removeAttr("style");
			}
			setTimeout(function(){checkScrollRecommened = true;}, 100);
		}
	};
	
	var loadPage = function(){
		if($(w).scrollTop() + $(w).height() == $(doc).height()) {
			$(w).unbind('scroll');
			$(opts.listId).append('<div id="'+ opts.loadingImgId + '" class="loading_image"><div class="imgWrapper"><img src="/resources/imgs/'+ opts.loadingImg +'" /></div></div>');
			$("#"+opts.loadingImgId).fadeIn('200');
			
			var requestUrl = "http://" + w.location.hostname + ":" + w.location.port + w.location.pathname.split(opts.pagePrefix)[0] + opts.pagePrefix + paged;
			
			$.ajax({

				type: "POST",

				url: requestUrl,

				dataType: "json",

				success: function(result) {
					
					$("#"+opts.loadingImgId).fadeOut('50', function(){
					
						$("#"+opts.loadingImgId).remove();
						
						if(result.status == MYS.conf.ajaxStatus.success && result.data.stories.length){
							$(opts.listId).append( Mustache.render( $(opts.tempateId).html(), result.data ));
							$(w).bind('scroll',loadPage);
							paged++;
						}
						else if(result.status == MYS.conf.ajaxStatus.success && !result.data.stories.length){
							$(".loading_image").fadeOut('200', function(){
								$(".loading_image").remove();
							});
						}
						else{
							alert(result.message);
						}
					});
				},
				error : function(xhr, status){
					alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
					$(".loading_image").fadeOut('200', function(){
						$(".loading_image").remove();
					});
				}
			});
		}
	};
	
	// return public functions here.
	return {
		init: init
	};
	
}(window, document, jQuery, Mustache));