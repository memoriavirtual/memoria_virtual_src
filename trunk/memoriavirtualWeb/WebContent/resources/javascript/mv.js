function mostrarErro(messagesId, alvoId) {

	var messages = $(messagesId);

	var erro = messages.hasClass('has-error');
	var warning = messages.hasClass('has-warning');
	var success = messages.hasClass('has-success');

	var texto = messages.text();

	if (texto !== '') {
		var alvo = $(alvoId);
		alvo.attr('title', texto);
		alvo.attr('data-original-title', texto);
		alvo.attr('data-toggle', 'tooltip');
		alvo.tooltip();
		alvo.removeClass('hidden');
		if (erro) {
			alvo.addClass('color-error');
			alvo.removeClass('color-warning');
			alvo.removeClass('color-success');
			var formGroup = alvo.closest('.form-group');
			formGroup.addClass('has-error');
			formGroup.removeClass('has-warning');
			formGroup.removeClass('has-success');
		} else if (warning) {
			alvo.addClass('color-warning');
			alvo.removeClass('color-error');
			alvo.removeClass('color-success');
			var formGroup = alvo.closest('.form-group');
			formGroup.addClass('has-warning');
			formGroup.removeClass('has-error');
			formGroup.removeClass('has-success');
		} else if (success) {
			alvo.addClass('color-success');
			alvo.removeClass('color-error');
			alvo.removeClass('color-warning');
			var formGroup = alvo.closest('.form-group');
			formGroup.addClass('has-success');
			formGroup.removeClass('has-error');
			formGroup.removeClass('has-warning');
		}
	} else {
		removerErro(alvoId);
	}

	messages = $(messagesId);
}

function removerErro(alvoId) {
	alvo = $(alvoId);
	alvo.addClass('hidden');
	var formGroup = alvo.closest('.form-group');
	formGroup.removeClass('has-error');
	formGroup.removeClass('has-warning');
	formGroup.removeClass('has-success');
}



/**
 * Cadastrar bem patrimonial javascript
 * 
 */
function PlayerImagen(player, nomeDoBean, lastIndex, thisIndex) {
	var classe = this;
	this.player = player;
	this.nomeDoBean = nomeDoBean;
	this.lastIndex = lastIndex;
	this.thisIndex = thisIndex;

	this.inicio = function() {
		classe.player.setAttribute("class", "cadastroNovoAutor");
		classe.player.setAttribute("scrolling-y", "no");
		{
			var xmlhttp = new XMLHttpRequest();

			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
					if (xmlhttp.responseText.match("image.*")) {
						var image = document.createElement("img");
						image.setAttribute("src",
								"/memoriavirtual/multimidia?bean="
										+ classe.nomeDoBean + "&indice="
										+ classe.thisIndex);
						image.setAttribute("class", "imageNoPlayer");
						image.addEventListener("click", function(e) {
							e.stopPropagation();
							return false;
						}, false);
						classe.player.appendChild(image);
					} else if (xmlhttp.responseText.match("video.*")) {
						var video = document.createElement("video");
						video.setAttribute("src",
								"/memoriavirtual/multimidia?bean="
										+ classe.nomeDoBean + "&indice="
										+ classe.thisIndex);
						video.setAttribute("class", "imageNoPlayer");
						video.setAttribute("type", xmlhttp.responseText);
						video.setAttribute("controls", true);
						classe.player.appendChild(video);
					} else if (xmlhttp.responseText.match("audio.*")) {
						var audio = document.createElement("video");
						audio.setAttribute("src",
								"/memoriavirtual/multimidia?bean="
										+ classe.nomeDoBean + "&indice="
										+ classe.thisIndex);
						audio.setAttribute("class", "imageNoPlayer");
						audio.setAttribute("type", xmlhttp.responseText);
						audio.setAttribute("controls", true);
						classe.player.appendChild(audio);
					} else if (xmlhttp.responseText.match("application/pdf")) {
						var embed = document.createElement("iframe");
						var esq = document.createElement("img");
						esq
								.setAttribute("src",
										"/memoriavirtual/javax.faces.resource/seta_esquerda.png.jsf?ln=imagens");
						esq.setAttribute("class", "setaRolarDir");
						var dir = document.createElement("img");
						dir
								.setAttribute("src",
										"/memoriavirtual/javax.faces.resource/seta_direita.png.jsf?ln=imagens");
						dir.setAttribute("class", "setaRolarEsq");
						embed.setAttribute("name", "plugin");
						embed.setAttribute("type", xmlhttp.responseText);
						embed.setAttribute("width", "80%");
						embed.setAttribute("height", "100%");
						embed.setAttribute("src",
								"/memoriavirtual/multimidia?bean="
										+ classe.nomeDoBean + "&indice="
										+ classe.thisIndex);
						// embed.setAttribute("class", "imageNoPlayer");
						classe.player.appendChild(esq);
						classe.player.appendChild(embed);
						classe.player.appendChild(dir);
					}
			};
			xmlhttp.open("GET", "/memoriavirtual/multimidia?bean="
					+ classe.nomeDoBean + "&indice=" + classe.thisIndex
					+ "&type=true", true);
			xmlhttp.send();
		}

	};
	this.rolar = function(e) {

	};
	this.playerSair = function(e) {
		try {
			classe.player.innerHTML = "";
			classe.player.setAttribute("class", "cadastroNovoAutorDesativado");
		} catch (err) {
			classe.player.setAttribute("class", "cadastroNovoAutorDesativado");
		}
		e.stopPropagation();
	};

}

