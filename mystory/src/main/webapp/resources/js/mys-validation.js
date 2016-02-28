MYS.validation = (function (w, doc, $, undefined){

	var opts = {
		loginFormId				: "#form-signin",
		registerFormId			: "#form-signup",
		resetPasswordFormId		: "#form-reset-password",
		updatePasswordFormId	: "#form-update-password",
		storyCreateEditFormId	: "#story_telling_form",
		newCommentFormId		: "#commentform",
		messageFormId			: "#mailbox_send_message_form",
		messageReplyFormId		: "#mailbox_message_detail_form",
			
		defaultValidation		: {
			ignore: "",
			submitHandler: function(form) {
				w.onbeforeunload = null;
				form.submit();
			}
		}
	};
	
	var init = function(){
		$.validator.addMethod("mysRemote", function(value, element, param)
		{
			var urlToCall = param.urlToCall();
			  
			  if (!urlToCall) {
			      return "dependency-mismatch";
			  }
			  
			var isSuccess = false;
			
		    $.ajax(
		    {
		        type: "GET",
		        url: urlToCall,
		        dataType: "json",
		        data: {},
		        async: false,
		        success: function(data)
		        {
		            isSuccess = data;
		        },
		        error: function(xhr, textStatus, errorThrown)
		        {
		        	alert(MYS.i18n.messages.errors.general + "\nError " + xhr.status + ": "+xhr.statusText);
		            isSuccess = false;
		        }
		    });
		    
		    return isSuccess;

		}, '');
		
		$.validator.addMethod("checkUserExists", function(value, element, param)
		{
			var urlToCall = param.urlToCall();
			  
			  if (!urlToCall) {
			      return "dependency-mismatch";
			  }
			  
			var isSuccess = false;
			
		    $.ajax(
		    {
		        type: "GET",
		        url: urlToCall,
		        dataType: "json",
		        data: {},
		        async: false,
		        success: function(data)
		        {
		            isSuccess = !data;
		        },
		        error: function(xhr, textStatus, errorThrown)
		        {
					alert(MYS.i18n.messages.errors.general + "\nError " + xhr.status + ": "+xhr.statusText);
		            isSuccess = false;
		        }
		    });
		    
		    return isSuccess;

		}, MYS.i18n.messages.errors.register.username.remote);
		
		$.validator.addMethod("checkUniqueEmail", function(value, element, param)
		{
			var urlToCall = param.urlToCall();
			  
			  if (!urlToCall) {
			      return "dependency-mismatch";
			  }
			  
			var isSuccess = false;
			
		    $.ajax(
		    {
		        type: "GET",
		        url: urlToCall,
		        dataType: "json",
		        data: {},
		        async: false,
		        success: function(data)
		        {
		            isSuccess = data;
		        },
		        error: function(xhr, textStatus, errorThrown)
		        {
		        	alert(MYS.i18n.messages.errors.general + "\nError " + xhr.status + ": "+xhr.statusText);
		            isSuccess = false;
		        }
		    });
		    
		    return isSuccess;

		}, MYS.i18n.messages.errors.register.username.remote);

		// LOGIN FORM
		if($(opts.loginFormId).length){
			$(opts.loginFormId).validate( $.extend(opts.defaultValidation, {
				rules: {
					"j_username" : "required",
					"j_password" : "required"
				},
				messages: {
					"j_username" : MYS.i18n.messages.errors.login.username.required,
					"j_password" : MYS.i18n.messages.errors.login.password.required
				}
			}));
		}
		
		// REGISTER FORM
		if($(opts.registerFormId).length){
			$(opts.registerFormId).validate($.extend(opts.defaultValidation, {
				rules: {
					"username" : {
						required : true,
						rangelength: [4,13],
						mysRemote: {
							urlToCall 	: function(){
								return "/user/check-unique/" + $("#username").val();
							}
						}
					},
					"email" : {
						required : true,
						email: true,
						mysRemote : {
							urlToCall	: function(){
								return "/user/check-unique-email/" + $("#email").val() + "/";
							}
						}
					},
					"password" : {
						required : true,
						minlength : 6
					}
				},
				messages: {
					"username" : {
						required 		: MYS.i18n.messages.errors.register.username.required,
						rangelength		: MYS.i18n.messages.errors.register.username.rangelength, 
						mysRemote		: MYS.i18n.messages.errors.register.username.remote 
					},
					"email" : {
						required		: MYS.i18n.messages.errors.register.email.required,
						email			: MYS.i18n.messages.errors.register.email.format,
						mysRemote		: MYS.i18n.messages.errors.register.email.remote
					},
					"password" : {
						required		: MYS.i18n.messages.errors.register.password.required,
						minlength		: MYS.i18n.messages.errors.register.password.minlength
					}
				}
			}));
		}
		
		// RESET PASSWORD FORM
		if($(opts.resetPasswordFormId).length){
			$(opts.resetPasswordFormId).validate($.extend(opts.defaultValidation, {
				rules: {
					"email" : {
						required : true,
						email: true
					}
				},
				messages: {
					"email" : {
						required		: MYS.i18n.messages.errors.register.email.required,
						email			: MYS.i18n.messages.errors.register.email.format
					}
				}
			}));
		}
		
		// UPDATE PASSWORD FORM
		if($(opts.updatePasswordFormId).length){
			$(opts.updatePasswordFormId).validate($.extend(opts.defaultValidation, {
				rules: {
					"oldPassword" : {
						required : true
					},
					"newPassword" : {
						required : true,
						minlength : 6
					}
				},
				messages: {
					"oldPassword" : {
						required		: MYS.i18n.messages.errors.updatePassword.oldRequired
					},
					"newPassword" : {
						required		: MYS.i18n.messages.errors.updatePassword.newRequired,
						minlength		: MYS.i18n.messages.errors.register.password.minlength
					}
				}
			}));
		}
		
		// STORY CREATE EDIT FORM
		if($(opts.storyCreateEditFormId).length){
			$(opts.storyCreateEditFormId).validate($.extend(opts.defaultValidation, {
				rules: {
					"content" : {
						required : {
							depends: function(){
								var field = $(opts.storyCreateEditFormId).find("[name='content']").first();
								$(field).val( $.trim($(field).data('sceditor').val()) );
								return true;
							}
						}
					},
					"title" : {
						required : true,
						maxlength : 200
					}
				},
				messages: {
					"content" : {
						required		: MYS.i18n.messages.errors.telling.content.required
					},
					"title" : {
						required		: MYS.i18n.messages.errors.telling.title.required,
						maxlength 		: MYS.i18n.messages.errors.telling.title.maxlength
					}
				},
				submitHandler: function(form) {
					if($(MYS.upload.getFeaturedUploadBar()).hasClass("hide") || confirm(MYS.i18n.messages.errors.upload.inProgress)){
						$("#story_telling_submit").mask(MYS.i18n.messages.labels.telling.submitting);
						w.onbeforeunload = null;
						form.submit();
					}
				}
			}));
		}
		
		// COMMENT CREATE FORM
		if($(opts.newCommentFormId).length){
			$(opts.newCommentFormId).validate($.extend(opts.defaultValidation, {
				rules: {
					"content" : {
						required : {
							depends: function(){
								var field = $(opts.newCommentFormId).find("[name='content']").first();
								$(field).val( $.trim($(field).data('sceditor').val()) );
								return true;
							}
						}
					}
				},
				messages: {
					"content" : {
						required		: MYS.i18n.messages.errors.telling.content.required
					}
				}
			}));
		}
		
		$('body').bind('msgComposeValidate', function(){
			validatePrivateMessage();
		});
	};
	
	// Validate Edit Comment
	var validateFormComment = function(id){
		$(id).validate($.extend(opts.defaultValidation, {
			rules: {
				"content" : {
					required : {
						depends: function(){
							var field = $(id).find("[name='content']").first();
							$(field).val( $.trim($(field).data('sceditor').val()) );
							return true;
						}
					}
				}
			},
			messages: {
				"content" : {
					required		: MYS.i18n.messages.errors.telling.content.required
				}
			}
		}));
	};
	
	var validatePrivateMessage = function(){
		var formId = "#noexists"; //non-existence form
		if($(opts.messageReplyFormId).length) formId = opts.messageReplyFormId;
		else if($(opts.messageFormId).length) formId = opts.messageFormId;
		
		$(formId).validate($.extend(opts.defaultValidation, {
			rules: {
				"to" : {
					required : true,
					checkUserExists : {
						urlToCall 	: function(){
							return "/user/check-unique/" + $("input[name='to']").val();
						}
					}
				},
				"title" : {
					required : true,
					maxlength: 200
				},
				"content" : {
					required : {
						depends: function(){
							var field = $(formId).find("[name='content']").first();
							$(field).val( $.trim($(field).data('sceditor').val()) );
							return true;
						}
					}
				}
			},
			messages: {
				"to" : {
					required 		: MYS.i18n.messages.errors.telling.recipient.required,
					checkUserExists	: MYS.i18n.messages.errors.telling.recipient.notexist
				},
				"title" : {
					required 		: MYS.i18n.messages.errors.telling.title.required,
					maxlength 		: MYS.i18n.messages.errors.telling.title.maxlength
				},
				"content" : {
					required		: MYS.i18n.messages.errors.telling.content.required
				}
			}
		}));
	};
	
	// return public functions here.
	return {
		init: init,
		validateFormComment : validateFormComment
	};
	
}(window, document, jQuery));