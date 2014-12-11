package br.usp.memoriavirtual.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.usp.memoriavirtual.modelo.fachadas.remoto.ValidacaoRemote;

@ManagedBean(name = "validacaoMB")
@RequestScoped
public class ValidacoesDeCampos {

	@EJB
	private ValidacaoRemote validacaoEJB;

	public static final int LIMITE_PADRAO_CAMPO_TEXTO = 10;
	
	public static boolean validarFormatoEstado(String estado) {
		
		String regexp = "[A-Z]{2}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(estado);

		if (!matcher.matches())
			return false;

		return true;
	}

	public static boolean validarFormatoEmail(String email) {
		
		if (email.isEmpty()) return true;

		String regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
				+ "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
				+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoWebSite(String url) {
		
		if (url.isEmpty()) return true;

		String regexp = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(url);

		if (!matcher.matches())
			return false;

		return true;
	}

	public static boolean validarFormatoTelefone(String telefone) {

		if (telefone.isEmpty()) return true;
		
		String regexp8digitos = "([0-9]{2})[0-9]{4}-?[0-9]{4}$";
		Pattern pattern8digitos = Pattern.compile(regexp8digitos);
		Matcher matcher8digitos = pattern8digitos.matcher(telefone);

		String regexp9digitos = "([0-9]{2})[0-9]{5}-[0-9]{4}$";
		Pattern pattern9digitos = Pattern.compile(regexp9digitos);
		Matcher matcher9digitos = pattern9digitos.matcher(telefone);

		if (!matcher8digitos.matches() && !matcher9digitos.matches())
			return false;

		return true;
	}

	public static boolean validarFormatoCep(String Cep) {//Cep tem que seguir o formato XXXXX-XXX ou estar em branco
		if(Cep.isEmpty())
			return true;
		
		String regexp = "^[0-9]{5}-?[0-9]{3}$";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Cep);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoCaixaPostal(String CaixaPostal) {
		if(CaixaPostal.isEmpty())
			return true;
		
		String regexp = "^[0-9]{5}-?[0-9]{3}$";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(CaixaPostal);

		if (!matcher.matches())
			return false;

		return true;
	}

	public static boolean validarFormatoLocalizacao(String Localizacao) {

		String regexp = "[0-9]{3}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Localizacao);

		if (!matcher.matches())
			return false;

		return true;
	}

	public static boolean validaCoordenadaGeografica(String Coordenada) {

		String regexp = "[-]?[0-9]*[.]{0,1}[0-9]{4}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Coordenada);

		if (!matcher.matches() || Coordenada.equals(""))
			return false;

		return true;
	}

	public static boolean validarAltitude(String Altitude) {

		String regexp = "[-]?[0-9]*[.]?{0,1}[0-9]{2}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Altitude);

		if (!matcher.matches() || Altitude.equals(""))
			return false;

		return true;
	}

	public static boolean validarNaoNulo(Object o) {
		if (o == null) {
			return false;
		}
		return true;
	}

	public static boolean validarStringNaoVazia(String s) {
		if (s.length() == 0) {
			return false;
		}
		return true;
	}

	public static boolean validarComprimento(String s, int l) {
		if (s.length() == l) {
			return true;
		}
		return false;
	}

	public boolean validarUnico(String query, Object o,
			Map<String, Object> parametros) throws Exception {
		try {
			return this.validacaoEJB.validacaoUnico(query, o, parametros);
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean validarNaoExiste(String query, Object o,
			Map<String, Object> parametros) throws Exception {
		try {
			return this.validacaoEJB.validacaoNaoExiste(query, o, parametros);
		} catch (Exception e) {
			throw e;
		}
	}

}
