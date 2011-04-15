$(document).ready(function () {
	$('#Abas').each(function (index, element) {
		element.click(function () {
			$(" #CaixaCorpo").load($(this).href);
			return false;
		});
		alert('lol');
	});
});