mostrarPlayerImagen = (function(nome, fim, index) {

	var player = document.getElementById("cadastroNovoAutor");
	var nomeDoBean = new String(nome);
	var lastIndex = new Number(fim);
	var thisIndex = new Number(index);
	var playerImagen = new PlayerImagen(player, nomeDoBean, lastIndex,
			thisIndex);

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

	for (var i = 0; i < tabelas.length; i++) {
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

	for (var i = 0; i < tabelas.length; i++) {
		if (i != j) {
			tabelas[i].setAttribute("style", "display: none;");

		} else
			tabelas[i].removeAttribute("style");
	}
});

// vari�veis est�ticas utilizadas nas fun��es que acertam os campos que devem
// ser vistos
var listaDeDivs;
var tipoDobemfixo = null;

/**
 * organiza os campos que devem ser vistos no grupo descri��o
 * 
 */
organizarGrupoDescricao = (function(idfieldset) {
	var listDivs = document.getElementById(idfieldset).getElementsByTagName(
			"div");
	listaDeDivs = listDivs;
	if (tipoDobemfixo == null) {

		for (var i = 0; i < listDivs.length; i++) {
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

		for (var i = 0; i < listDivs.length; i++) {

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

entradaAutoSave = (function() {
	setInterval("testAutoSave()", 30000);
});

testAutoSave = (function() {
	var autosave = document.getElementById("autoSave:0");
	var butonAutosave = document.getElementById("botaoEscondidoAutoSave");
	if (autosave.checked) {
		butonAutosave.click();
	} else {

	}
});

/**
 * Fun��o chamada quando um novo tipo de bem e escolhido Ela reorganiza os
 * campos que devem ser vistos
 */
mostrarDescricao = (function() {

	var menu = document.getElementById("GeralInfo:tipodobem");

	// vari�vel est�tica que tem o tipo do bem
	tipoDobemfixo = menu.selectedIndex;

	// Acerta os campos vistos no grupo descri��o
	organizarGrupoDescricao("grupoDescricao");
	// Acerta os campos vistos no grupo Interven��o
	organizarGrupoDescricao("grupoIntervencao");
});

/**
 * Fun��o chamada quando um novo disponibilidade � escolhida Ela reorganiza os
 * campos que devem ser vistos
 */
mostrarDisponibilidade = (function(menu) {

	// var menu = document.getElementById("disponibilidade:disponibilidade");
	var tipodisponibilidade = new Number(menu.getAttribute("id").split(":")[2]);

	if (tipodisponibilidade == 0) {// acervo
		// mostrar condi��es de acesso
		var elem = document.getElementById("disponibilidade:acesso");
		elem.removeAttribute("style");

		// esconder data de retorno
		var elem1 = document.getElementById("disponibilidade:data");
		elem1.setAttribute("style", "display: none;");

		return;
	}
	if (tipodisponibilidade == 5) {

		// esconder data de retorno
		var elem1 = document.getElementById("disponibilidade:data");
		elem1.setAttribute("style", "display: none;");

		// esconder condi��es de acesso
		var elem = document.getElementById("disponibilidade:acesso");
		elem.setAttribute("style", "display: none;");
		return;
	}

	// mostrar data de retorno
	var elem = document.getElementById("disponibilidade:data");
	elem.removeAttribute("style");

	// esconder condi��es de acesso
	var elem1 = document.getElementById("disponibilidade:acesso");
	elem1.setAttribute("style", "display: none;");
});

inicioUnity = (function() {
	/**
	 * Integra��o com o Unity Beta
	 */

	inicioUnity = (function() {/*
								 * 
								 * function unityReady() { // Integrate with
								 * Unity! } var Unity =
								 * external.getUnityObject(1.0);
								 * 
								 * Unity.init({ name : "Memoria Virtual",
								 * iconUrl :
								 * "http?://localhost:8080?/memoriavirtual/icon.png",
								 * onInit : unityReady });
								 */
	});
});
