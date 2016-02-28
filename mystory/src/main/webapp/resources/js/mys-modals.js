MYS.modals = (function (w, doc, $, undefined){
	
	var opts = {
		notif_modal 					: "#notification_overlay",
		notif_toggle					: "#notification_toggle",
		notif_count						: "#notification_count",
		
		original_story_modal 			: "#original_story_overlay",
		original_story_toggle			: "#btn_choose_original",
		original_story_accept 			: "#accept_original",
		original_story_cancel 			: "#cancel_original",
		original_story_field_name 		: "originalStoryId",
		original_story_choosen_display 	: "#original_story_name",
		original_story_radio_id_prefix 	: "#radio_original_story_",
		original_story_revert 			: "#current_original_story",
			
		helper_modal					: "#helper_overlay",
		helper_label					: "#helper_modal_label",
		helper_message					: "#helper_modal_message"
	};
	
	var init = function(){
		
		// Notification Modal
		if($(opts.notif_modal).length > 0){
			
			$(opts.notif_modal).modal({'show' : false});
			
			$(doc).on('click', opts.notif_toggle, function(e){
				e.preventDefault();
				$(opts.notif_modal).modal('show');
				
				if(!$(opts.notif_toggle).hasClass("nonew")){
					$.ajax({
						url : "/user/lasviewed-update",
						type : "get",
						success : function(){
							$(opts.notif_toggle).addClass("nonew");
							$(notif_count).html("0");
						}
					});
				}
			});
		}
		
		
		// Story Create Edit - Original Story Modal
		if($(opts.notif_modal).length > 0){
			
			$(opts.original_story_modal).modal({'show' : false});
			
			$(doc).on('click', opts.original_story_toggle, function(e){
				e.preventDefault();
				
				$(opts.original_story_modal).modal('toggle');
			});
			
			$(doc).on('click', 'input[name="' + opts.original_story_field_name + '"]', function(){
				$(opts.original_story_choosen_display).html($('input[name="' + opts.original_story_field_name + '"]:checked').attr('title'));
			});
			
			$(doc).on('click', opts.original_story_accept, function(e){
				e.preventDefault();
				
				$(opts.original_story_revert).val($('input[name="' + opts.original_story_field_name + '"]:checked').val());
				
				$(opts.original_story_modal).modal('hide');
				
			});
			
			$(doc).on('click', opts.original_story_cancel, function(e){
				e.preventDefault();
				
				$(opts.original_story_radio_id_prefix + $(opts.original_story_revert).val()).click();
				$(opts.original_story_choosen_display).html($('input[name="' + opts.original_story_field_name + '"]:checked').attr('title'));
				
				$(opts.original_story_modal).modal('hide');
			});
		}
		
		if(MYS.core.getUrlParam('register') && $(opts.helper_modal).length > 0){
			$(opts.helper_label).html(MYS.i18n.messages.helpers.register.label);
			$(opts.helper_message).html(MYS.i18n.messages.helpers.register.message);
			$(opts.helper_modal).modal({'show' : true});
		}
		if(MYS.core.getUrlParam('activated') && $(opts.helper_modal).length > 0){
			$(opts.helper_label).html(MYS.i18n.messages.helpers.activate.label);
			$(opts.helper_message).html(MYS.i18n.messages.helpers.activate.message);
			$(opts.helper_modal).modal({'show' : true});
		}
		if(MYS.core.getUrlParam('reset') && $(opts.helper_modal).length > 0){
			$(opts.helper_label).html(MYS.i18n.messages.helpers.reset.label);
			$(opts.helper_message).html(MYS.i18n.messages.helpers.reset.message);
			$(opts.helper_modal).modal({'show' : true});
		}
	};
	
	return {
		init: init,
	};
}(window, document, jQuery));