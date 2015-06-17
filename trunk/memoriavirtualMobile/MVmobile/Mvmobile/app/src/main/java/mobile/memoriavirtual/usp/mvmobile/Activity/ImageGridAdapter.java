package mobile.memoriavirtual.usp.mvmobile.Activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import mobile.memoriavirtual.usp.mvmobile.Utils.Utils;

/**
 * Created by daniele on 17/06/2015.
 */
public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> images;

    public ImageGridAdapter(Context c, ArrayList<String> images) {
        mContext = c;
        this.images = images;
    }
    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(Utils.stringToBitMap(images.get(position)));
        return imageView;
    }
}
