/**
 * Cadastrar bem patrimonial javascript
 * 
 */
function PlayerImagen(player  , nomeDoBean , lastIndex , thisIndex) {
	var classe = this;
	this.player = player;
	this.nomeDoBean = nomeDoBean;
	this.lastIndex = lastIndex;
	this.thisIndex = thisIndex;
	
	
	this.inicio = function() {
		classe.player.setAttribute("class", "cadastroNovoAutor");
		classe.player
				.addEventListener("click", classe.playerSair, false);
		classe.player.setAttribute("scrolling-y", "no");
		{
		var xmlhttp=new XMLHttpRequest();
		  
		
		xmlhttp.onreadystatechange = function(){
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
				if(xmlhttp.responseText.match("image.*")){
					var image = document.createElement("img");
					image.setAttribute("src",
					"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
					image.setAttribute("class", "imageNoPlayer");
					image.addEventListener("click", classe.rolar, false);
					classe.player.appendChild(image);
			  	}else if(xmlhttp.responseText.match("video.*")){
			  		var video = document.createElement("video");
			  		video.setAttribute("src",
					"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
			  		video.setAttribute("class", "imageNoPlayer");
			  		video.setAttribute("type", xmlhttp.responseText);
			  		video.setAttribute("controls", true);
			  		video.addEventListener("click", classe.rolar, false);
					classe.player.appendChild(video);
			  	}else if(xmlhttp.responseText.match("audio.*")){
			  		var audio = document.createElement("video");
			  		audio.setAttribute("src",
					"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
			  		audio.setAttribute("class", "imageNoPlayer");
			  		audio.setAttribute("type", xmlhttp.responseText);
			  		audio.setAttribute("controls", true);
			  		audio.addEventListener("click", classe.rolar, false);
					classe.player.appendChild(audio);
			  	}else if(xmlhttp.responseText.match("application/pdf")){
			  		var embed = document.createElement("iframe");
			  		embed.setAttribute("name", "plugin");
			  		embed.setAttribute("type", xmlhttp.responseText);
			  		embed.setAttribute("width", "80%");
			  		embed.setAttribute("height", "100%");
			  		embed.setAttribute("src",
					"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
			  		//embed.setAttribute("class", "imageNoPlayer");
			  		embed.addEventListener("click", classe.rolar, false);
					classe.player.appendChild(embed);
			  	}
			};
		xmlhttp.open("GET","/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex + "&type=true",true);
		xmlhttp.send();
		}
		
		
		
	};
	this.rolar = function(e) {
		
		e.stopPropagation();
		
		
		if( (window.outerHeight/10) * 7  < e.clientY ){
			;
		}else{
			try{
				var a = classe.player.childNodes[0];
				classe.player.removeChild(a);
				
			}
			catch(err ){
					classe.player.setAttribute("class", "cadastroNovoAutorDesativado");
			}
		if((window.outerWidth/2) > e.clientX ){
			classe.thisIndex --;
			if(classe.thisIndex < 0 ){
				classe.thisIndex = classe.lastIndex;
			}
		}else{
			classe.thisIndex ++;
			if(classe.thisIndex > classe.lastIndex ){
				classe.thisIndex = 0;
			}
		}
		
		
		
		{
			var xmlhttp=new XMLHttpRequest();
			  
			
			xmlhttp.onreadystatechange = function(){
				if (xmlhttp.readyState==4 && xmlhttp.status==200)
				  	if(xmlhttp.responseText.match("image.*")){
						var image = document.createElement("img");
						image.setAttribute("src",
						"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
						image.setAttribute("class", "imageNoPlayer");
						image.addEventListener("click", classe.rolar, false);
						classe.player.appendChild(image);
				  	}else if(xmlhttp.responseText.match("video.*")){
				  		var video = document.createElement("video");
				  		video.setAttribute("src",
						"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
				  		video.setAttribute("class", "imageNoPlayer");
				  		video.setAttribute("type", xmlhttp.responseText);
				  		video.setAttribute("controls", true);
				  		video.addEventListener("click", classe.rolar, false);
						classe.player.appendChild(video);
				  	}else if(xmlhttp.responseText.match("audio.*")){
				  		var audio = document.createElement("video");
				  		audio.setAttribute("src",
						"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
				  		audio.setAttribute("class", "imageNoPlayer");
				  		audio.setAttribute("type", xmlhttp.responseText);
				  		audio.setAttribute("controls", true);
				  		audio.addEventListener("click", classe.rolar, false);
						classe.player.appendChild(audio);
				  	}else if(xmlhttp.responseText.match("application/pdf")){
				  		var embed = document.createElement("iframe");
				  		embed.setAttribute("type", xmlhttp.responseText);
				  		embed.setAttribute("name", "plugin");
				  		embed.setAttribute("width", "80%");
				  		embed.setAttribute("height", "100%");
				  		embed.setAttribute("src",
						"/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex);
				  		//embed.setAttribute("class", "imageNoPlayer");
				  		embed.addEventListener("click", classe.rolar, false);
						classe.player.appendChild(embed);
				  	}
				};
			xmlhttp.open("GET","/memoriavirtual/multimidia?bean=" + classe.nomeDoBean + "&indice=" + classe.thisIndex + "&type=true",true);
			xmlhttp.send();
			}
		
		}
	};
	this.playerSair = function(e) {
		try{
		var a = classe.player.childNodes[0];
		classe.player.removeChild(a);
		classe.player.setAttribute("class", "cadastroNovoAutorDesativado");
		}
		catch(err ){
			classe.player.setAttribute("class", "cadastroNovoAutorDesativado");
		}
		e.stopPropagation();
	};

}

mostrarPlayerImagen = (function(nome, fim , index) {
	var player = document.getElementById("cadastroNovoAutor");
	var nomeDoBean = new String  (nome) ;
	var lastIndex = new  Number ( fim );
	var thisIndex = new  Number ( index );
	//alert(nomeDoBean );
	//alert(lastIndex );
	var playerImagen = new PlayerImagen(player , nomeDoBean , lastIndex , thisIndex);

	playerImagen.inicio();

});



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


//variáveis estáticas utilizadas nas funções que acertam os campos que devem ser vistos
var listaDeDivs;
var tipoDobemfixo = null;



/**
 * organiza os campos que devem ser vistos no grupo descrição
 * 
 */
organizarGrupoDescricao = (function(idfieldset) {
	var listDivs = document.getElementById(idfieldset).getElementsByTagName(
			"div");
	listaDeDivs = listDivs;
	if (tipoDobemfixo == null) {

		for ( var i = 0; i < listDivs.length; i++) {
			listDivs[i].setAttribute("style", "display: none;");
		}
	} else {
		var strTipo = "outrosBens";
		switch (tipoDobemfixo) {

		case 5:
			strTipo = "arquitetonico";
			break;
		case 7:
			strTipo = "natural";
			break;
		case 1:
			strTipo = "arqueologico";
			break;
		}

		for ( var i = 0; i < listDivs.length; i++) {

			if (listDivs[i].getAttribute("class") != null) {
				if (listDivs[i].getAttribute("class") != strTipo) {

					listDivs[i].setAttribute("style", "display: none;");
				} else {
					listDivs[i].removeAttribute("style");
				}
			}
		}

	}
});

/**
 * Função chamada quando um novo tipo de bem e escolhido
 * Ela reorganiza os campos que devem ser vistos 
 */
mostrarDescricao = (function() {

	var menu = document.getElementById("GeralInfo:tipodobem");
	
	
	//variável estática que tem o tipo do bem 
	tipoDobemfixo = menu.selectedIndex;
	
	//Acerta os campos vistos no grupo descrição
	organizarGrupoDescricao("grupoDescricao");
	//Acerta os campos vistos no grupo Intervenção
	organizarGrupoDescricao("grupoIntervencao");
});

/**
 * Função chamada quando um novo disponibilidade  é  escolhida
 * Ela reorganiza os campos que devem ser vistos 
 */
mostrarDisponibilidade = (function(menu) {

	//var menu = document.getElementById("disponibilidade:disponibilidade");
	var tipodisponibilidade = new Number(menu.getAttribute("id").split(":")[2]);
	

	
	

	
	if(tipodisponibilidade ==  0){//acervo
		//mostrar condições de acesso
		var elem = document.getElementById("disponibilidade:acesso");
		elem.removeAttribute("style");
	
		//esconder data de retorno
		var elem1 = document.getElementById("disponibilidade:data");
		elem1.setAttribute("style", "display: none;");
		
		return;
	}
	if(tipodisponibilidade ==  5){
	
		//esconder data de retorno
		var elem1 = document.getElementById("disponibilidade:data");
		elem1.setAttribute("style", "display: none;");
		
		//esconder condições de acesso
		var elem = document.getElementById("disponibilidade:acesso");
		elem.setAttribute("style", "display: none;");
		return;	
	}

	//mostrar data de retorno
	var elem = document.getElementById("disponibilidade:data");
	elem.removeAttribute("style");
	
	//esconder condições de acesso
	var elem1 = document.getElementById("disponibilidade:acesso");
	elem1.setAttribute("style", "display: none;");
});
