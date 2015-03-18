package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;

import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormAutor;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormDescricao;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormDisponibilidade;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormEstado;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormInformacoes;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormMidia;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormOutros;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormProcedencia;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormProducao;
import mobile.memoriavirtual.usp.mvmobile.Model.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;

public class AddBemPatrimonialActivity extends ActionBarActivity implements FormMidia.OnFragmentInteractionListener,FormInformacoes.OnFragmentInteractionListener, FormAutor.OnFragmentInteractionListener,FormProducao.OnFragmentInteractionListener,FormDescricao.OnFragmentInteractionListener,FormEstado.OnFragmentInteractionListener,FormDisponibilidade.OnFragmentInteractionListener,FormProcedencia.OnFragmentInteractionListener,FormOutros.OnFragmentInteractionListener {

    AddBemPatrimonialAdapter mAdapter;
    ViewPager mPager;
    BemPatrimonial mBemPatrimonial;

    //Informações gerais
    CheckBox cadastro_externo;
    EditText cadastro_tipo;
    EditText cadastro_num_registro;
    EditText cadastro_titulo_principal;
    EditText cadastro_complemento;
    EditText cadastro_colecao;
    EditText cadastro_latitude;
    EditText cadastro_longitude;

    //Autor
    EditText cadastro_autoria;

    //Produção
    EditText cadastro_local;
    EditText cadastro_ano;
    EditText cadastro_edicao;
    EditText cadastro_outras_responsabilidades;

    //Descrição
    EditText cadastro_caracteristicas;
    EditText cadastro_dimensoes_quantificacoes;
    EditText cadastro_condicao_topografica;
    EditText cadastro_uso;
    EditText cadastro_num_ambientes;
    EditText cadastro_num_pavimentos;
    CheckBox cadastro_alcova;
    CheckBox cadastro_porao;
    CheckBox cadastro_sotao;
    EditText cadastro_meio_antropico;
    EditText cadastro_carac_ambientais;
    EditText cadastro_sitio_paisagem;
    EditText cadastro_agua_proxima;
    EditText cadastro_vegetacao;
    EditText cadastro_exposicao;
    EditText cadastro_uso_atual;
    EditText cadastro_outros;
    EditText cadastro_area_total;
    EditText cadastro_altura_fachada_frontal;
    EditText cadastro_altura_fachada_posterior;
    EditText cadastro_largura;
    EditText cadastro_altura;
    EditText cadastro_profundidade;
    EditText cadastro_altura_cumeeira;
    EditText cadastro_altura_total;
    EditText cadastro_pe_direito_terreo;
    EditText cadastro_tipo_pe_direito;
    EditText cadastro_comprimento;
    EditText cadastro_localizacao_fisica;
    EditText cadastro_conteudo;
    EditText cadastro_meio_acesso;
    EditText cadastro_notas;

    //Estado
    EditText cadastro_estado_preservacao;
    EditText cadastro_estado_convervacao;
    EditText cadastro_notas_estado_convervacao;

    //Disponibilidade
    EditText cadastro_disponibilidade;
    EditText cadastro_condicao_acesso;
    EditText cadastro_condicao_reproducao;
    EditText cadastro_protecao;
    EditText cadastro_numero_processo;
    EditText cadastro_data_retorno;
    EditText cadastro_notas_uso_aproveitamento;

    //Procedência
    EditText cadastro_tipo_aquisicao;
    EditText cadastro_valor_venal;
    EditText cadastro_data;
    EditText cadastro_primeiro_proprietario;
    EditText cadastro_dados_transacao;
    EditText cadastro_historico;
    EditText cadastro_instrumento_pesquisa;

