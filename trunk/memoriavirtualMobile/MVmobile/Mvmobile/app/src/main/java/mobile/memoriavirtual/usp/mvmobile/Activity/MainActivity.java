package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import mobile.memoriavirtual.usp.mvmobile.Fragment.ListaBemPatrimonial.ListaBemPatrimonialFragment;
import mobile.memoriavirtual.usp.mvmobile.R;
import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;


public class MainActivity extends ActionBarActivity implements ListaBemPatrimonialFragment.OnFragmentInteractionListener {

    private String[] mDrawerTitulos;
    private ListView mDrawerLista;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Seta o contexto
        Utils.setContext(getBaseContext());

        mDrawerTitulos = new String[2];
        mDrawerTitulos[0] = "Bens cadastrados";
        mDrawerTitulos[1] = "Cadastrar";

        //Fragment inicial
        Fragment fragment = new ListaBemPatrimonialFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.main_content_fragment, fragment).commit();
        //Set Drawer
        setDrawer();
    }

    private void setDrawer()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLista = (ListView) findViewById(R.id.left_drawer);
        mDrawerLista.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerTitulos));
        mDrawerLista.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLista.setItemChecked(0, true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        mDrawerToggle.syncState(); //very importante
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        // update selected item and title, then close the drawer
        //selectDrawer(position);

        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ListaBemPatrimonialFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content_fragment, fragment).commit();
                break;
            case 1:
                Intent intent = new Intent(this, AddBemPatrimonialActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(mDrawerLista);
                break;
        }
    }

    private void selectDrawer(int position)
    {
        if(position == 3) {
            mDrawerLayout.closeDrawer(mDrawerLista);
        }
        else{
            //mDrawerLista.setItemChecked(position, true);
            setTitle(mDrawerTitulos[position]);
            //mDrawerLayout.closeDrawer(mDrawerLista);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Clique da lista

    public void onRemoveClick(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Deseja mesmo excluir?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Utils.removerBemPatrimonialCache(0);
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
}
