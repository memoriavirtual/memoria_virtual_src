package mobile.memoriavirtual.usp.mvmobile.Model;

import android.content.res.Resources;
import android.text.Editable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;
import mobile.memoriavirtual.usp.mvmobile.R;

/**
 * Created by User on 09/02/2015.
 */
public class BemPatrimonial {

    //Informações gerais
    private String cadastro_externo;
    private String cadastro_tipo;
    private String cadastro_num_registro;
    private String cadastro_titulo_principal;
    private String cadastro_complemento;
    private String cadastro_colecao;
    private String cadastro_latitude;
    private String cadastro_longitude;

    //Autor
    private String cadastro_autoria;

    //Produção
    private String cadastro_local;
    private String cadastro_ano;
    private String cadastro_edicao;
    private String cadastro_outras_responsabilidades;

    //Descrição
    private String cadastro_caracteristicas;
    private String cadastro_dimensoes_quantificacoes;
    private String cadastro_condicao_topografica;
    private String cadastro_uso;
    private String cadastro_num_ambientes;
    private String cadastro_num_pavimentos;
    private String cadastro_alcova;
    private String cadastro_porao;
    private String cadastro_sotao;
    private String cadastro_meio_antropico;
    private String cadastro_carac_ambientais;
    private String cadastro_sitio_paisagem;
    private String cadastro_agua_proxima;
    private String cadastro_vegetacao;
    private String cadastro_exposicao;
    private String cadastro_uso_atual;
    private String cadastro_outros;
    private String cadastro_area_total;
    private String cadastro_altura_fachada_frontal;
    private String cadastro_altura_fachada_posterior;
    private String cadastro_largura;
    private String cadastro_altura;
    private String cadastro_profundidade;
    private String cadastro_altura_cumeeira;
    private String cadastro_altura_total;
    private String cadastro_pe_direito_terreo;
    private String cadastro_tipo_pe_direito;
    private String cadastro_comprimento;
    private String cadastro_localizacao_fisica;
    private String cadastro_conteudo;
    private String cadastro_meio_acesso;
    private String cadastro_notas;

    //Estado
    private String cadastro_estado_preservacao;
    private String cadastro_estado_convervacao;
    private String cadastro_notas_estado_convervacao;

    //Disponibilidade
    private String cadastro_disponibilidade;
    private String cadastro_condicao_acesso;
    private String cadastro_condicao_reproducao;
    private String cadastro_protecao;
    private String cadastro_numero_processo;
    private String cadastro_data_retorno;
    private String cadastro_notas_uso_aproveitamento;

    //Procedência
    private String cadastro_tipo_aquisicao;
    private String cadastro_valor_venal;
    private String cadastro_data;
    private String cadastro_primeiro_proprietario;
    private String cadastro_dados_transacao;
    private String cadastro_historico;
    private String cadastro_instrumento_pesquisa;

    //Outros
    private String cadastro_assuntos;
    private String cadastro_descritores;
    private String cadastro_fontes_informacao;
    private String cadastro_pesquisadores;
    private String cadastro_relacionar_outros_bens;

    public BemPatrimonial() {
    }


