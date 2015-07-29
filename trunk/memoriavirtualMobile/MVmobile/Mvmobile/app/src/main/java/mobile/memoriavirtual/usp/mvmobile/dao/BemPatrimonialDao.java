package mobile.memoriavirtual.usp.mvmobile.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.services.impl.BaseServiceImpl;

/**
 * Created by danieleboscolo on 25/07/14.
 */

public class BemPatrimonialDao extends BaseServiceImpl {

    public static List<BemPatrimonial> parseBemPatrimoniaisArray(JSONObject jsonObject) throws JSONException {
        List<BemPatrimonial> channels = new ArrayList<BemPatrimonial>();
        //JSONObject channelsObj = jsonObject.getJSONObject("channels");

        JSONArray bemArray = null;

        //Verifica se é um dicionario ou array
        Object object = jsonObject.get("channel");

        if (object instanceof JSONObject) {
            bemArray = new JSONArray();
            bemArray.put(object);
        } else {
            bemArray = (JSONArray) object;
        }

        for (int i = 0; i < bemArray.length(); i++) {
            JSONObject channelJsonObject = bemArray.getJSONObject(i);
            channels.add(parseBemPatrimonial(channelJsonObject));
        }
        return channels;

    }

    private static BemPatrimonial parseBemPatrimonial(JSONObject jsonChannel) throws JSONException {
        BemPatrimonial bem = new BemPatrimonial();
        /*
                    Utils.safeString(jsonChannel, "id_channel"),
                    Utils.safeString(jsonChannel, "tx_name"),
                    Utils.safeString(jsonChannel, "tx_desc"),
                    Utils.safeString(jsonChannel, "id_type"),
                    Utils.safeString(jsonChannel, "id_user"),
                    Utils.safeInteger(jsonChannel, "vl_rank"),
                    Utils.safeInteger(jsonChannel, "vl_views"));
  */
        return bem;
    }
}
