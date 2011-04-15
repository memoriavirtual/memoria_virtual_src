$(document).ready(function() {	


  //Pega todos os elementos da lista #MenuAbas
  $('#Abas > li').click(function(){
        
    //Remove a classe dos elementos
    $('#Abas > li').removeClass('selecionado');
    
    //Reatribui uma nova classe
    $(this).addClass('selecionado');
 
    
  }).mouseover(function() {

    //Adiciona e remove a classe
    $(this).addClass('mousesobre');
    $(this).removeClass('mousefora');   
    
  }).mouseout(function() {
    
    //Adiciona e remove a classe
    $(this).addClass('mousefora');
    $(this).removeClass('mousesobre');    
    
  });
	
});
