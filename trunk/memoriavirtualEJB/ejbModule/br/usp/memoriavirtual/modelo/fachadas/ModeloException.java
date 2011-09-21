package br.usp.memoriavirtual.modelo.fachadas;

/**
 * Essa exceção deverá ser lançada por todos os métodos das fachadas.
 * 
 * @author tbianchi
 * 
 */
public class ModeloException extends Exception {

	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 1499067965457668515L;

	public ModeloException() {

	}

	public ModeloException(String mensagem) {
		super(mensagem);
	}

	public ModeloException(Throwable excecao) {
		super(excecao);
	}

	public ModeloException(String mensagem, Throwable excecao) {
		super(mensagem, excecao);
	}

}
