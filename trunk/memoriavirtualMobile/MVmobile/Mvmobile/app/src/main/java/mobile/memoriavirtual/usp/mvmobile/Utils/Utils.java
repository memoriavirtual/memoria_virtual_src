package mobile.memoriavirtual.usp.mvmobile.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Producao;
import mobile.memoriavirtual.usp.mvmobile.R;


/**
 * Created by daniele on 28/07/2015.
 */
public class Utils {
    public static Context mContext;
    public static String mPathBemPatrimonial;
    public static String mPathLogin;

    public static void setContext(Context context){
        mContext = context;
        mPathBemPatrimonial = context.getString(R.string.cache_bens_patrimoniais);
        mPathLogin = context.getString(R.string.cache_login);
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

    public static JSONObject parseBemPatrimonialToJSON(BemPatrimonial bemPatrimonial) throws JSONException {
        //Map<String, Object> map = new HashMap<>();
        JSONObject map = new JSONObject();

        //Midia
        //map.put(mContext.getString(R.string.cadastro_midia), bemPatrimonial.getContainerMultimidia());

        //Informações Gerais
        //  map.put(br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial.condicoesTopograficas.acidentado., bemPatrimonial.getCadastro_externo());
        map.put(mContext.getString(R.string.atributo_tipo), bemPatrimonial.getTipoBemPatrimonial());
        map.put(mContext.getString(R.string.atributo_num_registro), bemPatrimonial.getNumeroRegistro());
        map.put(mContext.getString(R.string.atributo_titulo_principal), bemPatrimonial.getTituloPrincipal());
        map.put(mContext.getString(R.string.atributo_complemento), bemPatrimonial.getComplemento());
        map.put(mContext.getString(R.string.atributo_colecao), bemPatrimonial.getColecao());
        map.put(mContext.getString(R.string.atributo_latitude), bemPatrimonial.getLatitude());
        map.put(mContext.getString(R.string.atributo_longitude), bemPatrimonial.getLongitude());

        return map;
    }
    public static String parseBemPatrimonialToString(BemPatrimonial bemPatrimonial) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        //Midia
        map.put(mContext.getString(R.string.cadastro_midia), bemPatrimonial.getContainerMultimidia());

        //Informações Gerais
      //  map.put(br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial.condicoesTopograficas.acidentado., bemPatrimonial.getCadastro_externo());
        map.put(mContext.getString(R.string.atributo_tipo), bemPatrimonial.getTipoBemPatrimonial());
        map.put(mContext.getString(R.string.atributo_num_registro), bemPatrimonial.getNumeroRegistro());
        map.put(mContext.getString(R.string.atributo_titulo_principal), bemPatrimonial.getTituloPrincipal());
        map.put(mContext.getString(R.string.atributo_complemento), bemPatrimonial.getComplemento());
        map.put(mContext.getString(R.string.atributo_colecao), bemPatrimonial.getColecao());
        map.put(mContext.getString(R.string.atributo_latitude), bemPatrimonial.getLatitude());
        map.put(mContext.getString(R.string.atributo_longitude), bemPatrimonial.getLongitude());

        //Autor
        map.put(mContext.getString(R.string.atributo_autoria), bemPatrimonial.getAutorias());

        //Producao

        map.put(mContext.getString(R.string.atributo_local), bemPatrimonial.getProducao().getLocal());
        map.put(mContext.getString(R.string.atributo_ano), bemPatrimonial.getProducao().getAno();
        map.put(mContext.getString(R.string.atributo_edicao), bemPatrimonial.getCadastro_edicao());
        map.put(mContext.getString(R.string.atributo_outras_responsabilidades), bemPatrimonial.getCadastro_outras_responsabilidades());

        //Descricao
        map.put(mContext.getString(R.string.atributo_caracteristicas), bemPatrimonial.getCadastro_caracteristicas());
        map.put(mContext.getString(R.string.atributo_dimensoes_quantificacoes), bemPatrimonial.getCadastro_dimensoes_quantificacoes());
        map.put(mContext.getString(R.string.atributo_condicao_topografica), bemPatrimonial.getCadastro_condicao_topografica());
        map.put(mContext.getString(R.string.atributo_uso), bemPatrimonial.getCadastro_uso());
        map.put(mContext.getString(R.string.atributo_num_ambientes), bemPatrimonial.getCadastro_num_ambientes());
        map.put(mContext.getString(R.string.atributo_num_pavimentos), bemPatrimonial.getCadastro_num_pavimentos());

        map.put(mContext.getString(R.string.atributo_alcova), bemPatrimonial.getCadastro_alcova());
        map.put(mContext.getString(R.string.atributo_porao), bemPatrimonial.getCadastro_porao());
        map.put(mContext.getString(R.string.atributo_sotao), bemPatrimonial.getCadastro_sotao());

        map.put(mContext.getString(R.string.atributo_meio_antropico), bemPatrimonial.getCadastro_meio_antropico());
        map.put(mContext.getString(R.string.atributo_carac_ambientais), bemPatrimonial.getCadastro_carac_ambientais());
        map.put(mContext.getString(R.string.atributo_sitio_paisagem), bemPatrimonial.getCadastro_sitio_paisagem());
        map.put(mContext.getString(R.string.atributo_agua_proxima), bemPatrimonial.getCadastro_agua_proxima());
        map.put(mContext.getString(R.string.atributo_vegetacao), bemPatrimonial.getCadastro_vegetacao());
        map.put(mContext.getString(R.string.atributo_exposicao), bemPatrimonial.getCadastro_exposicao());
        map.put(mContext.getString(R.string.atributo_uso_atual), bemPatrimonial.getCadastro_uso_atual());
        map.put(mContext.getString(R.string.atributo_outros), bemPatrimonial.getCadastro_outros());
        map.put(mContext.getString(R.string.atributo_area_total), bemPatrimonial.getCadastro_area_total());
        map.put(mContext.getString(R.string.atributo_altura_fachada_frontal), bemPatrimonial.getCadastro_altura_fachada_frontal());
        map.put(mContext.getString(R.string.atributo_altura_fachada_posterior), bemPatrimonial.getCadastro_altura_fachada_posterior());
        map.put(mContext.getString(R.string.atributo_largura), bemPatrimonial.getCadastro_largura());
        map.put(mContext.getString(R.string.atributo_altura), bemPatrimonial.getCadastro_altura());
        map.put(mContext.getString(R.string.atributo_profundidade), bemPatrimonial.getCadastro_profundidade());

        map.put(mContext.getString(R.string.atributo_altura_cumeeira), bemPatrimonial.getCadastro_altura_cumeeira());
        map.put(mContext.getString(R.string.atributo_altura_total), bemPatrimonial.getCadastro_altura_total());
        map.put(mContext.getString(R.string.atributo_pe_direito_terreo), bemPatrimonial.getCadastro_pe_direito_terreo());
        map.put(mContext.getString(R.string.atributo_tipo_pe_direito), bemPatrimonial.getCadastro_tipo_pe_direito());
        map.put(mContext.getString(R.string.atributo_comprimento), bemPatrimonial.getCadastro_comprimento());
        map.put(mContext.getString(R.string.atributo_localizacao_fisica), bemPatrimonial.getCadastro_localizacao_fisica());
        map.put(mContext.getString(R.string.atributo_conteudo), bemPatrimonial.getCadastro_conteudo());
        map.put(mContext.getString(R.string.atributo_meio_acesso), bemPatrimonial.getCadastro_meio_acesso());
        map.put(mContext.getString(R.string.atributo_notas), bemPatrimonial.getCadastro_notas());

        //Estado
        map.put(mContext.getString(R.string.atributo_estado_preservacao), bemPatrimonial.getCadastro_estado_preservacao());
        map.put(mContext.getString(R.string.atributo_estado_convervacao), bemPatrimonial.getCadastro_estado_convervacao());
        map.put(mContext.getString(R.string.atributo_notas_estado_convervacao), bemPatrimonial.getCadastro_notas_estado_convervacao());

        //Disponibilidadget
        map.put(mContext.getString(R.string.atributo_condicao_reproducao), bemPatrimonial.getCadastro_condicao_reproducao());
        map.put(mContext.getString(R.string.atributo_protecao), bemPatrimonial.getCadastro_protecao());
        map.put(mContext.getString(R.string.atributo_numero_processo), bemPatrimonial.getCadastro_numero_processo());
        map.put(mContext.getString(R.string.atributo_data_retorno), bemPatrimonial.getCadastro_data_retorno());
        map.put(mContext.getString(R.string.atributo_notas_uso_aproveitamento), bemPatrimonial.getCadastro_notas_uso_aproveitamento());

        //Procedencia
        map.put(mContext.getString(R.string.atributo_tipo_aquisicao), bemPatrimonial.getCadastro_tipo_aquisicao());
        map.put(mContext.getString(R.string.atributo_valor_venal), bemPatrimonial.getCadastro_valor_venal());
        map.put(mContext.getString(R.string.atributo_data), bemPatrimonial.getCadastro_data());
        map.put(mContext.getString(R.string.atributo_primeiro_proprietario), bemPatrimonial.getCadastro_primeiro_proprietario());
        map.put(mContext.getString(R.string.atributo_dados_transacao), bemPatrimonial.getCadastro_dados_transacao());
        map.put(mContext.getString(R.string.atributo_historico), bemPatrimonial.getCadastro_historico());
        map.put(mContext.getString(R.string.atributo_instrumento_pesquisa), bemPatrimonial.getCadastro_instrumento_pesquisa());

        //Assuntos
        map.put(mContext.getString(R.string.atributo_assuntos), bemPatrimonial.getCadastro_assuntos());
        map.put(mContext.getString(R.string.atributo_descritores), bemPatrimonial.getCadastro_descritores());
        map.put(mContext.getString(R.string.atributo_fontes_informacao), bemPatrimonial.getCadastro_fontes_informacao());
        map.put(mContext.getString(R.string.atributo_pesquisadores), bemPatrimonial.getCadastro_pesquisadores());
        map.put(mContext.getString(R.string.atributo_relacionar_outros_bens), bemPatrimonial.getCadastro_relacionar_outros_bens());

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
                    BemPatrimonial bp = parseBemPatrimonial(obj);
                    arrayBemPatrimonial.add(bp);
                }

                return arrayBemPatrimonial;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static BemPatrimonial parseBemPatrimonial(JSONObject jsonObject) throws JSONException {

        BemPatrimonial bp = new BemPatrimonial();

        Resources resources = Utils.getContext().getResources();

        //Midia
        ContainerMultimidia containerMultimidia = new ContainerMultimidia();

        /*
        bp.cadastro_image = new ArrayList<String>();

        JSONArray imageArray = jsonObject.getJSONArray(resources.getString(R.string.cadastro_midia));
        for (int i = 0; i < imageArray.length() ; i++) {
            String dataImage = imageArray.getString(i);
            bp.cadastro_image.add(dataImage);
        }
        */

        bp.setContainerMultimidia(containerMultimidia);

        //Informações gerais
       // bp.setExterno(Utils.safeString(jsonObject,resources.getString(R.string.cadastro_externo)));
        bp.setTipoBemPatrimonial(Utils.safeString(jsonObject,resources.getString(R.string.atributo_tipo)));
        bp.setNumeroRegistro(Utils.safeString(jsonObject, resources.getString(R.string.atributo_num_registro)));
        bp.setTituloPrincipal(Utils.safeString(jsonObject, resources.getString(R.string.atributo_titulo_principal)));
        bp.setComplemento(Utils.safeString(jsonObject, resources.getString(R.string.atributo_complemento)));
        bp.setColecao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_colecao)));
       // bp.cadastro_latitude = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_latitude));
       // bp.cadastro_longitude = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_longitude));

        return bp;
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


    //-------------------------------------------- LOGIN ------------------------------------------------------
    public static void salvarLoginCache(String email, String senha, String codInstituicao) throws JSONException {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathLogin, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Cria map dos dados
        Map<String, Object> map = new HashMap<>();
        map.put(mContext.getString(R.string.login_email), email);
        map.put(mContext.getString(R.string.login_senha), senha);
        map.put(mContext.getString(R.string.login_cod_instituition), codInstituicao);

        //transforma Map em JSONObject
        JSONObject json = new JSONObject(map);
        String jsonString = json.toString();

        //salva no Cache
        editor.putString(mPathLogin, jsonString);
        editor.commit();
    }

    public static JSONObject carregarLoginSalvoCache() throws JSONException {

        SharedPreferences sharedPref = mContext.getSharedPreferences(mPathLogin,Context.MODE_PRIVATE);
        String cache = (sharedPref.getString(mPathBemPatrimonial, (new JSONObject()).toString()));

        //Se não for null retorna o cache
        if( cache != null ) {
            return new JSONObject(sharedPref.getString(mPathLogin, (new JSONObject()).toString()));
        }
        return null;
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


    // Reading file paths from SDCard
    public ArrayList<String> getFilePaths() {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(
                android.os.Environment.getExternalStorageDirectory()
                        + File.separator + AppConstant.PHOTO_ALBUM);

        // check for directory
        if (directory.isDirectory()) {
            // getting list of file paths
            File[] listFiles = directory.listFiles();

            // Check for count
            if (listFiles.length > 0) {

                // loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        filePaths.add(filePath);
                    }
                }
            } else {
                // image directory is empty
                Toast.makeText(
                        mContext,
                        AppConstant.PHOTO_ALBUM
                                + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setTitle("Error!");
            alert.setMessage(AppConstant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    // Check supported file extensions
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (AppConstant.FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

    /*
     * getting screen width
     */
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
