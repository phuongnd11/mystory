MYS.betaInvitation = (function (w, doc, $, undefined){
	
	var opts = {
		emailInviteBtn 		: "#email_invite_btn",
		emailInviteForm		: "#email_invitation",
		emailInviteStatus	: "#email_invite_status",
		emailInviteInput	: "#email_invite_textfield",
		emailInviteUrl		: "/user/invite",
		
		generateCodeBtn		: "#btn_gen_code",
		codesTable			: "#table_codes",
		generateCodeUrl		: "/user/invitecode/generate",
		
		codeStatusStrs		: {
			NEW				: "Mới tạo",
			SENT			: "Đã gửi",
			TAKEN			: "Đã đăng ký"
		}
	};
	var init = function(){
		$(doc).on('click', opts.emailInviteBtn, function(e){
			e.preventDefault();
			$.ajax({
				url		: opts.emailInviteUrl,
				type 	: "post",
				data	: JSON.stringify({
					email : $(opts.emailInviteInput).val()
				}),
				contentType: "application/json",
				
				success : function(result){
					$(opts.emailInviteStatus).html(result.message);
					$(opts.emailInviteInput).val("");
				},
				
				error	: function(result){
					$(opts.emailInviteInput).val(result.message);
				}
			});
		});
		
		$(doc).on('click', opts.generateCodeBtn, function(e){
			e.preventDefault();
			
			$.ajax({
				url		: opts.generateCodeUrl,
				type 	: "post",
				contentType: "application/json",
				
				success : function(result){
					var row = doc.createElement("tr");
					if(result.data.status == "SENT"){
						$(row).addClass("warning");
					}
					else if(result.data.status == "TAKEN"){
						$(row).addClass("success");
					}
					$(row).append("<td>"+result.data.code+"</td>");
					$(row).append("<td>"+result.data.invited+"</td>");
					$(row).append("<td>"+result.data.generatedTime+"</td>");
					$(row).append("<td>"+opts.codeStatusStrs[result.data.status]+"</td>");
					
					$(opts.codesTable + " tbody").append(row);
				},
				
				error	: function(result){
					console.log(result.message);
				}
			});
		});
	};
	
	// return public functions here.
	return {
		init: init
	};
	
}(window, document, jQuery));