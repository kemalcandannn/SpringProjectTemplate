$(document).on('click', 'td', function(e) {
	e.preventDefault();
	e.stopPropagation();
	$('#nonAjaxLoad').hide();
});