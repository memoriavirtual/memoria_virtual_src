package mobile.memoriavirtual.usp.mvmobile.services.impl;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.Utils.AppConstant;
import mobile.memoriavirtual.usp.mvmobile.Utils.URLs;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;
import mobile.memoriavirtual.usp.mvmobile.manager.VolleyManager;
import mobile.memoriavirtual.usp.mvmobile.services.BemPatrimonialService;


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
    public void enviarBemPatrimonial(String username, String senha, String idInstituicao, BemPatrimonial bemPatrimonial, final Response.Listener<String> result, final Response.ErrorListener responseError) {
        final String URL = URLs.URL_ENVIAR_BEM_PATRIMONIAL + idInstituicao;


        try {
            JSONObject obj = new JSONObject();

            if (bemPatrimonial != null) {
                obj = Utils.parseBemPatrimonialToJSON(bemPatrimonial);
                //obj.put("bemPatrimonial", bemStr);
            }

            // prepare the Request
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, obj,
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

            )
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>() ;
                    header.put("authorization", "bXZpcnR1YWw6bXZpcnR1YWw=");
                    return header;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            VolleyManager.getInstance().addToRequestQueue(req, AppConstant.TAG_ENVIAR_BEM_PATRIMONIAL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
