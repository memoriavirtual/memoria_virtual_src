package mobile.memoriavirtual.usp.mvmobile.services;

import com.android.volley.Response;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;


/**
 * Created by danieleboscolo on 25/07/15.
 */

public interface BemPatrimonialService {

    public void enviarBemPatrimonial(String username, String senha, String idInstituicao, BemPatrimonial bemPatrimonial, final Response.Listener<String> result, final Response.ErrorListener responseError);
}