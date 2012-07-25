function ContainerExpansivo(container , botaoMenos , botaoMais , divBotao ,estado){
	var classe = this;
	this.container = container;
	this.botaoMenos = botaoMenos;
	this.botaoMais = botaoMais;
	this.divBotao = divBotao;
	this.estado = estado;
	this.botaoMaisAtivo;
	window.alert(classe.estado.getAttribute("value"));
	if ("plift" == classe.estado.getAttribute("value"))
		classe.botaoMaisAtivo = 0;
	else
		classe.botaoMaisAtivo = 1;
	
	
	this.inicio = function(){
		classe.botaoMais.addEventListener("mouseover", classe.hover, false);
		classe.botaoMais.addEventListener("mouseout", classe.hout, false);
		classe.botaoMenos.addEventListener("mouseover", classe.hover, false);
		classe.botaoMenos.addEventListener("mouseout", classe.hout, false);
		classe.botaoMais.addEventListener("click", classe.clickMais, false);
		classe.botaoMenos.addEventListener("click",classe.clickMenos, false);
		
		if(classe.botaoMaisAtivo == 0)
			classe.clickMenos();
		else
			classe.clickMais();
	};
	
	this.hover= function(e){
		if ( classe.botaoMaisAtivo == 1){
			classe.botaoMenos.setAttribute("class", "botaoMaisMenosOver");
		}else{
			classe.botaoMais.setAttribute("class", "botaoMaisMenosOver");
		}
		
		
	};
	
	this.hout= function(e){
		if ( classe.botaoMaisAtivo == 1){
			classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
		}else{
			classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		}
	};
	
	this.clickMais = function(e){
		classe.botaoMaisAtivo = 1;
		classe.botaoMais.setAttribute("class", "botaoOculto");
		classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
		classe.container.setAttribute("style", "display: inline-table;");
		classe.divBotao.setAttribute("class" , "containerExpansivoEscuro");
		classe.estado.setAttribute("value" , "ploft");
	};
	;
	this.clickMenos = function(e){
		classe.botaoMaisAtivo = 0;
		classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		classe.botaoMenos.setAttribute("class", "botaoOculto");
		classe.container.setAttribute("style", "display: none;");
		classe.divBotao.setAttribute("class" , "containerExpansivo");
		classe.estado.setAttribute("value" , "plift");
	};
}


iniciarComponenteContainerExpansivo = (function(idContainer, idMenos , idMais , idDivBotao ,idEstado) {
	var container = document.getElementById(idContainer);
	var botaoMenos = document.getElementById(idMenos);
	var botaoMais = document.getElementById(idMais);
	var divBotao = document.getElementById( idDivBotao);
	var estado = document.getElementById( idEstado);
	
	var containerExpansivo = new ContainerExpansivo(container , botaoMenos , botaoMais , divBotao , estado);
	
	containerExpansivo.inicio();
});