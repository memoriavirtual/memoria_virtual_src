package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormAutor;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormDescricao;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormDisponibilidade;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormEstado;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormInformacoes;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormMidia;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormOutros;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormProcedencia;
import mobile.memoriavirtual.usp.mvmobile.Fragment.Formulario.FormProducao;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;

public class AddBemPatrimonialAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 9;

    public AddBemPatrimonialAdapter(FragmentManager fm) {
        super(fm);
    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
        Bundle data = new Bundle();
        switch(arg0){

            /** tab1 is selected */
            case 0:
                FormMidia fragment0 = new FormMidia();
                return fragment0;

            /** tab2 is selected */
            case 1:
                FormInformacoes fragment1 = new FormInformacoes();
                return fragment1;

            case 2:
                FormAutor fragment2 = new FormAutor();
                return fragment2;

            case 3:
                FormProducao fragment3 = new FormProducao();
                return fragment3;

            case 4:
                FormDescricao fragment4 = new FormDescricao();
                return fragment4;

            case 5:
                FormEstado fragment5 = new FormEstado();
                return fragment5;

            case 6:
                FormDisponibilidade fragment6 = new FormDisponibilidade();
                return fragment6;

            case 7:
                FormProcedencia fragment7 = new FormProcedencia();
                return fragment7;

            case 8:
                FormOutros fragment9 = new FormOutros();
                return fragment9;
        }
        return null;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return Utils.getContext().getString(R.string.titulo_cadastro_midia).toUpperCase();
            case 1:
                return Utils.getContext().getString(R.string.titulo_cadastro_informacoes_gerais).toUpperCase();
            case 2:
                return Utils.getContext().getString(R.string.titulo_cadastro_autor).toUpperCase();
            case 3:
                return Utils.getContext().getString(R.string.titulo_cadastro_producao).toUpperCase();
            case 4:
                return Utils.getContext().getString(R.string.titulo_cadastro_descricao).toUpperCase();
            case 5:
                return Utils.getContext().getString(R.string.titulo_cadastro_estado).toUpperCase();
            case 6:
                return Utils.getContext().getString(R.string.titulo_cadastro_disponibilidade).toUpperCase();
            case 7:
                return Utils.getContext().getString(R.string.titulo_cadastro_procedencia).toUpperCase();
            case 8:
                return Utils.getContext().getString(R.string.titulo_cadastro_outros).toUpperCase();
        }
        return null;
    }



}
