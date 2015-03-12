package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormAssuntos;
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

public class AddBemPatrimonialActivity extends ActionBarActivity implements FormMidia.OnFragmentInteractionListener,FormInformacoes.OnFragmentInteractionListener, FormAutor.OnFragmentInteractionListener,FormProducao.OnFragmentInteractionListener,FormDescricao.OnFragmentInteractionListener,FormEstado.OnFragmentInteractionListener,FormDisponibilidade.OnFragmentInteractionListener,FormProcedencia.OnFragmentInteractionListener,FormAssuntos.OnFragmentInteractionListener,FormOutros.OnFragmentInteractionListener {

    AddBemPatrimonialAdapter mAdapter;
    ViewPager mPager;

    EditText tipo;
    EditText numRegistro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bem_patrimonial);

        mAdapter = new AddBemPatrimonialAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(10); //nao destroi as fragments escondidos
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

                        //Referencia editTexts
                        tipo = (EditText) findViewById(R.id.cadastro_tipo);
                        numRegistro = (EditText)findViewById(R.id.cadastro_num_registro);

                        BemPatrimonial bemPatrimonial = new BemPatrimonial(tipo.getText().toString(),numRegistro.getText().toString());

                        try {
                            Utils.salvarBemPatrimonialCache(bemPatrimonial);
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

    private void nextTouched()
    {
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }



}
