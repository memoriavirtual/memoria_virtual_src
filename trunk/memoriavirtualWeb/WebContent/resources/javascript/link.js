$(document).ready(function () {
	$('#Abas ul li a').live("click", function(){
		var linkhref = $(this).attr("href");
		$("#CaixaCorpo").load(linkhref);
		return false;
	});
});