package mobile.memoriavirtual.usp.mvmobile.services.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.manager.VolleyManager;
import mobile.memoriavirtual.usp.mvmobile.services.BemPatrimonialService;
import mobile.memoriavirtual.usp.mvmobile.Utils.AppConstant;
import mobile.memoriavirtual.usp.mvmobile.Utils.URLs;


/**
 * Created by danieleboscolo on 25/07/15.
 */

public class BemPatrimonialServiceImpl extends BaseServiceImpl implements BemPatrimonialService {

    private static BemPatrimonialService instance;

    public static BemPatrimonialService getInstance() {
        if (instance == null) {
            instance = new BemPatrimonialServiceImpl();
        }
        return instance;
    }

    private BemPatrimonialServiceImpl() {

    }

    @Override
    public void enviarBemPatrimonial(String username, String senha, String idInstituicao, mobile.memoriavirtual.usp.mvmobile.Model.BemPatrimonial bemPatrimonial, final Response.Listener<String> result, final Response.ErrorListener responseError) {
        final String URL = URLs.URL_ENVIAR_BEM_PATRIMONIAL;

        try {
            JSONObject obj = new JSONObject();

            if (bemPatrimonial != null) {
                obj.put("bemPatrimonial", bemPatrimonial);
            }

            if (idInstituicao != null) {
                obj.put("instituicao", idInstituicao);
            }

            JSONObject params = new JSONObject();
            params.put("params", obj);

            // prepare the Request
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                          String status = "";
                            try {
                                //TODO: VERIFICAR KEY QUE CHECA STATUS
                                status = response.getString("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            result.onResponse(status);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            responseError.onErrorResponse(error);
                        }
                }
        );

        VolleyManager.getInstance().addToRequestQueue(req, AppConstant.TAG_ENVIAR_BEM_PATRIMONIAL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
