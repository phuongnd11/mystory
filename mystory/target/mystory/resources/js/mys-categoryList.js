MYS.categoryList = (function (w, doc, $, undefined){
	
	var opts = {
		categoryListId 	: "#categories_list",
		allCategoryId	: "#all_categories",
		btnEditId		: "#btn_edit_categories",
		navRightId		: "#nav_right"
	};
	
	var init = function(){
		
		// NOTE: CURRENT IMPLEMENTATION DOESN'T USE AUTOCOMPLETE'S REMOTE SOURCE. IT WILL NEED REWORK WHEN USER CUSTOM CATEGORY FEATURE IS ADDED.
		if($(opts.categoryListId).length && MYS.isAuthenticated){
			
			$(opts.categoryListId).sortable({
				items						: "li:not([data-unsortable='true'])",
				placeholder					: "placeholder",
				forcePlaceholderSize		: true,
				appendTo					: document.body,
				start						: function(event, ui){
					$(opts.categoryListId + " a").on('click', function(e){e.preventDefault();});
				},
				stop						: function(event, ui){
					setTimeout(function(){$(opts.categoryListId + " a").off('click');}, 500);
				}
			});
			
			initDragging();
			
			// event trigger only work if event is bound after initialization
			$(opts.categoryListId).bind( "sortupdate", function( event, ui ) {
				if($(opts.categoryListId+" li").length > 7){
					$(opts.categoryListId +" li:last-child").animate({"opacity" : "0", "width": "0"}, 200, function(){
						$(this).remove();
						resetDragging();
						ajaxUpdateCat();
					});
				}
				else{
					ajaxUpdateCat();
				}
			});
			
			$("body").on('click', opts.btnEditId, function(e){
				e.preventDefault();
				if(!$(opts.allCategoryId).hasClass("shown")){
					$(opts.allCategoryId).fadeIn(100, function(){
						$(opts.allCategoryId).addClass("shown");					
					});
				}
			});
			
			$("body").on('click', ':not('+opts.allCategoryId+')', function(e){
				if($(opts.allCategoryId).hasClass("shown")){
					$(opts.allCategoryId).fadeOut(100, function(){
						$(opts.allCategoryId).removeClass("shown");					
					});
				}
			});
		}
	};
	
	var ajaxUpdateCat = function(){
		var array = new Array();
		$(opts.categoryListId + " li").each(function(){
			array.push($(this).attr("data-name").toUpperCase());
		});
		
		$.ajax({
			url : "/account/updateTagSetting",
			type : "post",
			dataType : "json",
			data : {categories : array},
			success : function(result){
				if(result.status == MYS.conf.ajaxStatus.failure){
					alert(result.message);
				}
			},
			error : function(xhr, status){
				alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
			}
		});
	};
	
	var initDragging = function(){
		$(opts.categoryListId).find("li").each(function(){
			$(opts.allCategoryId).find("li[data-name='"+$(this).attr("data-name") + "']").addClass("added");
		});
		
		$(opts.allCategoryId + " li").draggable({
			appendTo					: document.body,
			helper						: "clone",
			connectToSortable 			: opts.categoryListId,
			disabled					: true
		});
		
		$(opts.allCategoryId + " li:not(.added)").draggable( "enable" );
	};
	
	var resetDragging = function(){
		$(opts.allCategoryId +" li").removeClass("added");
		$(opts.categoryListId).find("li").each(function(){
			$(opts.allCategoryId).find("li[data-name='"+$(this).attr("data-name") + "']").addClass("added");
		});
		
		$(opts.allCategoryId + " li").draggable( "disable" );
		$(opts.allCategoryId + " li:not(.added)").draggable( "enable" );
	};
	
	// return public functions here.
	return {
		init: init
	};
	
}(window, document, jQuery));