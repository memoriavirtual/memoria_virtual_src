package mobile.memoriavirtual.usp.mvmobile.Utils;

/**
 * Created by danieleboscolo on 22/07/15.
 */

public class URLs {

    public static final String NUMERO_IP = "[IP]";

    public static final String URL_IP = "http://" + NUMERO_IP + ":8080/";
    public static final String URL_DOMAIN = URL_IP + "memoriavirtualWebService/rest/";
    public static final String URL_ENVIAR_BEM_PATRIMONIAL = URL_DOMAIN + "cadastrar/bemPatrimonial/";
    public static final String URL_LISTAR_INSTITUICOES = URL_DOMAIN + "buscar/listarInstituicoes";
}