    //Outros
    EditText cadastro_assuntos;
    EditText cadastro_descritores;
    EditText cadastro_fontes_informacao;
    EditText cadastro_pesquisadores;
    EditText cadastro_relacionar_outros_bens;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bem_patrimonial);

        mAdapter = new AddBemPatrimonialAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(9); //nao destroi as fragments escondidos
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_bem_patrimonial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_bem_patrimonial:
                addTouched();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onFragmentInteraction() {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Acoes de botoes
    public void onAddButtonClick(View v) {
        addTouched();
    }
    public void onNextButtonClick(View v) {
        nextTouched();
    }

    private void addTouched()
    {
        new AlertDialog.Builder(this)
                .setTitle("Deseja mesmo salvar?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        setReferenciasDosCampos();
                        getBemPatrimonialDosCampos();

                        try {
                            Utils.salvarBemPatrimonialCache(mBemPatrimonial);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }


    private void setReferenciasDosCampos()
    {
        //Informações gerais
        cadastro_externo = (CheckBox) findViewById(R.id.cadastro_externo);
        cadastro_tipo = (EditText) findViewById(R.id.cadastro_tipo);
        cadastro_num_registro = (EditText) findViewById(R.id.cadastro_num_registro);
        cadastro_titulo_principal = (EditText) findViewById(R.id.cadastro_titulo_principal);
        cadastro_complemento = (EditText) findViewById(R.id.cadastro_complemento);
        cadastro_colecao = (EditText) findViewById(R.id.cadastro_colecao);
        cadastro_latitude = (EditText) findViewById(R.id.cadastro_latitude);
        cadastro_longitude = (EditText) findViewById(R.id.cadastro_longitude);

        //Autor
        cadastro_autoria = (EditText) findViewById(R.id.cadastro_autoria);

        //Produção

        cadastro_local = (EditText) findViewById(R.id.cadastro_local);
        cadastro_ano = (EditText) findViewById(R.id.cadastro_ano);
        cadastro_edicao = (EditText) findViewById(R.id.cadastro_edicao);
        cadastro_outras_responsabilidades = (EditText) findViewById(R.id.cadastro_outras_responsabilidades);

        //Descrição
        cadastro_caracteristicas = (EditText) findViewById(R.id.cadastro_caracteristicas);
        cadastro_dimensoes_quantificacoes = (EditText) findViewById(R.id.cadastro_dimensoes_quantificacoes);
        cadastro_condicao_topografica = (EditText) findViewById(R.id.cadastro_condicao_topografica);
        cadastro_uso = (EditText) findViewById(R.id.cadastro_uso);
        cadastro_num_ambientes = (EditText) findViewById(R.id.cadastro_num_ambientes);
        cadastro_num_pavimentos = (EditText) findViewById(R.id.cadastro_num_pavimentos);
        cadastro_alcova = (CheckBox) findViewById(R.id.cadastro_alcova);
        cadastro_porao = (CheckBox) findViewById(R.id.cadastro_porao);
        cadastro_sotao = (CheckBox) findViewById(R.id.cadastro_sotao);
        cadastro_meio_antropico = (EditText) findViewById(R.id.cadastro_meio_antropico);
        cadastro_carac_ambientais = (EditText) findViewById(R.id.cadastro_carac_ambientais);
        cadastro_sitio_paisagem = (EditText) findViewById(R.id.cadastro_sitio_paisagem);
        cadastro_agua_proxima = (EditText) findViewById(R.id.cadastro_agua_proxima);
        cadastro_vegetacao = (EditText) findViewById(R.id.cadastro_vegetacao);
        cadastro_exposicao = (EditText) findViewById(R.id.cadastro_exposicao);
        cadastro_uso_atual = (EditText) findViewById(R.id.cadastro_uso_atual);
        cadastro_outros = (EditText) findViewById(R.id.cadastro_outros);
        cadastro_area_total = (EditText) findViewById(R.id.cadastro_area_total);
        cadastro_altura_fachada_frontal = (EditText) findViewById(R.id.cadastro_altura_fachada_frontal);
        cadastro_altura_fachada_posterior = (EditText) findViewById(R.id.cadastro_altura_fachada_posterior);
        cadastro_largura = (EditText) findViewById(R.id.cadastro_largura);
        cadastro_altura = (EditText) findViewById(R.id.cadastro_altura);
        cadastro_profundidade = (EditText) findViewById(R.id.cadastro_profundidade);
        cadastro_altura_cumeeira = (EditText) findViewById(R.id.cadastro_altura_cumeeira);
        cadastro_altura_total = (EditText) findViewById(R.id.cadastro_altura_total);
        cadastro_pe_direito_terreo = (EditText) findViewById(R.id.cadastro_pe_direito_terreo);
        cadastro_tipo_pe_direito = (EditText) findViewById(R.id.cadastro_tipo_pe_direito);
        cadastro_comprimento = (EditText) findViewById(R.id.cadastro_comprimento);
        cadastro_localizacao_fisica = (EditText) findViewById(R.id.cadastro_localizacao_fisica);
        cadastro_conteudo = (EditText) findViewById(R.id.cadastro_conteudo);
        cadastro_meio_acesso = (EditText) findViewById(R.id.cadastro_meio_acesso);
        cadastro_notas = (EditText) findViewById(R.id.cadastro_notas);

        //Estado
        cadastro_estado_preservacao = (EditText) findViewById(R.id.cadastro_estado_preservacao);
        cadastro_estado_convervacao = (EditText) findViewById(R.id.cadastro_estado_convervacao);
        cadastro_notas_estado_convervacao = (EditText) findViewById(R.id.cadastro_notas_estado_convervacao);

        //Disponibilidade
        cadastro_disponibilidade = (EditText) findViewById(R.id.cadastro_disponibilidade);
        cadastro_condicao_acesso = (EditText) findViewById(R.id.cadastro_condicao_acesso);
        cadastro_condicao_reproducao = (EditText) findViewById(R.id.cadastro_condicao_reproducao);
        cadastro_protecao = (EditText) findViewById(R.id.cadastro_protecao);
        cadastro_numero_processo = (EditText) findViewById(R.id.cadastro_numero_processo);
        cadastro_data_retorno = (EditText) findViewById(R.id.cadastro_data_retorno);
        cadastro_notas_uso_aproveitamento = (EditText) findViewById(R.id.cadastro_notas_uso_aproveitamento);

        //Procedência
        cadastro_tipo_aquisicao = (EditText) findViewById(R.id.cadastro_tipo_aquisicao);
        cadastro_valor_venal = (EditText) findViewById(R.id.cadastro_valor_venal);
        cadastro_data = (EditText) findViewById(R.id.cadastro_data);
        cadastro_primeiro_proprietario = (EditText) findViewById(R.id.cadastro_primeiro_proprietario);
        cadastro_dados_transacao = (EditText) findViewById(R.id.cadastro_dados_transacao);
        cadastro_historico = (EditText) findViewById(R.id.cadastro_historico);
        cadastro_instrumento_pesquisa = (EditText) findViewById(R.id.cadastro_instrumento_pesquisa);

        //Outros
        cadastro_assuntos = (EditText) findViewById(R.id.cadastro_assuntos);
        cadastro_descritores = (EditText) findViewById(R.id.cadastro_descritores);
        cadastro_fontes_informacao = (EditText) findViewById(R.id.cadastro_fontes_informacao);
        cadastro_pesquisadores = (EditText) findViewById(R.id.cadastro_pesquisadores);
        cadastro_relacionar_outros_bens = (EditText) findViewById(R.id.cadastro_relacionar_outros_bens);
    }

    private void getBemPatrimonialDosCampos()
    {
        mBemPatrimonial = new BemPatrimonial();
        mBemPatrimonial.setCadastro_externo((cadastro_externo.isSelected())? "true" : "false");
        mBemPatrimonial.setCadastro_tipo(cadastro_tipo.getText().toString());
        mBemPatrimonial.setCadastro_titulo_principal(cadastro_titulo_principal.getText().toString());
    }

    private void nextTouched()
    {
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }



}
