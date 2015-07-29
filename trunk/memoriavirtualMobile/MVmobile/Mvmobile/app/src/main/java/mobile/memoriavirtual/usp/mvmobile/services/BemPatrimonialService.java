package mobile.memoriavirtual.usp.mvmobile.services;

import com.android.volley.Response;


/**
 * Created by danieleboscolo on 25/07/15.
 */

public interface BemPatrimonialService {

    public void enviarBemPatrimonial(String username, String senha, String idInstituicao, mobile.memoriavirtual.usp.mvmobile.Model.BemPatrimonial bemPatrimonial, final Response.Listener<String> result, final Response.ErrorListener responseError);
}