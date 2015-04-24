package br.usp.memoriavirtual.utils;

public enum MVControleTiposDeMensagem {
	erro, sucesso, aviso;
	
    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
        case 0:
            name = "text-danger alert alert-danger";
            break;
        case 1:
            name = "text-success alert alert-success";
            break;
        case 2:
            name = "text-warning alert alert-warning";
            break;
        default:
            name = "";
            break;
        }
        return name;
    }
}
