$(document).ready(function() {
	$("#sugestoesId").hide();

	$("input").keyup(function() {
		if ($("#sugestoesId > tbody > tr > td:empty ").size() > 0) {
			$("div #sugestoes").hide();
		}
		else
			$("div #sugestoes").show();
	});

	
});

function opa() {
}
