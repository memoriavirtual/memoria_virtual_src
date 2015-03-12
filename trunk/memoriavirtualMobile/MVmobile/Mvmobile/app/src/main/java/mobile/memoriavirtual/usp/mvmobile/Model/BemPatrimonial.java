package mobile.memoriavirtual.usp.mvmobile.Model;

import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;
import mobile.memoriavirtual.usp.mvmobile.R;

/**
 * Created by User on 09/02/2015.
 */
public class BemPatrimonial {
    private String tipo;
    private String numRegistro;


    public BemPatrimonial() {
    }

    public BemPatrimonial(String tipo, String numRegistro) {
        this.tipo = tipo;
        this.numRegistro = numRegistro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(String numRegistro) {
        this.numRegistro = numRegistro;
    }

    public static BemPatrimonial parseBemPatrimonial(JSONObject jsonObject) throws JSONException {
        BemPatrimonial bp = new BemPatrimonial();

        Resources resources = Utils.getContext().getResources();
        bp.tipo         = jsonObject.getString(resources.getString(R.string.cadastro_tipo));
        bp.numRegistro  = jsonObject.getString(resources.getString(R.string.cadastro_num_registro));
        return bp;
    }

}
