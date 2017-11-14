package bluetowel.com.laundryhelper;

import android.app.Application;
import android.content.Context;

import java.lang.ref.SoftReference;

import bluetowel.com.laundryhelper.model.Cloth;

/**
 * Created by Pawan on 10/10/2017.
 */

public class MainApplication extends Application {

    public static volatile Context applicationContext;
    public  static Cloth clothObj ;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
