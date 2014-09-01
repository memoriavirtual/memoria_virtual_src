package br.usp.memoriavirtual.controle;

public interface BeanMemoriaVirtual {
	public String traduzir(String chave);

	public String redirecionar(String pagina, boolean redirect);

	public String cancelar();

	public boolean validar();
	
	public void validarCampo(String nomeCampoMensagem, String nomeCampo, String campo);
	/* nomeCampoMensagem = Campo em que a mensagem de erro sera exibida,
	 * nomeCampo = Nome generico do campo a ser exibido o erro para usuario,
	 * campo = Atributo do form/bean a ser validado
	 */
}
