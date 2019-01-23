package br.usp.memoriavirtual.utils;

import java.util.Calendar;
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
		
		//expressao regular: (XX)XXXX-XXXX
		String regexp8digitos = "[(][0-9]{2}[)][0-9]{4}-[0-9]{4}$";
		Pattern pattern8digitos = Pattern.compile(regexp8digitos);
		Matcher matcher8digitos = pattern8digitos.matcher(telefone);

		//expressao regular: (XX)XXXXX-XXXX
		String regexp9digitos = "[(][0-9]{2}[)][0-9]{5}-[0-9]{4}$";
		Pattern pattern9digitos = Pattern.compile(regexp9digitos);
		Matcher matcher9digitos = pattern9digitos.matcher(telefone);

		//verifica se a string do telefone é compatível com um dos 
		//formatos disponiveis de telefone
		if (!matcher8digitos.matches() && !matcher9digitos.matches())
			return false;

		return true;
	}

	public static boolean validarFormatoCep(String Cep) {//Cep tem que seguir o formato XXXXX-XXX ou estar em branco
		if(Cep.isEmpty())
			return true;
		
		String regexp = "\\d{5}-\\d{3}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Cep);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoCaixaPostal(String CaixaPostal) {
		if(CaixaPostal.isEmpty())
			return true;
		
		String regexp = "\\d{5}-\\d{3}";
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

	public static boolean validarLatitude(String Coordenada) {

		String regexp = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Coordenada);

		if (!matcher.matches() || Coordenada.equals(""))
			return false;

		return true;
	}

	public static boolean validarLongitude(String Coordenada){
		
		String regexp = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Coordenada);
		
		if(!matcher.matches() || Coordenada.equals(""))
			return false;
		
		return true;
	}
	
	public static boolean validarAltitude(String Altitude) {

		String regexp =  "^([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-7][0-9][0-9][0-9]|8[0-7][0-9][0-9]|88[0-3][0-9]|884[0-8])$";
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

	/**
	 * Verifica se uma dada string de data é precisa. Caso seja, verifica
	 * se está dentro do padrão adotado: "dd/mm/aaaa".
	 * @param data
	 * @param flag
	 * @return data validada (ou não)
	 */
	public static boolean validarData(String data, boolean flag){
		
		String regexp = "^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$";	//string de um código de expressão regular
		Pattern pattern = Pattern.compile(regexp);				//pattern definido pela string regexp
		Matcher matcher = pattern.matcher(data);				//verificando se a data está no padrão definido por regexp, em pattern
		
		//caso a string data não esteja no padrão definido
		//e a flag para verificar se a data é precisa for false
		if (matcher.matches() && flag == false){
			//Verificando a validade do dia, do mês e do ano
			String aux_data[] = data.split("/");
			int dia = Integer.parseInt(aux_data[0]);
			int mes = Integer.parseInt(aux_data[1]);
			int ano = Integer.parseInt(aux_data[2]);
			
			if ((mes >= 1 && mes <= 12) && 																//verificando os meses
				(dia >= 1 && dia <= 31)	&&																//verificando os dias
				(((ano % 4 == 0) && (mes == 2) && (dia <= 29)) || 										//verificando fevereiro em ano bissexto
						((ano % 4 != 0) && (mes == 2) && (dia <= 28)) ||								//verificando fevereiro em ano normal
				((mes == 4 || mes == 6 || mes == 9 || mes == 11) && (dia <= 30)) ||						//verificando os meses de abr, jun, set e nov
				(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12))){	//caso caia em qualquer outro mes
			
				//obtendo a data atual para comparar com a data inserida
				Calendar data_atual = Calendar.getInstance();
				
				if (ano <= data_atual.get(Calendar.YEAR)){							//ano inserido deve ser menor ou igual a data atual
					if (ano == data_atual.get(Calendar.YEAR)){						//verificando se o ano inserido é o ano atual
						if (mes <= (data_atual.get(Calendar.MONTH) + 1)){			//verificando se o mês inserido não é maior que o atual
							if (mes == (data_atual.get(Calendar.MONTH) + 1)){		//verificando se é o mês atual
								if (dia <= data_atual.get(Calendar.DAY_OF_MONTH))	//verificando se a data inserida está correta
									return true;
							}
							else return true;
						}
						else return true;
					}
					else return true;	
				}
			}
		}
		//caso não haja data precisa
		else if (flag == true) return true;
		
		return false;
	}
	
	/**
	 * Método que compara as datas de nascimento e óbito, caso as duas sejam definidas
	 * @param flagDataNascimento
	 * @param flagDataObito
	 * @param dataNascimento
	 * @param dataObito
	 * @return datas validadas (ou não)
	 */
	public static boolean compararDatasNascimentoEObito(boolean flagDataNascimento, boolean flagDataObito, String dataNascimento, String dataObito){
		//verificação das flags que indicam se determinada data é imprecisa (ou seja, sem máscara)
		if (flagDataNascimento == true || flagDataObito == true) return true;
		
		//caso alguma delas seja igual a true, deve-se verificar se a data de nascimento é menor ou igual a de óbito
		else{
			String data_nasc[] = dataNascimento.split("/");
			String data_obito[] = dataObito.split("/");
			
			int dia_nasc = Integer.parseInt(data_nasc[0]);
			int mes_nasc = Integer.parseInt(data_nasc[1]);
			int ano_nasc = Integer.parseInt(data_nasc[2]);
			int dia_obito = Integer.parseInt(data_obito[0]);
			int mes_obito = Integer.parseInt(data_obito[1]);
			int ano_obito = Integer.parseInt(data_obito[2]);
			
			if (ano_nasc <= ano_obito){					//ano de nascimento deve ser menor ou igual a data de óbito
				if (ano_nasc == ano_obito){				//verificando se o ano de nascimento é o ano de óbito
					if (mes_nasc <= mes_obito){			//verificando se o mês de nascimento não é maior que o de óbito
						if (mes_nasc == mes_obito){		//verificando se é o mês de óbito
							if (dia_nasc < dia_obito)	//verificando se a data de nascimento está correta
								return true;
							else return false;
						}
						else return true;
					}
					else return true;
				}
				else return true;	
			}
		}
		
		return false;
	}
}
