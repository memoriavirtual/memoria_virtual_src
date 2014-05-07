package br.usp.memoriavirtual.utils;

public enum MVModeloEmailTemplates {
	excluirInstituicao("/opt/emailTemplates/excluirInstituicao.html"), enviarConvite(
			"/opt/emailTemplates/enviarConvite.html"), editarCadastroUsuario(
			"/opt/emailTemplates/solicitarEdicaoUsuario.html"), excluirUsuario(
			"/opt/emailTemplates/solicitarExclusaoUsuario.html");

	private String local;

	private MVModeloEmailTemplates(String local) {
		this.local = local;
	}

	public String toString() {
		return local;
	}
}
