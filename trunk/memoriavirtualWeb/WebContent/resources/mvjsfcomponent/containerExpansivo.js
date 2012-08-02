function ContainerExpansivo(container, botaoMenos, botaoMais, divBotao, estado) {
	var classe = this;
	this.container = container;
	this.botaoMenos = botaoMenos;
	this.botaoMais = botaoMais;
	this.divBotao = divBotao;
	this.estado = estado;
	this.botaoMaisAtivo;
	// window.alert(classe.estado.getAttribute("value"));

	if ("aberto" == getCookie("estado"
			+ classe.container.getAttribute("id")))
		classe.botaoMaisAtivo = 1;
	else
		classe.botaoMaisAtivo = 0;

	this.inicio = function() {
		classe.botaoMais.addEventListener("mouseover", classe.hover, false);
		classe.botaoMais.addEventListener("mouseout", classe.hout, false);
		classe.botaoMenos.addEventListener("mouseover", classe.hover, false);
		classe.botaoMenos.addEventListener("mouseout", classe.hout, false);
		classe.botaoMais.addEventListener("click", classe.clickMais, false);
		classe.botaoMenos.addEventListener("click", classe.clickMenos, false);

		if (classe.botaoMaisAtivo == 0){
			classe.botaoMaisAtivo = 0;
		classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		classe.botaoMenos.setAttribute("class", "botaoOculto");
		classe.divBotao.setAttribute("class", "containerExpansivo");
		classe.container.setAttribute("style", "display: none;");
		setCoockie("estado"
				+ classe.container.getAttribute("id"), "fechado");
		}
		else{
			classe.botaoMaisAtivo = 1;
			classe.botaoMais.setAttribute("class", "botaoOculto");
			classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
			classe.container.setAttribute("style", "display: inline-table;");
			classe.divBotao.setAttribute("class", "containerExpansivoEscuro");
			setCoockie("estado"
					+ classe.container.getAttribute("id"), "aberto");
		}
	};

	this.hover = function(e) {
		if (classe.botaoMaisAtivo == 1) {
			classe.botaoMenos.setAttribute("class", "botaoMaisMenosOver");
		} else {
			classe.botaoMais.setAttribute("class", "botaoMaisMenosOver");
		}

	};

	this.hout = function(e) {
		if (classe.botaoMaisAtivo == 1) {
			classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
		} else {
			classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		}
	};

	this.clickMais = function(e) {
		classe.botaoMaisAtivo = 1;
		classe.botaoMais.setAttribute("class", "botaoOculto");
		classe.botaoMenos.setAttribute("class", "botaoMaisMenos");
		fadeIn(classe.container);
		classe.divBotao.setAttribute("class", "containerExpansivoEscuro");
		setCoockie("estado"
				+ classe.container.getAttribute("id"), "aberto");
	};

	this.clickMenos = function(e) {
		classe.botaoMaisAtivo = 0;
		classe.botaoMais.setAttribute("class", "botaoMaisMenos");
		classe.botaoMenos.setAttribute("class", "botaoOculto");
		fadeOut(classe.container);
		classe.divBotao.setAttribute("class", "containerExpansivo");
		setCoockie("estado"
				+ classe.container.getAttribute("id"), "fechado");
	};

	
}
/**
 * função recupera o estado salvo no cookie da página
 * @param id
 * @returns
 */
function getCookie(id) {
	var i, x, y, cookiesMap = document.cookie.split(";");
	for (i = 0; i < cookiesMap.length; i++) {
		x = cookiesMap[i].substr(0, cookiesMap[i].indexOf("="));
		y = cookiesMap[i].substr(cookiesMap[i].indexOf("=") + 1);
		x = x.replace(/^\s+|\s+$/g, "");
		if (x == id) {
			return unescape(y);
		}
	}
};

/**
 * salva no cookie o estado do container
 * @param id
 * @param estado
 */
function setCoockie (id, estado) {

	document.cookie = id + "=" + estado;
};
/**
 * 
 * @param id
 */
function fadeOut(e) {
	var time = 0.5;
	var ini = 100;
	var fin = 0;
    var target = e;
    var alpha = ini;
    var inc;
    if (fin >= ini) { 
        inc = 4; 
    } else {
        inc = -4;
    }
    timer = (time * 1000) / 25;
    var i = setInterval(
        function() {
            if ((inc > 0 && alpha >= fin) || (inc < 0 && alpha <= fin)) {
                clearInterval(i);
                e.setAttribute("style", "display: none;");
            }
            setAlpha(target, alpha);
            alpha += inc;
        }, timer);
}



function fadeIn(e) {
	e.style.opacity = 0/100;
	e.setAttribute("style", "display: inline-table;");
	var time = 0.3;
	var ini = 0;
	var fin = 100;
    var target = e;
    var alpha = ini;
    var inc;
    if (fin >= ini) { 
        inc = 4; 
    } else {
        inc = -4;
    }
    timer = (time * 1000) /25;
    var i = setInterval(
        function() {
            if ((inc > 0 && alpha >= fin) || (inc < 0 && alpha <= fin)) {
                clearInterval(i);
            }
            setAlpha(target, alpha);
            alpha += inc;
        }, timer);
    
}

function setAlpha(target, alpha) {
	target.style.filter = "alpha(opacity="+ alpha +")";
	target.style.opacity = alpha/100;
}



iniciarComponenteContainerExpansivo = (function(idContainer, idMenos, idMais,
		idDivBotao) {
	var container = document.getElementById(idContainer);
	var botaoMenos = document.getElementById(idMenos);
	var botaoMais = document.getElementById(idMais);
	var divBotao = document.getElementById(idDivBotao);
	

	var containerExpansivo = new ContainerExpansivo(container, botaoMenos,
			botaoMais, divBotao);

	containerExpansivo.inicio();
});