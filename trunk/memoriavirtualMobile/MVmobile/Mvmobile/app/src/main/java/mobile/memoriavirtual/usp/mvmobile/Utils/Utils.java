package mobile.memoriavirtual.usp.mvmobile.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.memoriavirtual.usp.mvmobile.Model.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.R;

/**
 * Created by User on 10/02/2015.
 */
public class Utils {

    public static Context mContext;
    public static String mPathBemPatrimonial;

    public static void setContext(Context context){
        mContext = context;
        mPathBemPatrimonial = context.getString(R.string.cache_bens_patrimoniais);
    }

    public static Context getContext(){
        return mContext;
    }

    public static void salvarBemPatrimonialCache(BemPatrimonial bemPatrimonial) throws JSONException {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Map<String, String> map = new HashMap<String, String>();
        map.put(mContext.getString(R.string.cadastro_tipo), bemPatrimonial.getTipo());
        map.put(mContext.getString(R.string.cadastro_num_registro), bemPatrimonial.getNumRegistro());

        //transforma Map em JSONObject
        JSONObject json = new JSONObject(map);
        String jsonString = json.toString();

        System.out.println("JSON OBJECT ADD = " + jsonString);

        //Recupera bens já salvos em cache e adiciona o novo
        JSONArray array = carregarBemPatrimonialJSONArray();
        array.put(jsonString);
        salvarBemPatrimonialJSONArray(array);
    }

    public static ArrayList<BemPatrimonial> carregarBensPatrimoniaisSalvosCache()
    {
        Map<String,String> outputMap = new HashMap<String,String>();

        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial,Context.MODE_PRIVATE);
        try{
            if (sharedPref != null){
                JSONArray array = carregarBemPatrimonialJSONArray();

                System.out.println("JSON RESPOSTA = "+ array);

                ArrayList<BemPatrimonial> arrayBemPatrimonial = new ArrayList<BemPatrimonial>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = new JSONObject(array.getString(i));
                    BemPatrimonial bp = BemPatrimonial.parseBemPatrimonial(obj);
                    arrayBemPatrimonial.add(bp);
                }

                return arrayBemPatrimonial;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
/*
    public static void saveJSONObject(Context c, String prefName, String key, JSONObject object) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mPathBemPatrimonial, object.toString());
        editor.commit();
    }

    public static JSONObject loadJSONObject(Context c, String prefName, String key) throws JSONException {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial,Context.MODE_PRIVATE);
        return new JSONObject(sharedPref.getString(mPathBemPatrimonial, (new JSONObject()).toString()));
    }*/

    public static void salvarBemPatrimonialJSONArray(JSONArray array) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mPathBemPatrimonial, array.toString());
        editor.commit();
    }

    public static JSONArray carregarBemPatrimonialJSONArray() throws JSONException {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial,Context.MODE_PRIVATE);

        String cache = (sharedPref.getString(mPathBemPatrimonial, (new JSONObject()).toString()));

        //Se for null ou json vazio entao cria a chave, senao retorna o cache
        if( cache == null || cache.equals("{}")) {
            //set key
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(mPathBemPatrimonial, String.valueOf(new JSONArray()));
            editor.commit();
            return new JSONArray();
        }
        else{
            return new JSONArray(sharedPref.getString(mPathBemPatrimonial, (new JSONObject()).toString()));
        }

    }

    public static void removeCache() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial,Context.MODE_PRIVATE);
        if (sharedPref.contains(mPathBemPatrimonial)) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(mPathBemPatrimonial);
            editor.commit();
        }
    }
}
