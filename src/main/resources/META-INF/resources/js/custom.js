$(document).ready(function(){
	$('#nonAjaxLoad').hide();
//	$('#islemListPanel_content').addClass('islemPanelContent');
	$('#islemListPanel_content').prepend("<img id='transparentBackgroundLogo' src='img/TC_Adalet_Bakanligi_logo.png'>" + $('#resim').html());
//	$('#resim').css('margin','auto');
});

$(window).bind('beforeunload', function() {
	$('#nonAjaxLoad').show();
});

function loadingKapat(){
	$('#nonAjaxLoad').hide();
}

var tiklananButon;
$(document).on('click', function(e) {
	if($(e.target).parent().prop("tagName") == "BUTTON" || ($(e.target).parent().prop("tagName") == "TR" && $(e.target).parent().closest('tbody').attr('id') != 'sdfBilgileriForm:manuelFisTable_data')) {
		$('#nonAjaxLoad').show();
		tiklananButon = $(e.target).parent();
	}
});

$(document).on("pfAjaxComplete", function(event, xhr, options) {
//	if(tiklananButon.attr('id') == options.source) {
//		$('#nonAjaxLoad').hide();
//	}
	$('#nonAjaxLoad').hide();
});

PrimeFaces.locales['tr'] = {
		closeText : 'kapat',
		prevText : 'geri',
		nextText : 'ileri',
		currentText : 'bugün',
		monthNames : [ 'Ocak', 'Şubat', 'Mart', 'Nisan', 'Mayıs',
		'Haziran', 'Temmuz', 'Ağustos', 'Eylül', 'Ekim', 'Kasım',
		'Aralık' ],
		monthNamesShort : [ 'Oca', 'Şub', 'Mar', 'Nis', 'May', 'Haz',
		'Tem', 'Ağu', 'Eyl', 'Eki', 'Kas', 'Ara' ],
		dayNames : [ 'Pazar', 'Pazartesi', 'Salı', 'Çarşamba', 'Perşembe',
		'Cuma', 'Cumartesi' ],
		dayNamesShort : [ 'Pz', 'Pt', 'Sa', 'Ça', 'Pe', 'Cu', 'Ct' ],
		dayNamesMin : [ 'Pz', 'Pt', 'Sa', 'Ça', 'Pe', 'Cu', 'Ct' ],
		weekHeader : 'Hf',
		firstDay : 1,
		isRTL : false,
		showMonthAfterYear : false,
		yearSuffix : '',
		timeOnlyTitle : 'Zaman Seçiniz',
		timeText : 'Zaman',
		hourText : 'Saat',
		minuteText : 'Dakika',
		secondText : 'Saniye',
		ampm : false,
		month : 'Ay',
		week : 'Hafta',
		day : 'Gün',
		allDayText : 'Tüm Gün'
		};

$(document).on('click', '.ui-inputmask', function(e) {
//	$('.ui-inputmask').focus();
	$(this).focus();
});

$(document).on('keyup', '.kimlikClass', function(e) {
	var text = $(this).val();
	if(text[text.length-1] == '0' || text[text.length-1] == '1' || text[text.length-1] == '2' || text[text.length-1] == '3' || text[text.length-1] == '4' || 
			text[text.length-1] == '5' || text[text.length-1] == '6' || text[text.length-1] == '7' || text[text.length-1] == '8' || 
			text[text.length-1] == '9' || text[text.length-1] == '%') {
		
	}
	else {
		text = text.substring(0, text.length - 1);
		$(this).val(text);
		console.log(text[text.length-1]);
	}
});

$(document).on('click', function(e) {
	var formId = $(e.target).closest('form');
	if(formId != undefined) {
		formId = formId.attr('id');
		var sorgulaBtn = $('#'+formId+'\\:temizle');
		if(sorgulaBtn != undefined) {
//			$('#'+formId).find('input').val("");
			
		}
		
		
		
		
//		var sorgulaBtn = $('#'+formId+'\\:sorgula');
//		var baslangic = $('[placeholder="Başlangıç Tarihi"]');
//		var bitis = $('[placeholder="Bitiş Tarihi"]');
//		if(sorgulaBtn != undefined && baslangic != undefined && bitis != undefined) {
//			baslangic.val("");
//			bitis.val("");
////			tarihKontrol(baslangic.val(), bitis.val(), message);
//			
//		}
	}
});

function tarihKontrol(baslangic, bitis, message) {
	console.log(baslangic);
	console.log(bitis);
	PF('#messages').renderMessage({summary:message, severity: "error"});
}


//$('.ui-autocomplete-dropdown.ui-button').focusin(function(e) {
//	console.log("tab basildi");
//	$('.ui-autocomplete-dropdown').tabs();
//});

$(document).on('keydown', '.ui-autocomplete-input.ui-autocomplete-dd-input', function(event) {
//	var e  = $.Event('keydown');
//	e.which= 9;
//	e.keyCode = 9;
//	console.log(e);
//	console.log(this);
//	$('.ui-autocomplete-dropdown.ui-button').trigger(e);
//	$(this).trigger(e);
//	
//	document.dispatchEvent(new KeyboardEvent("keypress", { key: 'Tab' })); 
	
	if (event.keyCode === 9) {
		console.log(event)
//		event.preventDefault();
//		event.trigger;
//		var x = event.originalEvent; //new KeyboardEvent('keypress', {key : 'Tab'});
//		console.log(x);
//		$(this).trigger(event);
//		$(this).trigger(x);
		
//		var e  = $.Event('keydown');
//		e.which= 9;
//		e.keyCode = 9;
//		$('.ui-autocomplete-dropdown.ui-button').trigger(e);
//		$(this).trigger(event);
//		console.log($('.ui-autocomplete-input.ui-autocomplete-dd-input'));
//		console.log($('.ui-autocomplete-input.ui-autocomplete-dd-input')[$('.ui-autocomplete-input.ui-autocomplete-dd-input').index(this)]);
//		 $('.ui-autocomplete-input.ui-autocomplete-dd-input')[$('.ui-autocomplete-input.ui-autocomplete-dd-input').index(this)+1].focus();
		 
	}
});

function simulateKeyPress() {
	jQuery.event.trigger({ type : 'keydown', keyCode : 9 });
}

function ebysIcerikPdfGoster(id,base64String) {
	$('#'+id).attr('src','data:application/pdf;base64, '+base64String);
}

function ebysLoginIframe(sonuc) {
//	$('#ebysIframe').attr('src',sonuc);
//	$('#ebysIframe').attr("src","https://sistem.benimsurum.com");

	
//	var el = document.getElementById('ebysIframe');
//	el.src = sonuc;
	console.log("param sonuc = ");
	console.log(sonuc);
//	window.frames['ebysIframe'].location = sonuc;
	
	window.open(sonuc,'sso_sekmesi'); //ayri pencerede aciliyor
	
	return;
	
	if ($('#iframe').length) {
		$('#iframe').remove();
	}
	//src="' + sonuc + '"
	$('#iframeDiv').append('<object id="iframe" type="text/html"  width="100%" height="600px" data="' + sonuc + '" ></object>');
}
//	console.log($('#ebysIframe').attr('src'));