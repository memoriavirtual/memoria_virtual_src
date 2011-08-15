/* Mascara para os formularios: 
 *  $("# id_do_campo_a_mascarar").mask("9999/999/9");
 *  
 *  Numeros 9 onde só entram números, letras a onde só entram letras 
 *  usa-se barras, parêntesis ou traços como separadores e ? para marcar preenchimento opcional
 *  
 *  Como opção um placeholder pode ser configurado
 *  
 *  jQuery(function($){
   		$("#product").mask("99/99/9999",{placeholder:" "});
	});
 * 
 *  Confira http://digitalbush.com/projects/masked-input-plugin/ para mais informações
 * */

jQuery(function($) {

	$("[id*='cep']").mask("(999)99999-999");
	$("[id*='telefone']").mask("(999)9999-9999");

});
