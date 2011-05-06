$(document).ready(function () {
	$('#Abas li a').live("click", function(){
		var linkhref = $(this).attr("href");
		$("#CaixaCorpo").load(linkhref);
		return false;
	});
});