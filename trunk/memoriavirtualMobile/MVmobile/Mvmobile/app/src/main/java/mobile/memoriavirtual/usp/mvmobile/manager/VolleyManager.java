package mobile.memoriavirtual.usp.mvmobile.manager;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by danieleboscolo on 25/07/15.
 */

public class VolleyManager extends Application {
    private final String REQUEST_DEFAULT_TAG = "Memoria";
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    private static VolleyManager mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mQueue = Volley.newRequestQueue(getApplicationContext());

        mImageLoader = new ImageLoader(this.mQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static synchronized VolleyManager getInstance()
    {
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag) ? REQUEST_DEFAULT_TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        mQueue.add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        addToRequestQueue(req,null);
    }

    public VolleyManager cancelAll(String tag)
    {
        mQueue.cancelAll(tag);
        return mInstance;
    }

}
