package bluetowel.com.laundryhelper.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import bluetowel.com.laundryhelper.R;
import bluetowel.com.laundryhelper.fragments.AllClothes;
import bluetowel.com.laundryhelper.fragments.PermissionFragment;
import bluetowel.com.laundryhelper.fragments.SingleClothView;
import bluetowel.com.laundryhelper.fragments.WashRequiredFragment;

/**
 * Created by Pawan on 10/11/2017.
 */

public class SwitchHelper {

    private static Context context;
    public static android.support.v4.app.FragmentManager fragmentManager;
    static public boolean animReq = false;

    public static void setContext(final Context context1) {
        if (context1 == null) return;
        context = context1;
        if (context1 instanceof Activity) {
            fragmentManager = ((FragmentActivity) context1).getSupportFragmentManager();
        }
    }


    public static void OpenAllClothesFragment() {
        AllClothes fragment = new AllClothes();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (animReq) {
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            animReq = false;
        }
//        else{
//            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
//        }
        transaction.replace(R.id.main_fragment_frame, fragment);
        transaction.commit();
    }

    public static void OpenSingleClothViewFragment() {
        SingleClothView fragment = new SingleClothView();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        transaction.replace(R.id.main_fragment_frame, fragment);
        transaction.commit();
    }

    public static void OpenWashRequiredFragment() {
        WashRequiredFragment fragment = new WashRequiredFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (animReq) {
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            animReq = false;
        }
        transaction.replace(R.id.main_fragment_frame, fragment);
        transaction.commit();
    }


    public static void OpenPermissionFragment() {
        PermissionFragment fragment = new PermissionFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        transaction.replace(R.id.main_fragment_frame, fragment);
        transaction.commit();
    }

}
