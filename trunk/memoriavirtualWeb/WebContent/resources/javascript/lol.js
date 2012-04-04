$(document).ready(function(){
	$("#sugestoesId").hide();
	$("#input").keyup(function(){
		if(this.value != ""){
			//$("#sugestoesId").show();
		}
		else{
			$("#sugestoesId").hide();
		}
	});
	$("#sugestao").click(function(){
		alert(this.size());
	});
});

