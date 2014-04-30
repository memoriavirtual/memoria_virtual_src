package br.usp.memoriavirtual.utils;

public enum MVModeloMapeamentoUrl {
	excluirInstituicao("/excluirinstituicao"), cadastrarUsuario("/cadastrarusuario"), editarCadastroUsuario("/editarcadastrousuario"), excluirUsuario("/excluirusuario");
	
    private String url;       

    private MVModeloMapeamentoUrl(String url) {
        this.url = url;
    }

    public String toString(){
       return url;
    }
}
