package br.usp.memoriavirtual.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
   


public class ValidacoesDeCampos {

	public static boolean validarFormatoEmail(String email) {

		String regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
				+ "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
				+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoTelefone(String telefone) {

		String regexp = "\\([0-9]{2}?\\)[0-9]{4}?\\-[0-9]{4}?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(telefone);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoCep(String Cep) {

		String regexp = "[0-9]{5}?\\-[0-9]{3}?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Cep);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoLocalizacao(String Localizacao) {

		String regexp = "[0-9]{2}?\\�[SN]{1}?\\-[0-9]{2}?\\�[OL]{1}?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Localizacao);

		if (!matcher.matches())
			return false;

		return true;
	}
	
}
