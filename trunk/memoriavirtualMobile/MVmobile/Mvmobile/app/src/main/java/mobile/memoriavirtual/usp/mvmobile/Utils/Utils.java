package mobile.memoriavirtual.usp.mvmobile.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        //Parse
        String jsonString = parseBemPatrimonialToString(bemPatrimonial);
        System.out.println("JSON OBJECT ADD = " + jsonString);

        //Recupera bens já salvos em cache e adiciona o novo
        JSONArray array = carregarBemPatrimonialJSONArray();
        array.put(jsonString);
        salvarBemPatrimonialJSONArray(array);
    }

    public static void editarBemPatrimonialCache(BemPatrimonial bemPatrimonial, Integer index) throws JSONException {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Parse
        String jsonString = parseBemPatrimonialToString(bemPatrimonial);
        System.out.println("JSON OBJECT ADD = " + jsonString);

        //Recupera bens já salvos em cache e adiciona o novo
        JSONArray output = new JSONArray();
        JSONArray array = carregarBemPatrimonialJSONArray();
        int len = array.length();

        for (int i = 0; i < len; i++)   {
            //Se for o index do bem a ser editado entao salva este, senao salva o restante do array
            if (i == index) {
                output.put(jsonString);
            }
            else{
                try {
                    output.put(array.get(i));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        salvarBemPatrimonialJSONArray(output);
    }

    public static void removerBemPatrimonialCache(Integer index) throws JSONException {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathBemPatrimonial, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Recupera bens já salvos em cache e adiciona o novo
        JSONArray output = new JSONArray();
        JSONArray array = carregarBemPatrimonialJSONArray();
        int len = array.length();

        for (int i = 0; i < len; i++)   {
            //Se for o index do bem a ser editado entao salva este, senao salva o restante do array
            if (i != index) {
                try {
                    output.put(array.get(i));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        salvarBemPatrimonialJSONArray(output);
    }




    public static String parseBemPatrimonialToString(BemPatrimonial bemPatrimonial) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();

        //Midia
        map.put(mContext.getString(R.string.cadastro_midia), bemPatrimonial.getCadastro_image());

        //Informações Gerais
        map.put(mContext.getString(R.string.cadastro_externo), bemPatrimonial.getCadastro_externo());
        map.put(mContext.getString(R.string.cadastro_tipo), bemPatrimonial.getCadastro_tipo());
        map.put(mContext.getString(R.string.cadastro_num_registro), bemPatrimonial.getCadastro_num_registro());
        map.put(mContext.getString(R.string.cadastro_titulo_principal), bemPatrimonial.getCadastro_titulo_principal());
        map.put(mContext.getString(R.string.cadastro_complemento), bemPatrimonial.getCadastro_complemento());
        map.put(mContext.getString(R.string.cadastro_colecao), bemPatrimonial.getCadastro_colecao());
        map.put(mContext.getString(R.string.cadastro_latitude), bemPatrimonial.getCadastro_latitude());
        map.put(mContext.getString(R.string.cadastro_longitude), bemPatrimonial.getCadastro_longitude());

        //transforma Map em JSONObject
        JSONObject json = new JSONObject(map);
        return json.toString();
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


    public static String safeString(JSONObject json, String string) throws JSONException {
        if (json.has(string)){
            return json.getString(string);
        }
        else{
            return "";
        }
    }

    public static String bitMapToString(Bitmap bitmap){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (Exception e){
            e.getMessage();
            return  null;
        }
    }

    public static Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
