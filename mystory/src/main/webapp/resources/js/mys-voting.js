MYS.voting = (function (w, doc, $, undefined){
	var opts = {
		requestUrl 			: "/vote",
		voteUpBtnClass 		: ".vote_up_btn",
		voteDownBtnClass 	: ".vote_down_btn",
		votePanelClass 		: ".vote_panel",
		voteCountClass		: ".vote_count",
		votedUpClass 		: "voted_up",
		votedDownClass 		: "voted_down",
		
		upvoteStr			: "UPVOTE",
		downvoteStr			: "DOWNVOTE",
		unupvoteStr			: "UN_UPVOTE",
		undownvoteStr		: "UN_DOWNVOTE"
	};
	
	var init = function(){
		$(doc).on('click', opts.voteUpBtnClass, function(e){
			e.preventDefault();
			
			if($(e.target).parent(opts.votePanelClass).hasClass(opts.votedUpClass)){
				requestVote($(e.target).attr("data-vid"), $(e.target).attr("data-vtype").toUpperCase(), opts.unupvoteStr, $(e.target).parent(opts.votePanelClass));
			}
			else{
				requestVote($(e.target).attr("data-vid"), $(e.target).attr("data-vtype").toUpperCase(), opts.upvoteStr, $(e.target).parent(opts.votePanelClass));
			}
		});
		
		$(doc).on('click', opts.voteDownBtnClass, function(e){
			e.preventDefault();
			
			if($(e.target).parent(opts.votePanelClass).hasClass(opts.votedDownClass)){
				requestVote($(e.target).attr("data-vid"), $(e.target).attr("data-vtype").toUpperCase(), opts.undownvoteStr, $(e.target).parent(opts.votePanelClass));
			}
			else{
				requestVote($(e.target).attr("data-vid"), $(e.target).attr("data-vtype").toUpperCase(), opts.downvoteStr, $(e.target).parent(opts.votePanelClass));
			}
		});
	};
	
	var requestVote = function(id, type, action, parent){
		
		$.ajax({
			url		: opts.requestUrl,
			type 	: "post",
			data	: {
				candidateId	: id,
				type		: action + "_" + type
			},
			
			success : function(result){
				if(result.status == MYS.conf.ajaxStatus.success){
					switch(action){
						case opts.upvoteStr:
							if($(parent).hasClass(opts.votedDownClass)){
								$(parent).removeClass(opts.votedDownClass);
								$(parent).find(opts.voteCountClass).html(parseInt($(parent).find(opts.voteCountClass).html()) + 2);
							}
							else{
								$(parent).find(opts.voteCountClass).html(parseInt($(parent).find(opts.voteCountClass).html()) + 1);
							}
							
							$(parent).addClass(opts.votedUpClass);
							
							break;
							
						case opts.unupvoteStr:
							$(parent).removeClass(opts.votedUpClass);
							$(parent).find(opts.voteCountClass).html(parseInt($(parent).find(opts.voteCountClass).html()) - 1);
							break;
							
						case opts.downvoteStr:
							if($(parent).hasClass(opts.votedUpClass)){
								$(parent).removeClass(opts.votedUpClass);
								$(parent).find(opts.voteCountClass).html(parseInt($(parent).find(opts.voteCountClass).html()) - 2);
							}
							else{
								$(parent).find(opts.voteCountClass).html(parseInt($(parent).find(opts.voteCountClass).html()) - 1);
							}
							
							$(parent).addClass(opts.votedDownClass);
							break;
							
						case opts.undownvoteStr:
							$(parent).removeClass(opts.votedDownClass);
							$(parent).find(opts.voteCountClass).html(parseInt($(parent).find(opts.voteCountClass).html()) + 1);
							break;
							
						default:
							console.log("Error while voting.");
							break;
					}
				}
				else{
					alert(result.message);
				}
			},
			error : function(xhr, status){
				alert(MYS.i18n.messages.errors.general + "\nError " + xhr.status + ": "+xhr.statusText);
			}
		});
	};
	
	// return public functions here.
	return {
		init: init
	};
	
}(window, document, jQuery));