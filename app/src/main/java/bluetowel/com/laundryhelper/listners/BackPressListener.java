package bluetowel.com.laundryhelper.listners;

/**
 * Created by Pawan on 10/29/2017.
 */

//*
// Usage Guidelines:
// Register the listener with the base activity
// and don't forget to unregister it form the activity on view/fragment destroy
// */
public interface BackPressListener {

    void onBackPress();
}
