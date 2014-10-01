package br.usp.mvmobile.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.usp.mvmobile.Fragment.FormFragment;
import br.usp.mvmobile.Fragment.Registered.RegisteredPatrimonialFragment;
import br.usp.mvmobile.R;

public class MainActivity extends Activity implements RegisteredPatrimonialFragment.OnRegistedInteractionListener, FormFragment.OnFragmentInteractionListener{

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlanetTitles = new String[4];
        mPlanetTitles[0] = "Bens cadastrados";
        mPlanetTitles[1] = "Cadastrar";
        mPlanetTitles[2] = "Excluir";
        mPlanetTitles[3] = "Login";

        //Fragment inicial
        Fragment fragment = new RegisteredPatrimonialFragment();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().add(R.id.content_fragment, fragment).commit();

        //Set Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRegistedInteraction(Uri uri) {

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
        selectDrawer(position);

        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new RegisteredPatrimonialFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
                break;
            case 1:
                fragment = new FormFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
                break;
            case 2:
                fragment = new RegisteredPatrimonialFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
                break;
            case 3:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }

    private void selectDrawer(int position)
    {
        if(position == 3) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else{
            mDrawerList.setItemChecked(position, true);
            setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