    public static BemPatrimonial parseBemPatrimonial(JSONObject jsonObject) throws JSONException {
        BemPatrimonial bp = new BemPatrimonial();

        Resources resources = Utils.getContext().getResources();

        //Informações gerais
        bp.cadastro_externo = Utils.safeString(jsonObject,resources.getString(R.string.cadastro_externo));
        bp.cadastro_tipo = Utils.safeString(jsonObject,resources.getString(R.string.cadastro_tipo));
        bp.cadastro_num_registro = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_num_registro));
        bp.cadastro_titulo_principal = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_titulo_principal));
        bp.cadastro_complemento = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_complemento));
        bp.cadastro_colecao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_colecao));
        bp.cadastro_latitude = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_latitude));
        bp.cadastro_longitude = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_longitude));

        //Autor
        bp.cadastro_autoria = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_autoria));

        //Produção
        bp.cadastro_local = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_local));
        bp.cadastro_ano = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_ano));
        bp.cadastro_edicao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_edicao));
        bp.cadastro_outras_responsabilidades = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_outras_responsabilidades));

        //Descrição
        bp.cadastro_caracteristicas = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_caracteristicas));
        bp.cadastro_dimensoes_quantificacoes = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_dimensoes_quantificacoes));
        bp.cadastro_condicao_topografica = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_condicao_topografica));
        bp.cadastro_uso = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_uso));
        bp.cadastro_num_ambientes = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_num_ambientes));
        bp.cadastro_num_pavimentos = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_num_pavimentos));
        bp.cadastro_alcova = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_alcova));
        bp.cadastro_porao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_porao));
        bp.cadastro_sotao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_sotao));
        bp.cadastro_meio_antropico = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_meio_antropico));
        bp.cadastro_carac_ambientais = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_carac_ambientais));
        bp.cadastro_sitio_paisagem = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_sitio_paisagem));
        bp.cadastro_agua_proxima = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_agua_proxima));
        bp.cadastro_vegetacao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_vegetacao));
        bp.cadastro_exposicao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_exposicao));
        bp.cadastro_uso_atual = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_uso_atual));
        bp.cadastro_outros = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_outros));
        bp.cadastro_area_total = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_area_total));
        bp.cadastro_altura_fachada_frontal = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_altura_fachada_frontal));
        bp.cadastro_altura_fachada_posterior = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_altura_fachada_posterior));
        bp.cadastro_largura = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_largura));
        bp.cadastro_altura = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_altura));
        bp.cadastro_profundidade = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_profundidade));
        bp.cadastro_altura_cumeeira = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_altura_cumeeira));
        bp.cadastro_altura_total = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_altura_total));
        bp.cadastro_pe_direito_terreo = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_pe_direito_terreo));
        bp.cadastro_tipo_pe_direito = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_tipo_pe_direito));
        bp.cadastro_comprimento = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_comprimento));
        bp.cadastro_localizacao_fisica = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_localizacao_fisica));
        bp.cadastro_conteudo = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_conteudo));
        bp.cadastro_meio_acesso = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_meio_acesso));
        bp.cadastro_notas = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_notas));

        //Estado
        bp.cadastro_estado_preservacao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_estado_preservacao));
        bp.cadastro_estado_convervacao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_estado_convervacao));
        bp.cadastro_notas_estado_convervacao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_notas_estado_convervacao));

        //Disponibilidade
        bp.cadastro_disponibilidade = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_disponibilidade));
        bp.cadastro_condicao_acesso = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_condicao_acesso));
        bp.cadastro_condicao_reproducao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_condicao_reproducao));
        bp.cadastro_protecao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_protecao));
        bp.cadastro_numero_processo = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_numero_processo));
        bp.cadastro_data_retorno = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_data_retorno));
        bp.cadastro_notas_uso_aproveitamento = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_notas_uso_aproveitamento));

        //Procedência
        bp.cadastro_tipo_aquisicao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_tipo_aquisicao));
        bp.cadastro_valor_venal = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_valor_venal));
        bp.cadastro_data = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_data));
        bp.cadastro_primeiro_proprietario = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_primeiro_proprietario));
        bp.cadastro_dados_transacao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_dados_transacao));
        bp.cadastro_historico = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_historico));
        bp.cadastro_instrumento_pesquisa = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_instrumento_pesquisa));

        //Outros
        bp.cadastro_assuntos = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_assuntos));
        bp.cadastro_descritores = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_descritores));
        bp.cadastro_fontes_informacao = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_fontes_informacao));
        bp.cadastro_pesquisadores = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_pesquisadores));
        bp.cadastro_relacionar_outros_bens = Utils.safeString(jsonObject, resources.getString(R.string.cadastro_relacionar_outros_bens));

        return bp;
    }


    public String getCadastro_externo() {
        return cadastro_externo;
    }

    public void setCadastro_externo(String cadastro_externo) {
        this.cadastro_externo = cadastro_externo;
    }

    public String getCadastro_tipo() {
        return cadastro_tipo;
    }

    public void setCadastro_tipo(String cadastro_tipo) {
        this.cadastro_tipo = cadastro_tipo;
    }

    public String getCadastro_num_registro() {
        return cadastro_num_registro;
    }

    public void setCadastro_num_registro(String cadastro_num_registro) {
        this.cadastro_num_registro = cadastro_num_registro;
    }

    public String getCadastro_titulo_principal() {
        return cadastro_titulo_principal;
    }

    public void setCadastro_titulo_principal(String cadastro_titulo_principal) {
        this.cadastro_titulo_principal = cadastro_titulo_principal;
    }

    public String getCadastro_complemento() {
        return cadastro_complemento;
    }

    public void setCadastro_complemento(String cadastro_complemento) {
        this.cadastro_complemento = cadastro_complemento;
    }

    public String getCadastro_colecao() {
        return cadastro_colecao;
    }

    public void setCadastro_colecao(String cadastro_colecao) {
        this.cadastro_colecao = cadastro_colecao;
    }

    public String getCadastro_latitude() {
        return cadastro_latitude;
    }

    public void setCadastro_latitude(String cadastro_latitude) {
        this.cadastro_latitude = cadastro_latitude;
    }

    public String getCadastro_longitude() {
        return cadastro_longitude;
    }

    public void setCadastro_longitude(String cadastro_longitude) {
        this.cadastro_longitude = cadastro_longitude;
    }

    public String getCadastro_autoria() {
        return cadastro_autoria;
    }

    public void setCadastro_autoria(String cadastro_autoria) {
        this.cadastro_autoria = cadastro_autoria;
    }

    public String getCadastro_local() {
        return cadastro_local;
    }

    public void setCadastro_local(String cadastro_local) {
        this.cadastro_local = cadastro_local;
    }

    public String getCadastro_ano() {
        return cadastro_ano;
    }

    public void setCadastro_ano(String cadastro_ano) {
        this.cadastro_ano = cadastro_ano;
    }

    public String getCadastro_edicao() {
        return cadastro_edicao;
    }

    public void setCadastro_edicao(String cadastro_edicao) {
        this.cadastro_edicao = cadastro_edicao;
    }

    public String getCadastro_outras_responsabilidades() {
        return cadastro_outras_responsabilidades;
    }

    public void setCadastro_outras_responsabilidades(String cadastro_outras_responsabilidades) {
        this.cadastro_outras_responsabilidades = cadastro_outras_responsabilidades;
    }

    public String getCadastro_caracteristicas() {
        return cadastro_caracteristicas;
    }

    public void setCadastro_caracteristicas(String cadastro_caracteristicas) {
        this.cadastro_caracteristicas = cadastro_caracteristicas;
    }

    public String getCadastro_dimensoes_quantificacoes() {
        return cadastro_dimensoes_quantificacoes;
    }

    public void setCadastro_dimensoes_quantificacoes(String cadastro_dimensoes_quantificacoes) {
        this.cadastro_dimensoes_quantificacoes = cadastro_dimensoes_quantificacoes;
    }

    public String getCadastro_condicao_topografica() {
        return cadastro_condicao_topografica;
    }

    public void setCadastro_condicao_topografica(String cadastro_condicao_topografica) {
        this.cadastro_condicao_topografica = cadastro_condicao_topografica;
    }

    public String getCadastro_uso() {
        return cadastro_uso;
    }

    public void setCadastro_uso(String cadastro_uso) {
        this.cadastro_uso = cadastro_uso;
    }

    public String getCadastro_num_ambientes() {
        return cadastro_num_ambientes;
    }

    public void setCadastro_num_ambientes(String cadastro_num_ambientes) {
        this.cadastro_num_ambientes = cadastro_num_ambientes;
    }

    public String getCadastro_num_pavimentos() {
        return cadastro_num_pavimentos;
    }

    public void setCadastro_num_pavimentos(String cadastro_num_pavimentos) {
        this.cadastro_num_pavimentos = cadastro_num_pavimentos;
    }

    public String getCadastro_alcova() {
        return cadastro_alcova;
    }

    public void setCadastro_alcova(String cadastro_alcova) {
        this.cadastro_alcova = cadastro_alcova;
    }

    public String getCadastro_porao() {
        return cadastro_porao;
    }

    public void setCadastro_porao(String cadastro_porao) {
        this.cadastro_porao = cadastro_porao;
    }

    public String getCadastro_sotao() {
        return cadastro_sotao;
    }

    public void setCadastro_sotao(String cadastro_sotao) {
        this.cadastro_sotao = cadastro_sotao;
    }

    public String getCadastro_meio_antropico() {
        return cadastro_meio_antropico;
    }

    public void setCadastro_meio_antropico(String cadastro_meio_antropico) {
        this.cadastro_meio_antropico = cadastro_meio_antropico;
    }

    public String getCadastro_carac_ambientais() {
        return cadastro_carac_ambientais;
    }

    public void setCadastro_carac_ambientais(String cadastro_carac_ambientais) {
        this.cadastro_carac_ambientais = cadastro_carac_ambientais;
    }

    public String getCadastro_sitio_paisagem() {
        return cadastro_sitio_paisagem;
    }

    public void setCadastro_sitio_paisagem(String cadastro_sitio_paisagem) {
        this.cadastro_sitio_paisagem = cadastro_sitio_paisagem;
    }

    public String getCadastro_agua_proxima() {
        return cadastro_agua_proxima;
    }

    public void setCadastro_agua_proxima(String cadastro_agua_proxima) {
        this.cadastro_agua_proxima = cadastro_agua_proxima;
    }

    public String getCadastro_vegetacao() {
        return cadastro_vegetacao;
    }

    public void setCadastro_vegetacao(String cadastro_vegetacao) {
        this.cadastro_vegetacao = cadastro_vegetacao;
    }

    public String getCadastro_exposicao() {
        return cadastro_exposicao;
    }

    public void setCadastro_exposicao(String cadastro_exposicao) {
        this.cadastro_exposicao = cadastro_exposicao;
    }

    public String getCadastro_uso_atual() {
        return cadastro_uso_atual;
    }

    public void setCadastro_uso_atual(String cadastro_uso_atual) {
        this.cadastro_uso_atual = cadastro_uso_atual;
    }

    public String getCadastro_outros() {
        return cadastro_outros;
    }

    public void setCadastro_outros(String cadastro_outros) {
        this.cadastro_outros = cadastro_outros;
    }

    public String getCadastro_area_total() {
        return cadastro_area_total;
    }

    public void setCadastro_area_total(String cadastro_area_total) {
        this.cadastro_area_total = cadastro_area_total;
    }

    public String getCadastro_altura_fachada_frontal() {
        return cadastro_altura_fachada_frontal;
    }

    public void setCadastro_altura_fachada_frontal(String cadastro_altura_fachada_frontal) {
        this.cadastro_altura_fachada_frontal = cadastro_altura_fachada_frontal;
    }

    public String getCadastro_altura_fachada_posterior() {
        return cadastro_altura_fachada_posterior;
    }

    public void setCadastro_altura_fachada_posterior(String cadastro_altura_fachada_posterior) {
        this.cadastro_altura_fachada_posterior = cadastro_altura_fachada_posterior;
    }

    public String getCadastro_largura() {
        return cadastro_largura;
    }

    public void setCadastro_largura(String cadastro_largura) {
        this.cadastro_largura = cadastro_largura;
    }

    public String getCadastro_altura() {
        return cadastro_altura;
    }

    public void setCadastro_altura(String cadastro_altura) {
        this.cadastro_altura = cadastro_altura;
    }

    public String getCadastro_profundidade() {
        return cadastro_profundidade;
    }

    public void setCadastro_profundidade(String cadastro_profundidade) {
        this.cadastro_profundidade = cadastro_profundidade;
    }

    public String getCadastro_altura_cumeeira() {
        return cadastro_altura_cumeeira;
    }

    public void setCadastro_altura_cumeeira(String cadastro_altura_cumeeira) {
        this.cadastro_altura_cumeeira = cadastro_altura_cumeeira;
    }

    public String getCadastro_altura_total() {
        return cadastro_altura_total;
    }

    public void setCadastro_altura_total(String cadastro_altura_total) {
        this.cadastro_altura_total = cadastro_altura_total;
    }

    public String getCadastro_pe_direito_terreo() {
        return cadastro_pe_direito_terreo;
    }

    public void setCadastro_pe_direito_terreo(String cadastro_pe_direito_terreo) {
        this.cadastro_pe_direito_terreo = cadastro_pe_direito_terreo;
    }

    public String getCadastro_tipo_pe_direito() {
        return cadastro_tipo_pe_direito;
    }

    public void setCadastro_tipo_pe_direito(String cadastro_tipo_pe_direito) {
        this.cadastro_tipo_pe_direito = cadastro_tipo_pe_direito;
    }

    public String getCadastro_comprimento() {
        return cadastro_comprimento;
    }

    public void setCadastro_comprimento(String cadastro_comprimento) {
        this.cadastro_comprimento = cadastro_comprimento;
    }

    public String getCadastro_localizacao_fisica() {
        return cadastro_localizacao_fisica;
    }

    public void setCadastro_localizacao_fisica(String cadastro_localizacao_fisica) {
        this.cadastro_localizacao_fisica = cadastro_localizacao_fisica;
    }

    public String getCadastro_conteudo() {
        return cadastro_conteudo;
    }

    public void setCadastro_conteudo(String cadastro_conteudo) {
        this.cadastro_conteudo = cadastro_conteudo;
    }

    public String getCadastro_meio_acesso() {
        return cadastro_meio_acesso;
    }

    public void setCadastro_meio_acesso(String cadastro_meio_acesso) {
        this.cadastro_meio_acesso = cadastro_meio_acesso;
    }

    public String getCadastro_notas() {
        return cadastro_notas;
    }

    public void setCadastro_notas(String cadastro_notas) {
        this.cadastro_notas = cadastro_notas;
    }

    public String getCadastro_estado_preservacao() {
        return cadastro_estado_preservacao;
    }

    public void setCadastro_estado_preservacao(String cadastro_estado_preservacao) {
        this.cadastro_estado_preservacao = cadastro_estado_preservacao;
    }

    public String getCadastro_estado_convervacao() {
        return cadastro_estado_convervacao;
    }

    public void setCadastro_estado_convervacao(String cadastro_estado_convervacao) {
        this.cadastro_estado_convervacao = cadastro_estado_convervacao;
    }

    public String getCadastro_notas_estado_convervacao() {
        return cadastro_notas_estado_convervacao;
    }

    public void setCadastro_notas_estado_convervacao(String cadastro_notas_estado_convervacao) {
        this.cadastro_notas_estado_convervacao = cadastro_notas_estado_convervacao;
    }

    public String getCadastro_disponibilidade() {
        return cadastro_disponibilidade;
    }

    public void setCadastro_disponibilidade(String cadastro_disponibilidade) {
        this.cadastro_disponibilidade = cadastro_disponibilidade;
    }

    public String getCadastro_condicao_acesso() {
        return cadastro_condicao_acesso;
    }

    public void setCadastro_condicao_acesso(String cadastro_condicao_acesso) {
        this.cadastro_condicao_acesso = cadastro_condicao_acesso;
    }

    public String getCadastro_condicao_reproducao() {
        return cadastro_condicao_reproducao;
    }

    public void setCadastro_condicao_reproducao(String cadastro_condicao_reproducao) {
        this.cadastro_condicao_reproducao = cadastro_condicao_reproducao;
    }

    public String getCadastro_protecao() {
        return cadastro_protecao;
    }

    public void setCadastro_protecao(String cadastro_protecao) {
        this.cadastro_protecao = cadastro_protecao;
    }

    public String getCadastro_numero_processo() {
        return cadastro_numero_processo;
    }

    public void setCadastro_numero_processo(String cadastro_numero_processo) {
        this.cadastro_numero_processo = cadastro_numero_processo;
    }

    public String getCadastro_data_retorno() {
        return cadastro_data_retorno;
    }

    public void setCadastro_data_retorno(String cadastro_data_retorno) {
        this.cadastro_data_retorno = cadastro_data_retorno;
    }

    public String getCadastro_notas_uso_aproveitamento() {
        return cadastro_notas_uso_aproveitamento;
    }

    public void setCadastro_notas_uso_aproveitamento(String cadastro_notas_uso_aproveitamento) {
        this.cadastro_notas_uso_aproveitamento = cadastro_notas_uso_aproveitamento;
    }

    public String getCadastro_tipo_aquisicao() {
        return cadastro_tipo_aquisicao;
    }

    public void setCadastro_tipo_aquisicao(String cadastro_tipo_aquisicao) {
        this.cadastro_tipo_aquisicao = cadastro_tipo_aquisicao;
    }

    public String getCadastro_valor_venal() {
        return cadastro_valor_venal;
    }

    public void setCadastro_valor_venal(String cadastro_valor_venal) {
        this.cadastro_valor_venal = cadastro_valor_venal;
    }

    public String getCadastro_data() {
        return cadastro_data;
    }

    public void setCadastro_data(String cadastro_data) {
        this.cadastro_data = cadastro_data;
    }

    public String getCadastro_primeiro_proprietario() {
        return cadastro_primeiro_proprietario;
    }

    public void setCadastro_primeiro_proprietario(String cadastro_primeiro_proprietario) {
        this.cadastro_primeiro_proprietario = cadastro_primeiro_proprietario;
    }

    public String getCadastro_dados_transacao() {
        return cadastro_dados_transacao;
    }

    public void setCadastro_dados_transacao(String cadastro_dados_transacao) {
        this.cadastro_dados_transacao = cadastro_dados_transacao;
    }

    public String getCadastro_historico() {
        return cadastro_historico;
    }

    public void setCadastro_historico(String cadastro_historico) {
        this.cadastro_historico = cadastro_historico;
    }

    public String getCadastro_instrumento_pesquisa() {
        return cadastro_instrumento_pesquisa;
    }

    public void setCadastro_instrumento_pesquisa(String cadastro_instrumento_pesquisa) {
        this.cadastro_instrumento_pesquisa = cadastro_instrumento_pesquisa;
    }

    public String getCadastro_assuntos() {
        return cadastro_assuntos;
    }

    public void setCadastro_assuntos(String cadastro_assuntos) {
        this.cadastro_assuntos = cadastro_assuntos;
    }

    public String getCadastro_descritores() {
        return cadastro_descritores;
    }

    public void setCadastro_descritores(String cadastro_descritores) {
        this.cadastro_descritores = cadastro_descritores;
    }

    public String getCadastro_fontes_informacao() {
        return cadastro_fontes_informacao;
    }

    public void setCadastro_fontes_informacao(String cadastro_fontes_informacao) {
        this.cadastro_fontes_informacao = cadastro_fontes_informacao;
    }

    public String getCadastro_pesquisadores() {
        return cadastro_pesquisadores;
    }

    public void setCadastro_pesquisadores(String cadastro_pesquisadores) {
        this.cadastro_pesquisadores = cadastro_pesquisadores;
    }

    public String getCadastro_relacionar_outros_bens() {
        return cadastro_relacionar_outros_bens;
    }

    public void setCadastro_relacionar_outros_bens(String cadastro_relacionar_outros_bens) {
        this.cadastro_relacionar_outros_bens = cadastro_relacionar_outros_bens;
    }
}
