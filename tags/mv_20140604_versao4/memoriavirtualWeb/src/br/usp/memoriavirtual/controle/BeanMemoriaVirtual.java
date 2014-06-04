package br.usp.memoriavirtual.controle;

public interface BeanMemoriaVirtual {
	public String traduzir(String chave);

	public String redirecionar(String pagina, boolean redirect);

	public String cancelar();

	public boolean validar();
}
