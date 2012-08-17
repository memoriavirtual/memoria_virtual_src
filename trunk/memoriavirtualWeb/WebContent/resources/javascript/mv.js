/**
 * Cadastrar bem patrimonial javascript
 * 
 */
function BackgroundNegro(backgrond) {
	var classe = this;
	this.backgrond = backgrond;

	this.inicio = function() {
		classe.backgrond.setAttribute("class", "cadastroNovoAutor");
		classe.backgrond
				.addEventListener("click", classe.backgroundSair, false);
		var iframe = document.createElement("iframe");
		var div = document.createElement("div");
		iframe.setAttribute("src",
				"/memoriavirtual/restrito/cadastrarautor.jsf");
		iframe.setAttribute("class", "frameCadastrarAutor");
		// iframe.setAttribute("scrolling", "no");

		// div.setAttribute("scrolling-y", "no");
		div.appendChild(iframe);
		classe.backgrond.appendChild(div);
	};
	this.backgroundSair = function() {
		var a = classe.backgrond.childNodes[0];
		classe.backgrond.removeChild(a);
		classe.backgrond.setAttribute("class", "cadastroNovoAutorDesativado");
	};

}

mostrarfundoNegro = (function() {
	var backgrond = document.getElementById("cadastroNovoAutor");

	var backgroundNegro = new BackgroundNegro(backgrond);

	backgroundNegro.inicio();

});

iniciaTabelas = (function(idtabela) {
	var tabela = document.getElementById(idtabela);
	var tabelas = tabela.getElementsByTagName("table");

	for ( var i = 0; i < tabelas.length; i++) {
		if (i != pass) {
			tabelas[i].setAttribute("style", "display: none;");

		} else
			tabelas[i].removeAttribute("style");
	}
});

var pass;

mostrarTableCorreta = (function(index, idtabela) {
	var j = new Number(index.getAttribute("id").split(":")[2]);
	pass = j;
	var tabela = document.getElementById(idtabela);
	var tabelas = tabela.getElementsByTagName("table");

	for ( var i = 0; i < tabelas.length; i++) {
		if (i != j) {
			tabelas[i].setAttribute("style", "display: none;");
			
		} else
			tabelas[i].removeAttribute("style");
	}
});
