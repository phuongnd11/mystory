$(function() {
	$.ajax({
		url : 'ajaxtest',
		success : function(data) {
			var result = jQuery.parseJSON(data);
			console.info(result);
			render(result);
		}
	});
	
    var submenu = $('.menu-navigation-basic');
    var mainmenu = $('.menu-navigation-round');
    
    mainmenu.slicknav();
    submenu.slicknav();
   
    // Mark the clicked item as selected

    submenu.on('click', 'a', function(){
        var a = $(this);

        a.siblings().removeClass('selected');
        a.addClass('selected');
    });
    
    mainmenu.on('click', 'a', function(){
        var a = $(this);

        a.siblings().removeClass('selected');
        a.addClass('selected');
    });
});

function render(data){
	var stories = data.home.stories;
	$("#list-content").empty();
    $.each(stories, function (key, value) {
    	$('#list-content').append('<div class="col-md-12 content-item" id="content-item">'
        +'<div class="row">'
        +'<div class="col-md-12">'
        +'    <div class="col-md-1 col-sm-2 col-xs-2"> '
        +'        <div class="vote-icon row text-center pd-top-10">'
        +'            <a href="#"><img src="/resources/imgs/vote.png" alt="vote" width="15" height="12"></a>'
        +'       </div>'
        +'       <div class="number-vote row text-center pd-top-5">' +value.voteCount
        +'      </div>'
        +'   </div>'
        +'   <div class="col-md-9 col-sm-8 col-xs-10">'
        +'       <div class="title">'+value.title+'<a href="#"></a></div>'
        +'       <div class="info">'
        +'          <ul>'
        +'              <li><a href="#">Thêm bình luận</a></li>'
        +'              <li><span class="dot">.</span><span class="time">'+value.shortTime+' trước bởi </span><span class="createdBy">'+value.author.name+'<a href="#"></a></span></li>'
        +'          </ul>'
        +'      </div>'
        +'  </div>'
        +'  <div class="col-md-2 col-sm-2 col-xs-2 pd-top-10 mobile-hidden">'
        +'      <div class="avatar col-xs-6 text-center">'
        +'          <img src="/resources/imgs/avartar/user.jpg" alt="user" width="30" height="30">'
        +'      </div>'
        +'      <div class="comment col-xs-6">'
        +'          <div class="row comment-icon text-center">'
        +'              <img src="/resources/imgs/comment_icon.png" alt="comment" width="15" height="15">'
        +'          </div>'
        +'          <div class="row number-comment text-center">' +value.commentCount
        +'          </div>'
        +'      </div>'
        +'  </div>'
        +'  </div>'
        +'</div>'
        +'</div>');
    });
}