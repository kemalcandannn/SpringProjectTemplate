var fails = $('.fail');
		
if(fails.length > 0) {
	$('.showCaptcha').css('display','inline');
}
else {
	$('.showCaptcha').css('display','none');
}

$(document).ready(function() {
	var ssoParam = location.href.split('ebysSsoToken=')[1];
	if(ssoParam != undefined){
		$('#ssoTokenInput').val(ssoParam);
		$( "#loginForm" ).submit();
		return false;
	}
		
 $.ajaxSetup({
      cache: false
    });

    var timestamp = (new Date()).getTime();


    $("#captchaRef").click(function() {

        var timestamp = (new Date()).getTime();
        var newSrc = $("#captchaImage").attr("src").split("?");
     //  $('#captchaImage').attr('src', '').attr('src', 'Captcha.jpg');
        newSrc = newSrc[0] + "?" + timestamp;
        $("#captchaImage").attr("src", newSrc);
        $("#captchaImage").slideDown("fast");

     });
    
    $("#loginButton").click(function(e) {
    	$('#nonAjaxLoad').show();
    });
    
    $("#loginButton2").click(function() {
    	$('#nonAjaxLoad').show();
    	
    });
 });

