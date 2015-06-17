package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import mobile.memoriavirtual.usp.mvmobile.R;

public class FullScreenViewActivity extends ActionBarActivity {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    ArrayList<String> bp_data_images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        Intent i = getIntent();
        bp_data_images = i.getStringArrayListExtra("images");// retrieving
        position = i.getIntExtra("position", 0);// retrieving

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                bp_data_images);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}
