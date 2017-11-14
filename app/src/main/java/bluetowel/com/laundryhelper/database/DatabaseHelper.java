package bluetowel.com.laundryhelper.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import bluetowel.com.laundryhelper.MainApplication;
import bluetowel.com.laundryhelper.model.Cloth;

/**
 * Created by Pawan on 10/10/2017.
 */

public class DatabaseHelper {
    private static SQLiteDatabase database;
    private static DatabaseHandler dbHandler;

    public DatabaseHelper() {
        if (dbHandler == null) {
            dbHandler = DatabaseHandler.getInstance();
        }
        open();
    }

    public void open() throws SQLException {
        if (initDBIfNot() && database == null) {
            database = dbHandler.getWritableDatabase();
        }
    }

    private static boolean initDBIfNot() {
        if (dbHandler != null) {
            return true;
        } else {
            if (MainApplication.applicationContext != null) {
                dbHandler = DatabaseHandler.getInstance();
                return true;
            } else {
                return false;
            }
        }
    }

    public void addValuesToTable(String loc, int category, int wearDayCount, int cutOffDayCount, Date lastWearDate, Date lastWashDate) {
        try {


            open();
            ContentValues values = new ContentValues();
//dd.MM.yyyy
//            yyyy.MM.dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

            if (lastWashDate != null) {
                String formattedLastWashDate = dateFormat.format(lastWashDate);
                Log.i("dateInfo", formattedLastWashDate.toString());
                values.put(DatabaseHandler.ClothColumns.lastWashDate, formattedLastWashDate);
            }
            if (lastWashDate != null) {
                String formattedLastWearDate = dateFormat.format(lastWearDate);
                values.put(DatabaseHandler.ClothColumns.lastWearDate, formattedLastWearDate);
            }
            if (loc != null) {
                values.put(DatabaseHandler.ClothColumns.location, loc);
            }
            if (category >= 0) {
                values.put(DatabaseHandler.ClothColumns.category, category);
            }
            if (wearDayCount >= 0) {
                values.put(DatabaseHandler.ClothColumns.wearDayCount, wearDayCount);
            }
            if (cutOffDayCount >= 0) {
                values.put(DatabaseHandler.ClothColumns.cutOffDayCount, cutOffDayCount);
            }


            long rowID = database.insert(DatabaseHandler.Tables.Clothes, null, values);

        } catch (SQLiteException e) {
            Log.e("ErrorTag", e.toString());
        } catch (Exception e) {
            Log.e("ErrorTag", e.toString());
        }
    }

    public void updateData(Cloth cloth) {

        if (cloth != null) {
            try {
                open();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
                ContentValues values = new ContentValues();
                values.put(DatabaseHandler.ClothColumns.wearDayCount, String.valueOf(cloth.getDayWearCount()));
                values.put(DatabaseHandler.ClothColumns.cutOffDayCount, cloth.getCutOffDayCount());
                values.put(DatabaseHandler.ClothColumns.category, cloth.getCategory());

                if (cloth.getLastWashDate() != null) {
                    String formattedLastWashDate = dateFormat.format(cloth.getLastWashDate());
                    values.put(DatabaseHandler.ClothColumns.lastWashDate, formattedLastWashDate);
                }


                if (cloth.getLastWearDate() != null) {
                    String formattedLastWearDate = dateFormat.format(cloth.getLastWearDate());
                    values.put(DatabaseHandler.ClothColumns.lastWearDate, formattedLastWearDate);
                }

                values.put(DatabaseHandler.ClothColumns.location, cloth.getImageUri().toString());

                int rows = database.update(DatabaseHandler.Tables.Clothes, values, DatabaseHandler.ClothColumns.ID + "=" + cloth.getID(), null);


                // TODO: 10/16/2017 update this
            } catch (Exception e) {
                Log.e("ErrorTag", e.toString());
            }
        }

    }

    public Cursor retAllFromTable(String table) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + table, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }

        return null;
    }

    public Cursor retClothFromTable(String table, int clothID) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + table + " WHERE " + DatabaseHandler.ClothColumns.ID + " = " + clothID, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }

        return null;
    }

    public int deleteFromTable(int clothID) {

        return database.delete(DatabaseHandler.Tables.Clothes, DatabaseHandler.ClothColumns.ID + "=" + String.valueOf(clothID), null);
    }

    public Cursor retWashReqClothes() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHandler.Tables.Clothes + " WHERE "
                        + DatabaseHandler.ClothColumns.wearDayCount + " >= " + DatabaseHandler.ClothColumns.cutOffDayCount
                , null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        }

        return null;
    }

    public void resetWearDayCount(int clothID) {
        database.execSQL("UPDATE " + DatabaseHandler.Tables.Clothes + " SET " + DatabaseHandler.ClothColumns.wearDayCount + " =0  WHERE "
                + DatabaseHandler.ClothColumns.ID + "=" + String.valueOf(clothID));
    }
}
