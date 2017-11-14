package bluetowel.com.laundryhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bluetowel.com.laundryhelper.MainApplication;

/**
 * Created by Pawan on 10/10/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LaundryHelperDB";

    private static volatile DatabaseHandler sInstance = null;

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance() {
        DatabaseHandler localInstance = sInstance;
        if (localInstance == null) {
            synchronized (DatabaseHandler.class) {
                localInstance = sInstance;
                if (localInstance == null) {
                    sInstance = localInstance = new DatabaseHandler(MainApplication.applicationContext);
                }
            }
        }
        return localInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Tables.Clothes + "("
                + ClothColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ClothColumns.location + " TEXT, "
                + ClothColumns.category + " INTEGER, "
                + ClothColumns.wearDayCount + " INTEGER, "
                + ClothColumns.cutOffDayCount + " INTEGER, "
                + ClothColumns.lastWearDate + " TEXT, "
                + ClothColumns.lastWashDate + " TEXT "
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        onCreate(sqLiteDatabase);
    }

    public interface Tables {
        String Clothes = "CLOTHES";
    }

    public interface ClothColumns {
        String ID = "ID";
        String location = "LOC";
        String category = "CAT";
        String wearDayCount = "DAYS";
        String cutOffDayCount = "CDAYS";
        String lastWearDate = "WEARD";
        String lastWashDate = "WASHD";


    }

}
