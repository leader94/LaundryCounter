package bluetowel.com.laundryhelper.handler;

import android.content.Context;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

/**
 * Created by Pawan on 10/25/2017.
 */

public class ImageHandler {

    private static Picasso instance;

    public static Picasso getSharedInstance(Context context) {
        if (instance == null) {
            instance = new Picasso.Builder(context).executor(Executors.newSingleThreadExecutor()).memoryCache(Cache.NONE).indicatorsEnabled(false).build();
            return instance;
        } else {
            return instance;
        }
    }
}