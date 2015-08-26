package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;
import mobile.memoriavirtual.usp.mvmobile.services.BemPatrimonialService;
import mobile.memoriavirtual.usp.mvmobile.services.impl.BemPatrimonialServiceImpl;

public class ServidorActivity extends ActionBarActivity {

    private BemPatrimonialService service;
    private List<Instituicao> mListaInstituicao;

    //UI references
    private EditText mServidorIPText;
    private TextView mStatusText;
    private View mLoginFormView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidor);

        mServidorIPText = (EditText) findViewById(R.id.servidor_numeroIP);
        mStatusText = (TextView) findViewById(R.id.servidor_status);

        mLoginFormView = findViewById(R.id.servidor_login_form);
        mProgressView = findViewById(R.id.servidor_login_progress);

        service = BemPatrimonialServiceImpl.getInstance();

        //Recupera dados de login salvos no cache
        recuperarLoginCache();
    }

    private void recuperarLoginCache(){
        try {
            String servidor = Utils.carregarServidorSalvoCache();
            mServidorIPText.setText(servidor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_servidor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checarServidorOnline(View v) {

        showProgress(true);
        mListaInstituicao = new ArrayList<Instituicao>();
        final String numeroIP = mServidorIPText.getText().toString();
        service.listarInstituicoes(numeroIP, new Response.Listener<List<Instituicao>>() {
                    @Override
                    public void onResponse(List<Instituicao> s) {
                        //Salva no cache
                        try {
                            Utils.salvarServidorCache(numeroIP);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Servidor online
                        mListaInstituicao = s;
                        showProgress(false);
                        mStatusText.setVisibility(View.GONE);
                        openLoginIntent();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Servidor offline
                        showProgress(false);
                        mStatusText.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    public void openLoginIntent(){
        Intent intentLogin = new Intent(this, LoginActivity.class);
        intentLogin.putExtra("lista", (java.io.Serializable) mListaInstituicao);
        intentLogin.putExtra("ip",mServidorIPText.getText().toString());
        startActivity(intentLogin);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = Utils.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
