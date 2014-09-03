package br.usp.memoriavirtual.utils;

public enum MVModeloMapeamentoUrl {
	excluirInstituicao("/restrito/validarexclusaoinstituicao.jsf"), 
	cadastrarUsuario("/cadastrarusuario.jsf"), 
	editarCadastroUsuario("/restrito/validaredicaousuario.jsf"), 
	excluirUsuario("/restrito/validarexclusaousuario.jsf");
	
    private String url;       

    private MVModeloMapeamentoUrl(String url) {
        this.url = url;
    }

    public String toString(){
       return url;
    }
}
