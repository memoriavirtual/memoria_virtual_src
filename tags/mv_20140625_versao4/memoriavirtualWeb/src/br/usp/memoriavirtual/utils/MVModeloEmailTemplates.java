package br.usp.memoriavirtual.utils;

public enum MVModeloEmailTemplates {
	excluirInstituicao("excluirInstituicao.html"), 
	enviarConvite("enviarConvite.html"), 
	editarCadastroUsuario("solicitarEdicaoUsuario.html"), 
	excluirUsuario("solicitarExclusaoUsuario.html");

	private String local;

	private MVModeloEmailTemplates(String local) {
		this.local = local;
	}

	public String toString() {
		return local;
	}
}
