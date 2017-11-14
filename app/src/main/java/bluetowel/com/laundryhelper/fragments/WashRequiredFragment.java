package bluetowel.com.laundryhelper.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bluetowel.com.laundryhelper.MainActivity;
import bluetowel.com.laundryhelper.R;
import bluetowel.com.laundryhelper.adapters.MyListAdapter;
import bluetowel.com.laundryhelper.database.DatabaseHandler;
import bluetowel.com.laundryhelper.database.DatabaseHelper;
import bluetowel.com.laundryhelper.interfaces.DrawerLocker;
import bluetowel.com.laundryhelper.model.Cloth;

/**
 * Created by Pawan on 11/1/2017.
 */

public class WashRequiredFragment extends Fragment {
    private ListView listView;
    private MyListAdapter adapter;
    private ArrayList<Cloth> clothArrayList = new ArrayList<Cloth>();
    private TextView noClothMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Wash These");
        View view = inflater.inflate(R.layout.fragment_wash_required, container, false);
        listView = (ListView) view.findViewById(R.id.fwr_listview);
        noClothMsg = (TextView) view.findViewById(R.id.fwr_tv_no_cloth_msg);
        clothArrayList.clear();


        adapter = new MyListAdapter(MainActivity.context, clothArrayList);

        listView.setAdapter(adapter);

        checkDBForUpdate();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MainApplication.clothObj = clothArrayList.get(position);
//                SwitchHelper.OpenSingleClothViewFragment();
//            }
//        });


        setHasOptionsMenu(true);
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        checkDBForUpdate();
    }


    private void checkDBForUpdate() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                DatabaseHelper dbHelper = new DatabaseHelper();

                Cursor cursor = dbHelper.retWashReqClothes();
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                    clothArrayList.clear();
                    do {
                        String uri_s = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ClothColumns.location));
                        int ID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.ID));
                        int dayWearCount = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.wearDayCount));
                        int cutOffDayCount = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.cutOffDayCount));
                        int cat = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.category));
                        String lastWashDate = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ClothColumns.lastWashDate));
                        String lastWearDate = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ClothColumns.lastWearDate));
                        Uri uri = Uri.parse(uri_s);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
                        Date formattedLastWashDate = null, formattedLastWearDate = null;
                        try {

                            if (lastWashDate != null) {
                                formattedLastWashDate = dateFormat.parse(lastWashDate);
                            }
                            if (lastWearDate != null) {
                                formattedLastWearDate = dateFormat.parse(lastWearDate);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        clothArrayList.add(new Cloth(ID, dayWearCount, cutOffDayCount, cat, formattedLastWashDate, formattedLastWearDate, uri));

                    } while (cursor.moveToNext());
                }


                adapter.notifyDataSetChanged();
                if (clothArrayList.isEmpty()) {
                    noClothMsg.setVisibility(View.VISIBLE);
                } else {
                    noClothMsg.setVisibility(View.INVISIBLE);
                }
            }
        };

        runnable.run();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.wash_req_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            checkDBForUpdate();
            adapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.action_wash_all) {

            for (Cloth cloth : clothArrayList) {
                DatabaseHelper databaseHelper = new DatabaseHelper();
                databaseHelper.resetWearDayCount(cloth.getID());
            }
            clothArrayList.clear();
            checkDBForUpdate();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

