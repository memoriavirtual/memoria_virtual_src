$(document).ready(function() {	

	$('.linkPrincipal').corner("top");
	//Pega todos os elementos da lista #MenuAbas
  	$('#Abas > ul > li').click(function(){
        
    	//Remove a classe dos elementos
    	$('#Abas > ul > li').removeClass('selecionado');
  
	    //Reatribui uma nova classe
    	$(this).addClass('selecionado');
		
	})
	$("#menuNavegacao li a.linkPrincipal").mouseover(function(){
		$("#menuNavegacao li a.close").fadeIn();
		$("#menuNavegacao li a.linkPrincipal").removeClass("ativo");
		$(this).addClass("ativo");
		$("#linkSecundarioRecipiente").animate({
			height: "39px"					   
		});
		$(".linkSecundario").hide();
		$(this).siblings(".linkSecundario").fadeIn();
	});	
  
  /*.mouseover(function() {

    //Adiciona e remove a classe
    $(this).addClass('mousesobre');
    $(this).removeClass('mousefora');   
    
  }).mouseout(function() {
    
    //Adiciona e remove a classe
    $(this).addClass('mousefora');
    $(this).removeClass('mousesobre');
    
  });
	*/
});
