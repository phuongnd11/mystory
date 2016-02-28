$(document).ready(function(){
	
	if(!MYS) MYS = {};
	
	MYS.conf = {
		ajaxStatus : {
			success	: "SUCCESS",
			failure : "FAILURE"
		}	
	};
	
	// Core functions (actually it's just misc. functions)
	if(MYS.core) MYS.core.init();
	
	// Popup Modals
	if(MYS.modals) MYS.modals.init();
	
	// Editor
	if(MYS.editor) MYS.editor.init();
	
	// Beta Invitaion
	if(MYS.validation) MYS.validation.init();
	
	// Voting
	if(MYS.voting) MYS.voting.init();

	// Home Endless Scrolling
	if(MYS.endlessScrolling) MYS.endlessScrolling.init();
	
	// Beta Invitaion
	if(MYS.betaInvitation) MYS.betaInvitation.init();
	
	// MailBox
	if(MYS.userprofile) MYS.userprofile.init();
	
	// jQuery uploads
	if(MYS.upload) MYS.upload.init();
	
	// Category customization
	if(MYS.categoryList) MYS.categoryList.init();
	
	// Story detail features
	if(MYS.story) MYS.story.init();
});