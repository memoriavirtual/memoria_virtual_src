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
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Diagnostico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.DisponibilidadeUsoProtecao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.HistoricoProcedencia;
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
        map.put(mContext.getString(R.string.atributo_externo), bemPatrimonial.isExterno());
        map.put(mContext.getString(R.string.atributo_tipo), bemPatrimonial.getTipoBemPatrimonial());
        map.put(mContext.getString(R.string.atributo_num_registro), bemPatrimonial.getNumeroRegistro());
        map.put(mContext.getString(R.string.atributo_titulo_principal), bemPatrimonial.getTituloPrincipal());
        map.put(mContext.getString(R.string.atributo_complemento), bemPatrimonial.getComplemento());
        map.put(mContext.getString(R.string.atributo_colecao), bemPatrimonial.getColecao());
        map.put(mContext.getString(R.string.atributo_latitude), bemPatrimonial.getLatitude());
        map.put(mContext.getString(R.string.atributo_longitude), bemPatrimonial.getLongitude());

        //TODO: Autor
        //map.put(mContext.getString(R.string.atributo_autoria), bemPatrimonial.getAutorias());

        //Producao
        map.put(mContext.getString(R.string.atributo_local), bemPatrimonial.getProducao().getLocal());
        map.put(mContext.getString(R.string.atributo_ano), bemPatrimonial.getProducao().getAno());
        map.put(mContext.getString(R.string.atributo_edicao), bemPatrimonial.getProducao().getEdicao());
        map.put(mContext.getString(R.string.atributo_outras_responsabilidades), bemPatrimonial.getProducao().getOutrasResponsabilidades());

        //Descricao
        map.put(mContext.getString(R.string.atributo_caracteristicas), bemPatrimonial.getCaracteristicasFisicasTecnicasExecutivas());
        map.put(mContext.getString(R.string.atributo_dimensoes_quantificacoes), bemPatrimonial.getDimensoesQuantificacoes());
        map.put(mContext.getString(R.string.atributo_condicao_topografica), bemPatrimonial.getCondicaoTopografica());
        map.put(mContext.getString(R.string.atributo_uso), bemPatrimonial.getUso());
        map.put(mContext.getString(R.string.atributo_num_ambientes), bemPatrimonial.getNumeroAmbientes());
        map.put(mContext.getString(R.string.atributo_num_pavimentos), bemPatrimonial.getNumeroPavimentos());

        map.put(mContext.getString(R.string.atributo_alcova), bemPatrimonial.isAlcova());
        map.put(mContext.getString(R.string.atributo_porao), bemPatrimonial.isPorao());
        map.put(mContext.getString(R.string.atributo_sotao), bemPatrimonial.isSotao());

        map.put(mContext.getString(R.string.atributo_meio_antropico), bemPatrimonial.getMeioAntropico());
        map.put(mContext.getString(R.string.atributo_carac_ambientais), bemPatrimonial.getCaracteristicasAmbientais());
        map.put(mContext.getString(R.string.atributo_sitio_paisagem), bemPatrimonial.getSitioPaisagem());
        map.put(mContext.getString(R.string.atributo_agua_proxima), bemPatrimonial.getAguaProxima());
        map.put(mContext.getString(R.string.atributo_vegetacao), bemPatrimonial.getVegetacao());
        map.put(mContext.getString(R.string.atributo_exposicao), bemPatrimonial.getExposicao());
        map.put(mContext.getString(R.string.atributo_uso_atual), bemPatrimonial.getUsoAtual());
        map.put(mContext.getString(R.string.atributo_outros), bemPatrimonial.getOutros());
        map.put(mContext.getString(R.string.atributo_area_total), bemPatrimonial.getAreaTotal());
        map.put(mContext.getString(R.string.atributo_altura_fachada_frontal), bemPatrimonial.getAlturaFachadaFrontal());
        map.put(mContext.getString(R.string.atributo_altura_fachada_posterior), bemPatrimonial.getAlturaFachadaPosterior());
        map.put(mContext.getString(R.string.atributo_largura), bemPatrimonial.getLargura());
        map.put(mContext.getString(R.string.atributo_altura), bemPatrimonial.getAltura());
        map.put(mContext.getString(R.string.atributo_profundidade), bemPatrimonial.getProfundidade());

        map.put(mContext.getString(R.string.atributo_altura_cumeeira), bemPatrimonial.getAlturaCumeeira());
        map.put(mContext.getString(R.string.atributo_altura_total), bemPatrimonial.getAlturaTotal());
        map.put(mContext.getString(R.string.atributo_pe_direito_terreo), bemPatrimonial.getPeDireitoTerreo());
        map.put(mContext.getString(R.string.atributo_tipo_pe_direito), bemPatrimonial.getPeDireitoTipo());
        map.put(mContext.getString(R.string.atributo_comprimento), bemPatrimonial.getComprimento());
        map.put(mContext.getString(R.string.atributo_localizacao_fisica), bemPatrimonial.getLocalizacaoFisica());
        map.put(mContext.getString(R.string.atributo_conteudo), bemPatrimonial.getConteudo());
        map.put(mContext.getString(R.string.atributo_meio_acesso), bemPatrimonial.getMeioAcesso());
        map.put(mContext.getString(R.string.atributo_notas), bemPatrimonial.getNotas());

        //Estado
        map.put(mContext.getString(R.string.atributo_estado_preservacao), bemPatrimonial.getDiagnostico().getEstadoPreservacao());
        map.put(mContext.getString(R.string.atributo_estado_convervacao), bemPatrimonial.getDiagnostico().getEstadoConservacao());
        map.put(mContext.getString(R.string.atributo_notas_estado_convervacao), bemPatrimonial.getDiagnostico().getNotasEstadoConservacao());

        //Disponibilidadget
        map.put(mContext.getString(R.string.atributo_condicao_reproducao), bemPatrimonial.getDisponibilidadeUsoProtecao().getCondicoesReproducao());
        map.put(mContext.getString(R.string.atributo_protecao), bemPatrimonial.getDisponibilidadeUsoProtecao().getProtecao());
        //map.put(mContext.getString(R.string.atributo_numero_processo), bemPatrimonial.getDisponibilidadeUsoProtecao().getpro());
        map.put(mContext.getString(R.string.atributo_data_retorno), bemPatrimonial.getDisponibilidadeUsoProtecao().getDataRetorno());
        map.put(mContext.getString(R.string.atributo_notas_uso_aproveitamento), bemPatrimonial.getDisponibilidadeUsoProtecao().getNotasUsoAproveitamento());

        //Procedencia
        map.put(mContext.getString(R.string.atributo_tipo_aquisicao), bemPatrimonial.getHistoricoProcedencia().getTipoAquisicao());
        map.put(mContext.getString(R.string.atributo_valor_venal), bemPatrimonial.getHistoricoProcedencia().getValorVenalTransacao());
        map.put(mContext.getString(R.string.atributo_data), bemPatrimonial.getHistoricoProcedencia().getDataAquisicao());
        map.put(mContext.getString(R.string.atributo_primeiro_proprietario), bemPatrimonial.getHistoricoProcedencia().getPrimeiroProprietario());
        map.put(mContext.getString(R.string.atributo_dados_transacao), bemPatrimonial.getHistoricoProcedencia().getDadosDocTransacao());
        map.put(mContext.getString(R.string.atributo_historico), bemPatrimonial.getHistoricoProcedencia().getHistorico());
        map.put(mContext.getString(R.string.atributo_instrumento_pesquisa), bemPatrimonial.getHistoricoProcedencia().getInstrumentoPesquisa());

        //TODO: Assuntos
        /*
        map.put(mContext.getString(R.string.atributo_assuntos), bemPatrimonial.getCadastro_assuntos());
        map.put(mContext.getString(R.string.atributo_descritores), bemPatrimonial.getCadastro_descritores());
        map.put(mContext.getString(R.string.atributo_fontes_informacao), bemPatrimonial.getCadastro_fontes_informacao());
        map.put(mContext.getString(R.string.atributo_pesquisadores), bemPatrimonial.getCadastro_pesquisadores());
        map.put(mContext.getString(R.string.atributo_relacionar_outros_bens), bemPatrimonial.getCadastro_relacionar_outros_bens());
        */

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
                    //TODO: PROBLEMA NO PARSE
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

        BemPatrimonial mBemPatrimonial = new BemPatrimonial();
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

        //bp.setContainerMultimidia(containerMultimidia);

        //Informações gerais
        mBemPatrimonial.setExterno(Utils.safeBoolean(jsonObject, resources.getString(R.string.atributo_externo)));
        mBemPatrimonial.setTipoBemPatrimonial(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));

        mBemPatrimonial.setNumeroRegistro(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setTituloPrincipal(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setComplemento(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setColecao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setLatitude(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setLongitude(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));

        //TODO: Autor
        //mBemPatrimonial.setAutorias();

        //Producao
        Producao producao = new Producao();
        producao.setLocal(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        producao.setAno(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        producao.setEdicao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        producao.setOutrasResponsabilidades(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setProducao(producao);

        //Descricao
        mBemPatrimonial.setCaracteristicasFisicasTecnicasExecutivas(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setDimensoesQuantificacoes(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setCondicaoTopografica(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setUso(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setNumeroAmbientes(Utils.safeInteger(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setNumeroPavimentos(Utils.safeInteger(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAlcova(Utils.safeBoolean(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setPorao(Utils.safeBoolean(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setSotao(Utils.safeBoolean(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setMeioAntropico(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setCaracteristicasAmbientais(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setSitioPaisagem(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAguaProxima(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setVegetacao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setExposicao(BemPatrimonial.Exposicao.aberto);
        mBemPatrimonial.setUso(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setOutros(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAreaTotal(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAlturaFachadaFrontal(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAlturaFachadaPosterior(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setLargura(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAltura(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setProfundidade(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAlturaCumeeira(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setAlturaTotal(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setPeDireitoTerreo(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setPeDireitoTipo(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setComprimento(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setLocalizacaoFisica(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setConteudo(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setMeioAcesso(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setNotas(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));

        //Estado
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setEstadoConservacao(Diagnostico.EstadoConservacao.bom);
        diagnostico.setEstadoPreservacao(Diagnostico.EstadoPreservacao.integro);
        diagnostico.setNotasEstadoConservacao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setDiagnostico(diagnostico);

        //Disponibilidade
        DisponibilidadeUsoProtecao disponibilidade = new DisponibilidadeUsoProtecao();
        //TODO: trocar dados fixos pelos dados que usuário entrou
        disponibilidade.setCondicoesAcesso(DisponibilidadeUsoProtecao.CondicoesAcesso.livre);
        disponibilidade.setCondicoesReproducao(DisponibilidadeUsoProtecao.CondicoesReproducao.nao);
        //disponibilidade.setCadastro_numero_processo(cadastro_numero_processo.getText().toString()); // Nao existe no modelo
        disponibilidade.setProtecao(DisponibilidadeUsoProtecao.Protecao.sim);
        disponibilidade.setDataRetorno(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        disponibilidade.setNotasUsoAproveitamento(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setDisponibilidadeUsoProtecao(disponibilidade);

        //Procedencia
        HistoricoProcedencia historico = new HistoricoProcedencia();
        historico.setTipoAquisicao(HistoricoProcedencia.TipoAquisicao.doacaoInstitucional);
        historico.setValorVenalTransacao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        historico.setDataAquisicao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        historico.setPrimeiroProprietario(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        historico.setDadosDocTransacao(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        historico.setInstrumentoPesquisa(Utils.safeString(jsonObject, resources.getString(R.string.atributo_tipo)));
        mBemPatrimonial.setHistoricoProcedencia(historico);

        //TODO: Assuntos
        /*
        mBemPatrimonial.setAssuntos(cadastro_assuntos.getText().toString());
        mBemPatrimonial.setDescritores(cadastro_descritores.getText().toString());
        mBemPatrimonial.setFontesInformacao(cadastro_fontes_informacao.getText().toString());
        mBemPatrimonial.setPesquisadores(cadastro_pesquisadores.getText().toString());
        mBemPatrimonial.setBensRelacionados(cadastro_relacionar_outros_bens.getText().toString());
        */

        return mBemPatrimonial;
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

    public static Integer safeInteger(JSONObject json, String string) throws JSONException {
        if (json.has(string)){
            return json.getInt(string);
        }
        else{
            return 0;
        }
    }

    public static Boolean safeBoolean(JSONObject json, String string) throws JSONException {
        if (json.has(string)){
            return json.getBoolean(string);
        }
        else{
            return false;
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
