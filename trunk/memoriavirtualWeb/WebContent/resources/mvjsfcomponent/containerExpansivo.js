function ContainerExpansivo(container , botaoMenos , botaoMais , divBotao){
	var classe = this;
	this.container = container;
	this.botaoMenos = botaoMenos;
	this.botaoMais = botaoMais;
	this.divBotao = divBotao;
	var botaoMaisAtivo = 0;
	
	
	this.inicio = function(){
		classe.botaoMais.addEventListener("mouseover", classe.hover, false);
		classe.botaoMais.addEventListener("mouseout", classe.hout, false);
		classe.botaoMenos.addEventListener("mouseover", classe.hover, false);
		classe.botaoMenos.addEventListener("mouseout", classe.hout, false);
		classe.botaoMais.addEventListener("click", classe.clickMais, false);
		classe.botaoMenos.addEventListener("click",classe.clickMenos, false);
		classe.clickMenos();
	};
	
	this.hover= function(e){
		if ( botaoMaisAtivo == 1){
			classe.botaoMenos.setAttribute("class", "botaoMaisMenosOver");
		}else{
			classe.botaoMais.setAttribute("class", "botaoMaisMenosOver");
		}
		
		
	};
	
	this.hout= function(e){
		if ( botaoMaisAtivo == 1){
			classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
		}else{
			classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		}
	};
	
	this.clickMais = function(e){
		botaoMaisAtivo = 1;
		classe.botaoMais.setAttribute("class", "botaoOculto");
		classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
		classe.container.setAttribute("style", "display: inline-table;");
		classe.divBotao.setAttribute("class" , "containerExpansivoEscuro");
	};
	;
	this.clickMenos = function(e){
		botaoMaisAtivo = 0;
		classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		classe.botaoMenos.setAttribute("class", "botaoOculto");
		classe.container.setAttribute("style", "display: none;");
		classe.divBotao.setAttribute("class" , "containerExpansivo");
	};
}


iniciarComponenteContainerExpansivo = (function(idContainer, idMenos , idMais , idDivBotao) {
	var container = document.getElementById(idContainer);
	var botaoMenos = document.getElementById(idMenos);
	var botaoMais = document.getElementById(idMais);
	var divBotao = document.getElementById( idDivBotao);
	
	
	var containerExpansivo = new ContainerExpansivo(container , botaoMenos , botaoMais , divBotao);
	
	containerExpansivo.inicio();
});