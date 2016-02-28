MYS.upload = (function (w, doc, $, undefined){
	
	var opts = {
		avatarUploadId		: "#fileupload_avatar",
		featuredImgUploadId	: "#fileupload_featured",
		imageUploadId		: "#fileupload_story_comment",
		progressBarSuffix	: "_progress_bar",
		progressClass		: ".progress"
	};
	
	var init = function(){
		
		// AVATAR UPLOAD
		if($(opts.avatarUploadId).length){
			$(opts.avatarUploadId).fileupload({
		        dataType: 'json',
		        add : function(e, data){
		        	if (data.autoUpload || (data.autoUpload !== false &&
		                    $(this).fileupload('option', 'autoUpload'))) {
		                data.process().done(function () {
		                	$(opts.avatarUploadId + opts.progressBarSuffix).removeClass("hide");
		    	        	$(opts.avatarUploadId + opts.progressBarSuffix+" span").html("0%");
		                    data.submit();
		                });
		            }
		        },
		        progressall: function (e, data) {
		            var progress = parseInt(data.loaded / data.total * 100, 10);
		            $(opts.avatarUploadId + opts.progressBarSuffix + " " + opts.progressClass).css('width',progress + '%');
		            $(opts.avatarUploadId + opts.progressBarSuffix+" span").html(progress+"%");
		        },
		        done: function (e, result) {
		        	if(result._response.result.status == MYS.conf.ajaxStatus.success){
			        	$(opts.avatarUploadId + opts.progressBarSuffix).addClass("hide");
						$("#avatar").attr("src", result._response.result.data.large);
						$("#sidebar_avatar").attr("src", result._response.result.data.small);
		        	}
		        	else{
		        		alert(result._response.result.message);
		        		$(opts.avatarUploadId + opts.progressBarSuffix).addClass("hide");
		        	}
		        }
		    });
		}
		
		// FEATURED IMAGE UPLOAD
		if($(opts.featuredImgUploadId).length){
			$(opts.featuredImgUploadId).fileupload({
		        dataType: 'json',
		        add : function(e, data){
		        	if (data.autoUpload || (data.autoUpload !== false &&
		                    $(this).fileupload('option', 'autoUpload'))) {
		                data.process().done(function () {
		                	$(opts.featuredImgUploadId + opts.progressBarSuffix).removeClass("hide");
		    	        	$(opts.featuredImgUploadId + opts.progressBarSuffix+" span").html("0%");
		                    data.submit();
		                });
		            }
		        },
		        progressall: function (e, data) {
		            var progress = parseInt(data.loaded / data.total * 100, 10);
		            $(opts.featuredImgUploadId + opts.progressBarSuffix + " " + opts.progressClass).css('width',progress + '%');
		            $(opts.featuredImgUploadId + opts.progressBarSuffix+" span").html(progress+"%");
		        },
		        done: function (e, result) {
		        	if(result._response.result.status == MYS.conf.ajaxStatus.success){
			        	$(opts.featuredImgUploadId + opts.progressBarSuffix).addClass("hide");
						$("#featured_preview").html("<img src='" + result._response.result.data.large  + "' />");
						$("#featured_img").val(result._response.result.data.name);
		        	}
		        	else{
		        		alert(result._response.result.message);
		        		$(opts.featuredImgUploadId + opts.progressBarSuffix).addClass("hide");
		        	}
		        }
		    });
			
			$("body").on('click', "#btn_remove_featured", function(e){
				e.preventDefault();
				$("#featured_preview").html("");
				$("#featured_img").val("");
			});
		}
		
		//CONTENT IMAGE UPLOAD
		/** 
		 * Because I am bad, my code is bad and the image uploader for contents are initialized in mys-editor.js
		 * Problem?
		 */
	};
	
	var getFeaturedUploadBar = function(){
		return opts.featuredImgUploadId + opts.progressBarSuffix;
	};
	
	// return public functions here.
	return {
		init					: init,
		getFeaturedUploadBar 	: getFeaturedUploadBar
	};
	
}(window, document, jQuery));