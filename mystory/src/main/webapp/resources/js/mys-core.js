var MYS = {};

MYS.core = (function (w, doc, $, undefined){

	var init = function(){
		
		//fix embeded video staying on top problem
		$('iframe').each(function() {
			var url = $(this).attr("src");
			if ($(this).attr("src").indexOf("?") > 0) {
				$(this).attr({
					"src" : url + "&wmode=transparent",
					"wmode" : "Opaque"
				});	
			}
			else {
				$(this).attr({
				  "src" : url + "?wmode=transparent",
				  "wmode" : "Opaque"
				});
			}
		});
		
		if($("body").hasClass("authenticated")) MYS.isAuthenticated = true;
		
	};
	
	var serializeObject = function(form){
	    var o = {};
	    var a = $(form).serializeArray();
	    $.each(a, function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = [o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};
	
	var getUrlParam = function(sParam){
		var sPageURL = window.location.search.substring(1);
  	    var sURLVariables = sPageURL.split('&');
  	    for (var i = 0; i < sURLVariables.length; i++)
  	    {
  	        var sParameterName = sURLVariables[i].split('=');
  	        if (sParameterName[0] == sParam)
  	        {
  	            return sParameterName[1];
  	        }
  	    }
	};
	
	return {
		init: init,
		serializeObject : serializeObject,
		getUrlParam : getUrlParam
	};
}(window, document, jQuery));