function mensagensSucesso(mensagem) {
	$('#mensagens').attr('class', 'text-success alert alert-success');
	$('#mensagens').find('span').text(mensagem);
	$('#mensagens').show('slow');
}

function limparMensagens() {
	var mensagens = $('#mensagens');
	mensagens.slideUp('slow');
	$.get('/memoriavirtual/limparmensagens', function() {
	});
}

function selecionarAbaInstituicao() {
	selecionarAba("1");
}

function selecionarAbaPatrimonio() {
	selecionarAba("2");
}

function selecionarAbaRevisao() {
	selecionarAba("3");
}

function selecionarAbaAutor() {
	selecionarAba("4");
}

function selecionarAbaAdministracao() {
	selecionarAba("5");
}

function selecionarAbaConta() {
	selecionarAba("6");
}

function selecionarAba(indiceItemMenu) {
	todosItens = $('.linkPrincipal');
	todosItens.removeClass('itemAtual');
	todosItens.addClass('outrosItens');

	segundoItemAnchor = $("#menuNavegacao li:nth-of-type(" + indiceItemMenu + ")").find(
			'.linkPrincipal');
	segundoItemAnchor.addClass('itemAtual');

	filhos = $('#menuNavegacao li:nth-of-type(' + indiceItemMenu + ')').find(
			'.linkSecundario').clone();
	subMenu = $('#subMenu');
	subMenu.html(filhos);
}

function mostrarErro(messagesId) {

	var messages = $(messagesId);

	var texto = messages.text();
	var formGroup = messages.closest('.form-group');

	if (texto !== '') {
		if (messages.hasClass('has-error')) {
			formGroup.addClass('has-error');
			formGroup.removeClass('has-warning');
			formGroup.removeClass('has-success');
		} else if (messages.hasClass('has-warning')) {
			formGroup.addClass('has-warning');
			formGroup.removeClass('has-error');
			formGroup.removeClass('has-success');
		} else if (messages.hasClass('has-success')) {
			formGroup.addClass('has-success');
			formGroup.removeClass('has-error');
			formGroup.removeClass('has-warning');
		}
	} else {
		formGroup.removeClass('has-error');
		formGroup.removeClass('has-warning');
		formGroup.removeClass('has-success');
	}
}

$('document').ready(function() {
	$('.container-expansivo-label').click(function(e){
		e.preventDefault();
		$(this).siblings('.container-expansivo-conteudo').toggle('slow');
	});
});