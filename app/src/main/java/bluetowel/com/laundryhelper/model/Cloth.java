package bluetowel.com.laundryhelper.model;

import android.net.Uri;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pawan on 10/9/2017.
 */

public class Cloth {
    private int ID;
    private int dayWearCount;
    private int cutOffDayCount = 3;
    private int category;
    private Date lastWashDate = setCurrentDate();
    private Date lastWearDate = setCurrentDate();    // simple date format "DD MM yyyy"
    private Uri imageUri; //


    public Cloth(int ID, int dayWearCount, int cutOffDayCount, int category, Date lastWashDate, Date lastWearDate, Uri imageUri) {
        this.ID = ID;
        this.dayWearCount = dayWearCount;
        this.cutOffDayCount = cutOffDayCount;
        this.category = category;
        this.lastWashDate = lastWashDate;
        this.lastWearDate = lastWearDate;
        this.imageUri = imageUri;
    }

    //returns current date
    private Date setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public int getDayWearCount() {
        return dayWearCount;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public int getID() {
        return ID;
    }

    public Date getLastWashDate() {
        return lastWashDate;
    }

    public Date getLastWearDate() {
        return lastWearDate;
    }

    public int getCutOffDayCount() {
        return cutOffDayCount;
    }

    public int getCategory() {
        return category;
    }
}
