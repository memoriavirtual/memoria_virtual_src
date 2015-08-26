package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;
import mobile.memoriavirtual.usp.mvmobile.services.BemPatrimonialService;
import mobile.memoriavirtual.usp.mvmobile.services.impl.BemPatrimonialServiceImpl;

/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends ActionBarActivity {

    public List<Instituicao> mListaInstituicao;
    public String mNumeroIp;

    private UserLoginTask mAuthTask = null;
    private BemPatrimonialService service;
    private long mCodInstituicao;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNomeInstituicao;
    private View mProgressView;
    private View mEmailLoginFormView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Dados passados da tela Servidor
        mListaInstituicao =  (ArrayList<Instituicao>)getIntent().getSerializableExtra("lista");
        mNumeroIp = (String)getIntent().getStringExtra("ip");

        service = BemPatrimonialServiceImpl.getInstance();

        mNomeInstituicao = (EditText) findViewById(R.id.codInstituicao);
        mNomeInstituicao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listaInstituicoesShow();
            }
        });

        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailLoginFormView = findViewById(R.id.email_login_form);

        //Recupera dados de login salvos no cache
        recuperarLoginCache();
    }

    private void recuperarLoginCache(){
        try {
            JSONObject json = Utils.carregarLoginSalvoCache();

            if(json != null) {
                mNomeInstituicao.setText(json.getString(Utils.getContext().getString(R.string.login_instituicao)));
                mEmailView.setText(json.getString(Utils.getContext().getString(R.string.login_email)));
                mPasswordView.setText(json.getString(Utils.getContext().getString(R.string.login_senha)));

                JSONObject listaInstituicaoJSON = json.getJSONObject(Utils.getContext().getString(R.string.login_instituicao_lista));
                for (int i = 0; i < listaInstituicaoJSON.length(); i++) {
                    Instituicao instituicao = new Instituicao();
                    instituicao.setId(listaInstituicaoJSON.getLong("id"));
                    instituicao.setNome(listaInstituicaoJSON.getString("nome"));
                    mListaInstituicao.add(instituicao);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void listaInstituicoesShow(){
        AlertDialog dialog ;
        int size = mListaInstituicao.size();

        //Preencher alert com os nomes das instituicoes retornadas do service
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Se array vazio significa que instituicoes ainda não foram carregadas
        if(size == 0 ){
            builder.setTitle("Carregando instituições...");
        } else {
            final CharSequence str[] = new String[size];
            for (int i = 0; i < size; i++) {
                str[i] = mListaInstituicao.get(i).getNome();
            }
            builder.setTitle("Instituição:");
            builder.setItems(str, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int position) {
                    mNomeInstituicao.setText(str[position]);
                    mCodInstituicao = mListaInstituicao.get(position).getId();
                }
            });
        }
        dialog = builder.create();
        dialog.show();
    }

    public void errorMessageShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erro ao carregar instituições");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String senha = mPasswordView.getText().toString();
        String idInstituicao = String.valueOf(mCodInstituicao);

        //Salva dados de login no Cache
        try {
            Utils.salvarLoginCache(email, senha, idInstituicao, mListaInstituicao);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(senha)) {//&& !isPasswordValid(password)) {
            mPasswordView.setError(Utils.getContext().getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(Utils.getContext().getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, senha);
            mAuthTask.execute((Void) null);

            //Recupera bemPatrimonial do Cache
            ArrayList<BemPatrimonial> bemPatrimonialArray = Utils.carregarBensPatrimoniaisSalvosCache();
            //Se tiver um bem patrimonial cadastrado entao envia para servidor
            if (bemPatrimonialArray != null) {
                if( bemPatrimonialArray.size() > 0) {
                    BemPatrimonial bemPatrimonial = bemPatrimonialArray.get(0);

                    service.enviarBemPatrimonial(mNumeroIp, email, senha, idInstituicao, bemPatrimonial, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    Log.e("Dado enviado com sucesso!", s);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            }
                    );
                }

            }
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
               // finish();
            } else {
               // mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}