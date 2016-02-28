MYS.userprofile = (function (w, doc, $, Mustache, undefined){
	
	var opts = {
		profileContainerClass	: ".profile_container",
		mailTemplateId			: "#mailTemplate",
		msgDetailTemplateId 	: "#messageDetailTemplate",
		sendMsgTemplateId		: "#messageComposeTemplate",
		
		//user profile tabs
		userFollowBtn			: "#profile_follow_btn",
		userUnfollowBtn			: "#profile_unfollow_btn",
		userViewNameId			: "#profile_view_username",
		userUpdatePassForm		: "#form-update-password",
		userUpdatePassId		: "#profile_update_password",
		userBackPassBtn			: "#profile_back_password",
		
		userProfileUrl			: "/account/profile",
		userFollowUrl			: "/account/follow",
		userUnfollowUrl			: "/account/unfollow",
		userMailBoxUrl			: "/account/mailbox",
		userUpdatePassUrl		: "/account/updatePassword",
		updatePasswordFormId	: "#form-update-password",
		
		//mailbox
		mailboxInboxBtn			: "#mailbox_inbox_btn",
		mailboxSentBtn			: "#mailbox_sent_btn",
		mailboxSendBtn			: "#mailbox_send_btn",
		mailboxReplyBtn			: "#mailbox_reply_btn",
		mailboxComposeBtn		: "#mailbox_compose_btn",
		mailboxRepToggleBtn 	: "#mailbox_reptoggle_btn",
		mailboxTableId			: "#table_messages",
		mailboxContainerId		: "#messagebox",
		mailboxSendMesgForm 	: "#mailbox_send_message_form",
		mailboxMesgDetailForm 	: "#mailbox_message_detail_form",
		mailboxTabListClass		: ".message_tab",
		
		mailboxInboxUrl			: "/account/inbox",
		mailboxSentUrl			: "/account/sent",		
		mailboxSendMesgUrl		: "/account/sendMessage",
		mailboxReplyMesgUrl		: "/account/replyMessage",
		mailboxViewInboxMsgUrl	: "/account/inbox/detail",
		mailboxViewSentMsgUrl   : "/account/sent/detail",			
		
		//profile view
		profileComposeMsg		: "#profile_compose_msg_btn",
			
		loginPageUrl   			: "/login",	
		
		//follow user
		followClass				: ".btn_follow",
		followUrl				: "/account/follow/",
		unfollowUrl				: "/account/unfollow/",
		isFollowingClass		: "is_followed"
		
	};
	
	var init = function(){
		
		//Follow user
		$(doc).on('click', opts.followClass, function(e){
			if($(e.target).hasClass(opts.isFollowingClass)){
				$.ajax({
					url : opts.unfollowUrl + $(e.target).attr("data-username"),
					type : "post",
					success : function(result) {
						if (result.status == MYS.conf.ajaxStatus.success) {
							$(opts.followClass+"[data-username='"+$(e.target).attr("data-username")+"']").each(function(){
								$(this).removeClass(opts.isFollowingClass);
								
								if($(this).children().length){
									$(this).children().first().html(parseInt($(this).children().first().html()) - 1);
								}
								else{
									$(this).html(parseInt($(this).html()) - 1);
								}
							});
							
						} else {
							alert(result.message);
						}
						
					},
					error : function(xhr, status){
						alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
					}
				});
			}
			else{
				$.ajax({
					url : opts.followUrl + $(e.target).attr("data-username"),
					type : "post",
					success : function(result) {
						if (result.status == MYS.conf.ajaxStatus.success) {
							
							
							
							$(opts.followClass+"[data-username='"+$(e.target).attr("data-username")+"']").each(function(){
								$(this).addClass(opts.isFollowingClass);
								
								if($(this).children().length){
									$(this).children().first().html(parseInt($(this).children().first().html()) + 1);
								}
								else{
									$(this).html(parseInt($(this).html()) + 1);
								}
							});
						} else {
							alert(result.message);
						}
						
					},
					error : function(xhr, status){
						alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
					}
				});
			}
		});
		
		if($(opts.profileContainerClass).length > 0){
			// actions in profile
			$(doc).on('click', opts.userFollowBtn, function(e){
				e.preventDefault();
				toggleFollow(opts.userFollowUrl);				
			});
			
			$(doc).on('click', opts.userUnfollowBtn, function(e){
				e.preventDefault();
				toggleFollow(opts.userUnfollowUrl);				
			});			
			
			$(doc).on('click', opts.userUpdatePassId, function(e){
				e.preventDefault();
				if($(opts.updatePasswordFormId).valid()){
					$.ajax({
						type : "POST",
						contentType: 'application/json; charset=UTF-8',
						url : opts.userUpdatePassUrl,				
						data : JSON.stringify(MYS.core.serializeObject(opts.userUpdatePassForm)),
						success : function(result) {
							if (result.status == MYS.conf.ajaxStatus.success) {						
								w.location = opts.userProfileUrl;
							} else {
								console.log(result.message);
								if (result.message != ""){
									$("#change_password_errors").addClass("form-group").html("<label class='error'>"+result.message+"</label>");
								}
								
								else w.location = opts.loginPageUrl;
							}
							
						},
						error : function(xhr, status){
							alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
						}
					});
				}
			});			
			
			
			$(doc).on('click', opts.userBackPassBtn, function(e){
				e.preventDefault();
				w.location = opts.userProfileUrl;				
			});
			
			// Inbox
			$(doc).on('click', opts.mailboxInboxBtn, function(e){
				e.preventDefault();
				loadTableMessage(opts.mailboxInboxUrl, opts.mailboxInboxBtn);
			});
			
			// Sent box
			$(doc).on('click', opts.mailboxSentBtn, function(e){
				e.preventDefault();
				loadTableMessage(opts.mailboxSentUrl, opts.mailboxSentBtn);
			});
			
			// Reply
			$(doc).on('click', opts.mailboxReplyBtn, function(e){
				e.preventDefault();
				if($(opts.mailboxMesgDetailForm).valid()){
					$.ajax({
						type : "POST",
						contentType: 'application/json; charset=UTF-8',
						url : opts.mailboxReplyMesgUrl,
						data : JSON.stringify(MYS.core.serializeObject(opts.mailboxMesgDetailForm)),
						success : function(result) {
							if (result.status == MYS.conf.ajaxStatus.success) {
								$(opts.mailboxSentBtn).click();
							} else {
								console.log(result.message);
								$("#mailbox_error_message").html(result.message);
							}
							
						},
						error : function(xhr, status){
							alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
						}
					});
				}
			});	
			
			// Send
			$(doc).on('click', opts.mailboxSendBtn, function(e){
				e.preventDefault();
				if($(opts.mailboxSendMesgForm).valid()){
					$.ajax({
						type : "POST",
						contentType: 'application/json; charset=UTF-8',
						url : opts.mailboxSendMesgUrl,
						data : JSON.stringify(MYS.core.serializeObject(opts.mailboxSendMesgForm)),
						success : function(result) {
							if (result.status == MYS.conf.ajaxStatus.success) {
								$(opts.mailboxSentBtn).click();
							} else {
								console.log(result.message);
								$("#mailbox_error_message").html(result.message);
							}
							
						},
						error : function(xhr, status){
							alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
						}
					});
				}
			});			
			
			// Message detail
			$("body").on('click', opts.mailboxTableId + " tbody tr:not(.no_message)", function(event){
				event.preventDefault();
				var fromTo = $(event.target).parents("tr").find('td').first().html();
				var submittedTime = $(event.target).parents("tr").find('td').last().html();
				var msgDetailUrl = "";
				if ($(opts.mailboxInboxBtn).parent("li").hasClass("selected")){
					msgDetailUrl =  opts.mailboxViewInboxMsgUrl + "?from=" + fromTo + "&submittedTime=" + submittedTime;
				} else { 
					msgDetailUrl =  opts.mailboxViewSentMsgUrl + "?to=" + fromTo + "&submittedTime=" + submittedTime;
				}
				$.ajax({
					url		: msgDetailUrl,
					type 	: "GET",
					contentType: "application/json",
					
					success : function(result){
						if(result.status == MYS.conf.ajaxStatus.success){
							$(opts.mailboxContainerId).html(Mustache.render($(opts.msgDetailTemplateId).html(), result.data.data));
						}
					},
					error : function(xhr, status){
						alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
					}
				});
			});
			
			// Show send message
			$("body").on('click', opts.mailboxComposeBtn, function(event){
				event.preventDefault();
				showSendMessage();
			});
			
			// Toggle Reply box
			$("body").on('click', opts.mailboxRepToggleBtn, function(event){
				event.preventDefault();
				$(event.target).remove();
				$("#reply_container").css("display","block");
				$('body').trigger('msgReplyCreation');
			});
			
			// mailbox initialization
			if($(opts.mailboxContainerId).length){
				var msgTo = MYS.core.getUrlParam('to');
				if(msgTo){
					showSendMessage(decodeURIComponent(msgTo));
				}
				else{
					var where = MYS.core.getUrlParam('where');
					
					if (where != null && where == "out") 
						$(opts.mailboxSentBtn).click();
					else 
						$(opts.mailboxInboxBtn).click();
				}
			}
		}
	};
	
	var showSendMessage = function(msgTo){
		$(opts.mailboxContainerId).html(Mustache.render($(opts.sendMsgTemplateId).html(), {msgTo : msgTo}));
		$('body').trigger('msgComposeCreation');
	};
	
	var loadTableMessage = function(url, target){
		$.ajax({
			url		: url,
			type 	: "GET",
			contentType: "application/json",
			
			success : function(result){
				if(result.status == MYS.conf.ajaxStatus.success){
					$(opts.mailboxContainerId).html(Mustache.render($(opts.mailTemplateId).html(), result.data.data));
					$(opts.mailboxTabListClass).children("li").each(function(){
						$(this).removeClass("selected");
					});
					$(target).parent().addClass("selected");
				}
			},
			error : function(xhr, status){
				alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
			}
		});		
	};
	
	var toggleFollow = function(url){
		$.ajax({
			type : "POST",
			url : url + "/" + $(opts.userViewNameId).html(),
			success : function(result) {
				if (result.status == MYS.conf.ajaxStatus.success) {
					location.reload();
				} else {
					//TODO
					alert(result.message);						
				}					
			},
			error : function(xhr, status){
				alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
			}
		});			
	};	
	
	// return public functions here.
	return {
		init: init
	};
	
}(window, document, jQuery, Mustache));