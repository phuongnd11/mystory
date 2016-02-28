/** MyStory Markdown editor functionalities.*/

MYS.editor = (function (w, doc, $, undefined){
	
	var opts = {
		editorName : {
			storyCreateEdit 		: "-story",
			commentNew				: "-comment-new",
			commentEdit				: "-comment-",
			messageCompose			: "-message",
			messageReply			: "-message-reply"
		},
		commentEditBtnClass		: ".action_edit_comment",
		commentEditSubmitClass	: ".submit_edit_comment",
		commentEditCancelClass	: ".submit_cancel_edit_comment",
		commentEditTemplateId	: "#commentEditTemplate",
		options					: {},
		emoticons: {
			dropdown: {
				':)': 'emoticons/smile.gif',
				';)': 'emoticons/wink.gif',
				':))': 'emoticons/laugh.gif',
				':D': 'emoticons/grin.gif',
				'xD': 'emoticons/xd.gif',
				'=))': 'emoticons/rofl.gif',
				':*': 'emoticons/kiss.gif',
				':O': 'emoticons/shocked.png',
				'o:-)': 'emoticons/innocent.gif',
				':">': 'emoticons/blush.gif',
				':|': 'emoticons/pouty.png',
				':(': 'emoticons/sad.png',
				':((': 'emoticons/cry.gif',
				'x(': 'emoticons/furious.gif'
			},
			more: {
				':angel:': 'emoticons/angel.png',
				':hapcry:': 'emoticons/happy_crying.gif',
				':p': 'emoticons/tongue.gif',
				':admire:': 'emoticons/admire.gif',
				':love:': 'emoticons/wub.gif',
				'<3': 'emoticons/heart.gif',
				':naughty:': 'emoticons/naughty.gif',
				':sweat:': 'emoticons/sweatdrop.gif',
				':/D': 'emoticons/tooth.gif',
				':clap:': 'emoticons/clap.gif',
				':nod:': 'emoticons/nod.gif',
				':-o<': 'emoticons/pray.gif',
				':>': 'emoticons/proud.gif',
				':-h': 'emoticons/bye.gif',
				':/': 'emoticons/duh.gif',
				':hae:': 'emoticons/hae.gif',
				'x-.': 'emoticons/pinch.gif',
				'(:|': 'emoticons/yawn.gif',
				':annoyed:': 'emoticons/annoyed.gif',
				'[-x': 'emoticons/lac.gif',
				'@-)': 'emoticons/hypo.gif',
				'S-(': 'emoticons/scared.gif',
				':-s': 'emoticons/worried.gif',
				':-&': 'emoticons/sick.gif',
				':-?': 'emoticons/think.gif',
				'8-X': 'emoticons/skull.gif',
				':v:': 'emoticons/v.gif',
				':devil:': 'emoticons/devil.gif',
				':finn:': 'emoticons/finn.gif',
				':ninja:': 'emoticons/ninja.gif',
				':cool:': 'emoticons/agent.gif',
				':alien:': 'emoticons/alien.png',
				':cop:': 'emoticons/cop.png',
				':devil:': 'emoticons/devil.png',
				':kenshin:': 'emoticons/kenshin.gif',
				':pirate:': 'emoticons/pirate.png',
				':-"': 'emoticons/whistle.gif',
				':n:': 'emoticons/thumbdown.gif',
				':y:': 'emoticons/thumbup.gif',
				':zzz:': 'emoticons/sleep.gif'
			},
			hidden: {
				':whistling:': 'emoticons/whistle.gif',
				':woot:': 'emoticons/w00t.png',
				':unsure:': 'emoticons/hae.gif',
				':silly:': 'emoticons/silly.png',
				':sick:': 'emoticons/sick.gif',
				':pouty:': 'emoticons/pouty.png',
				':happy:': 'emoticons/happy.png',
				':getlost:': 'emoticons/getlost.png',
				':cheerful:': 'emoticons/tooth.gif',
				':blush:': 'emoticons/blush.gif',
				':ermm:': 'emoticons/think.gif',
				':cwy:': 'emoticons/scared.gif',
				':angry:': 'emoticons/furious.gif',
				':sun:': 'emoticons/sun.gif',
				':metal:': 'emoticons/metal.gif',
				':hug:': 'emoticons/hug.gif',
				':sad:': 'emoticons/sad.png',
				':wallbash:': 'emoticons/wallbash.gif',
				':blue:': 'emoticons/blue.gif',
				':smile:': 'emoticons/smile.gif',
				':rofl:': 'emoticons/rofl.gif',
				':sideways:': 'emoticons/rofl.gif',
				';-)': 'emoticons/wink.gif',
				':wink:': 'emoticons/wink.gif',
				':lol:': 'emoticons/laugh.gif',
				':-D': 'emoticons/grin.gif',
				':-*': 'emoticons/kiss.gif',
				':kissing:': 'emoticons/kiss.gif',
				':-O': 'emoticons/shocked.png',
				':-o': 'emoticons/shocked.png',
				':o': 'emoticons/shocked.png',
				'O:-)': 'emoticons/innocent.gif',
				'>_<': 'emoticons/gah.gif',
				'X(': 'emoticons/furious.gif',
				'x-(': 'emoticons/furious.gif',
				'X-(': 'emoticons/furious.gif',
				'x_0': 'emoticons/dribble.gif',
				'x_O': 'emoticons/dribble.gif',
				':dizzy:': 'emoticons/dribble.gif',
				'o_0': 'emoticons/shocking.gif',
				'o_O': 'emoticons/shocking.gif',
				':-H': 'emoticons/bye.gif',
				':bye:': 'emoticons/bye.gif',
				':wave:': 'emoticons/bye.gif',
				':clover:': 'emoticons/clover.gif',
				'X/': 'emoticons/disgust.gif',
				'x-/': 'emoticons/disgust.gif',
				'X-/': 'emoticons/disgust.gif',
				':-/': 'emoticons/duh.gif',
				':happycry:': 'emoticons/happy_crying.gif',
				':heart:': 'emoticons/heart.gif',
				'[-X': 'emoticons/lac.gif',
				':iykwim:': 'emoticons/naughty.gif',
				':blink:': 'emoticons/naughty.gif',
				':correct:': 'emoticons/nod.gif',
				':->': 'emoticons/proud.gif',
				':-O<': 'emoticons/pray.gif',
				':yeah:': 'emoticons/yeah.gif',
				':yeah:': 'emoticons/yeah.gif',
				':sleep:': 'emoticons/sleep.gif',
				':sleeping:': 'emoticons/sleep.gif',
				':-S': 'emoticons/worried.gif',
				's-(': 'emoticons/scared.gif',
				':wub:': 'emoticons/wub.gif',
				'X-.': 'emoticons/pinch.gif',
				':pinch:': 'emoticons/pinch.gif',
				':P': 'emoticons/tongue.gif',
				':-p': 'emoticons/tongue.gif',
				':-P': 'emoticons/tongue.gif',
				':no:': 'emoticons/thumbdown.gif',
				':yes:': 'emoticons/thumbup.gif',
				':skull:': 'emoticons/skull.gif',
				':win:': 'emoticons/v.gif',
				':victory:': 'emoticons/v.gif',
				'8-x': 'emoticons/skull.gif'
			}
		}
	};
	
	var bindNewEditor = function(editorName, large){
		$(editorName).sceditor({ 
			plugins : "bbcode",
			style: "/resources/css/libs/jquery.sceditor.default.css",
			emoticonsRoot : "/resources/imgs/",
			width: "100%",
			resizeWidth : false,
			enablePasteFiltering: true,
			emoticons : opts.emoticons,
			emoticonsCompat : true,
			height: 400,
			autoExpand : true,
			locale : MYS.i18n.messages.locale,
			toolbar : "bold,italic,underline,strike|left,center,right|size,color,removeformat|quote,code|emoticon,upimage,image,youtube,link,unlink|maximize,source"
		});
	};
	
	var bindNewShortEditor = function(editorName, large){
		
		$(editorName).sceditor({ 
			plugins : "bbcode",
			style: "/resources/css/libs/jquery.sceditor.default.css",
			emoticonsRoot : "/resources/imgs/",
			width: "100%",
			resizeWidth : false,
			enablePasteFiltering : true,
			emoticons : opts.emoticons,
			emoticonsCompat : true,
			height: 200,
			autoExpand : true,
			locale : MYS.i18n.messages.locale,
			toolbar : "bold,italic,underline,strike|left,center,right|size,color,removeformat|quote,code|emoticon,upimage,image,youtube,link,unlink|maximize,source"
		});
	};
	
	var init = function(){
		opts.options = {
			strings: MYS.i18n.messages.editorStrings
		};
		
		
		$.sceditorBBCodePlugin.bbcode.remove('table')
	    .remove('tr')
	    .remove('td')
	    .remove('th')
	    .remove('ul')
	    .remove('li')
	    .remove('ol')
	    .remove('font')
	    .remove('email')
	    .remove('date')
	    .remove('time')
	    .remove('ltr')
	    .remove('rtl')
	    .remove('horizontalrule');
		
		$.sceditor.command.set("upimage", {
		    exec: function(caller) {
		    	
		        // Store the editor instance so it can be used
		        // in the click handler
		        var editor   = this,
		            $content = $("<div />");
		        
		        $content.append("<div class='progress_bar editor_progress hide' id='fileupload_story_comment_progress_bar'>" +
				"<div class='progress_container'><div class='progress'></div></div><span class='progress_percent'></span></div>" );
		        
		        $("<input type='file' id='fileupload_story_comment' class='button' accept='image/*' name='files[]' data-url='/image/upload/content' />")
	            .fileupload({
			        dataType: 'json',
			        add : function(e, data){
			        	if (data.autoUpload || (data.autoUpload !== false && $(this).fileupload('option', 'autoUpload'))) {
			                data.process().done(function () {
			                	$("#fileupload_story_comment_progress_bar").removeClass("hide");
			    	        	$("#fileupload_story_comment_progress_bar span").html("0%");
			                    data.submit();
			                });
			            }
			        },
			        progressall: function (e, data) {
			            var progress = parseInt(data.loaded / data.total * 100, 10);
			            $("#fileupload_story_comment_progress_bar .progress").css('width',progress + '%');
			            $("#fileupload_story_comment_progress_bar span").html(progress+"%");
			        },
			        done: function (e, result) {
			        	if(result._response.result.status == MYS.conf.ajaxStatus.success){
			        		editor.insert("[img]" + result._response.result.data.full + "[/img]");
			                editor.closeDropDown(true);

			                e.preventDefault();
			        	}
			        	else{
			        		alert(result._response.result.message);
			        		editor.closeDropDown(true);
			        		e.preventDefault();
			        	}
			        }
			    })
	            .prependTo($content);
		 
		        editor.createDropDown(caller, "header-picker", $content);
		    },
		    tooltip: MYS.i18n.messages.editorStrings.upimageTooltip
		});
		
		$.sceditor.plugins.bbcode.bbcode.set("size", {
		    format: function($element, content) {
		        var size = $element.css('font-size').replace('px', '');
		        return '[size=' + size + ']' + content + '[/size]';
		    },
		    html: function(token, attrs, content) {
		        return '<span style="font-size: ' + attrs.defaultattr + 'px">' + content + '</span>';
		    }
		});
		
		window.onload = function(){
			if($("#mys-input" + opts.editorName.storyCreateEdit).length){
				bindNewEditor("#mys-input" + opts.editorName.storyCreateEdit, true);
				
			}
			if($("#mys-input" + opts.editorName.commentNew).length){
				bindNewShortEditor("#mys-input" + opts.editorName.commentNew);
				
				$(doc).on('click', '.comment_reply', function(e){
					$("#mys-input" + opts.editorName.commentNew).sceditor('instance').insert("<blockquote>"+$("#comment-"+$(e.target).attr("data-comment-id") + " .comment_content").html()+"</blockquote>", null, false);
				});				
			}
			if($("#mys-input" + opts.editorName.messageCompose).length){
				bindNewShortEditor("#mys-input" + opts.editorName.messageCompose);
			}
		};
		
		// Check fields value
		w.onbeforeunload = function(){
			var result = false;
			$(".mys_input").each(function(){
				if($.trim($(this).data('sceditor').val()) != ""){
					result = true;
				}
			});
			
			$(".progress_bar").each(function(){
				if(!$(this).hasClass("hide")){
					result = true;
				}
			});
			
			if(result){
				return MYS.i18n.messages.warnings.onleave;
			}
		};
		
		// COMMENT EDIT
		$("body").on('click', opts.commentEditBtnClass, function(e){
			e.preventDefault();
			
			$(e.target).css({
				"pointer-events" : "none",
				"cursor" : "default"
			});
			var commentId = $(e.target).attr("data-comment-id");
			
			$.ajax({
				url : "/comment/editComment/" + commentId,
				type : "get",
				dataType: "json",
				success : function(result){
					if (result.status == MYS.conf.ajaxStatus.success) {
						MYS.editor.original_comment[commentId] = $("#comment-" + commentId + " .comment_content").html();
						$("#comment-" + commentId + " .comment_content").html( Mustache.render( $(opts.commentEditTemplateId).html(), result.data ));
						bindNewShortEditor("#mys-input" + opts.editorName.commentEdit + commentId);
						MYS.validation.validateFormComment("#edit_comment_"+commentId);
						
						$("#comment-" + commentId + " .footer_actions").css("display", "none");
					}
					else{
						alert(result.message);
					}
				},
				error : function(xhr, status){
					alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
				}
			});
		});

		$("body").on('click', opts.commentEditSubmitClass, function(e){
			e.preventDefault();
			var commentId = $(e.target).attr("data-comment-id");
			if($("#edit_comment_"+commentId).valid()){
				$("#comment-" + commentId + " .comment_content").mask(MYS.i18n.messages.labels.telling.submitting);
				$.ajax({
					url : "/comment/edit",
					type : "post",
					dataType: "json",
					data : {
						id : commentId,
						content : $("#mys-input-comment-" + commentId).val()
					},
					success : function(result){
						if (result.status == MYS.conf.ajaxStatus.success) {
							$("#comment-" + commentId + " .comment_content").html(result.data.content);
						}
						else{
							alert(result.message);
						}
						
						$("#comment-" + commentId + " .comment_content").unmask();
						$("#comment-" + commentId + " " + opts.commentEditBtnClass).removeAttr("style");
						$("#comment-" + commentId + " .footer_actions").removeAttr("style");
					},
					error : function(xhr, status){
						alert(MYS.i18n.messages.errors.general +"\nError " + xhr.status + ": "+xhr.statusText);
						$("#comment-" + commentId + " .comment_content").unmask();
					}
				});
			}
		});
		
		$("body").on('click', opts.commentEditCancelClass, function(e){
			e.preventDefault();
			var commentId = $(e.target).attr("data-comment-id");
			
			$("#comment-" + commentId + " .comment_content").html(MYS.editor.original_comment[commentId]);
			$("#comment-" + commentId + " "+ opts.commentEditBtnClass).removeAttr("style");
			$("#comment-" + commentId + " .footer_actions").removeAttr("style");
		});
		
		$('body').bind('msgReplyCreation', function(){
			bindNewShortEditor("#mys-input" + opts.editorName.messageReply);
			$('body').trigger('msgComposeValidate');
		});
		
		$('body').bind('msgComposeCreation', function(){
			bindNewShortEditor("#mys-input" + opts.editorName.messageCompose);
			$('body').trigger('msgComposeValidate');
		});
	};
	
	// return public functions here.
	return {
		init: init,
		original_comment : {}
	};
	
}(window, document, jQuery));