package bluetowel.com.laundryhelper.fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import bluetowel.com.laundryhelper.MainActivity;
import bluetowel.com.laundryhelper.MainApplication;
import bluetowel.com.laundryhelper.R;
import bluetowel.com.laundryhelper.database.DatabaseHelper;
import bluetowel.com.laundryhelper.dialogs.ListDialog;
import bluetowel.com.laundryhelper.dialogs.NumberPickerDialog;
import bluetowel.com.laundryhelper.interfaces.DrawerLocker;
import bluetowel.com.laundryhelper.listners.BackPressListener;
import bluetowel.com.laundryhelper.model.Cloth;
import bluetowel.com.laundryhelper.util.Constants;
import bluetowel.com.laundryhelper.util.SwitchHelper;

/**
 * Created by Pawan on 10/12/2017.
 */

public class SingleClothView extends Fragment implements NumberPicker.OnValueChangeListener {

    private Button save, inc, dec;
    private ImageView clothImage;
    private TextView count, wearDate, washDate, cat, cutoffDayCount;
    private int catNum; // on category update update this
    private Uri newImageURI = null; // on image update update this
    private ImageView editCutOffCount, editCat;
    private Cloth cloth;
    BackPressListener backPressListener = new BackPressListener() {
        @Override
        public void onBackPress() {
            closeFragment();
        }
    };
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setBackPressListener(backPressListener);
        cloth = MainApplication.clothObj;
        getActivity().setTitle(Constants.catArr[cloth.getCategory()]);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_cloth_view, container, false);

        inc = (Button) view.findViewById(R.id.fscv_ib_increase);
        dec = (Button) view.findViewById(R.id.fscv_ib_reduce);
        save = (Button) view.findViewById(R.id.fscv_ib_save);
        clothImage = (ImageView) view.findViewById(R.id.fscv_iv_cloth);
        count = (TextView) view.findViewById(R.id.fscv_tv_count);
//        washDate = (TextView) view.findViewById(R.id.fscv_tv_wash_date);
        cat = (TextView) view.findViewById(R.id.fscv_tv_cat);
//        wearDate = (TextView) view.findViewById(R.id.fscv_tv_worn_date);
        cutoffDayCount = (TextView) view.findViewById(R.id.fscv_tv_cutoff_count);
        editCutOffCount = (ImageView) view.findViewById(R.id.fscv_ib_edit_cutoff_count);
        editCat = (ImageView) view.findViewById(R.id.fscv_ib_edit_cat);

//        Bundle bundle = getArguments();
//
//        if (bundle != null) {
//            this.ID = (int) bundle.getInt(Constants.clothID);
//            DatabaseHelper dbHelper = new DatabaseHelper();
//            Cursor cursor = dbHelper.retClothFromTable(DatabaseHandler.Tables.Clothes, ID);
//            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
//                do {
//                    String uri_s = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ClothColumns.location));
//                    int ID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.ID));
//                    int dayWearCount = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.wearDayCount));
//                    int cutOffDayCount = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.cutOffDayCount));
//                    int cat = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.ClothColumns.category));
//                    String lastWashDate = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ClothColumns.lastWashDate));
//                    String lastWearDate = cursor.getString(cursor.getColumnIndex(DatabaseHandler.ClothColumns.lastWearDate));
//                    Uri uri = Uri.parse(uri_s);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
//                    Date formattedLastWashDate = null, formattedLastWearDate = null;
//                    try {
//                        if (lastWashDate != null) {
//                            formattedLastWashDate = dateFormat.parse(lastWashDate);
//                        }
//                        if (lastWearDate != null) {
//                            formattedLastWearDate = dateFormat.parse(lastWearDate);
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    cloth = new Cloth(ID, dayWearCount, cutOffDayCount, cat, formattedLastWashDate, formattedLastWearDate, uri);
//                } while (cursor.moveToNext());
//            }
//            doSetup();
//        }


        doSetup();
        return view;
    }


//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////
////        getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
////
////        Animation animation = AnimationUtils.makeInAnimation(getView().getContext(),false);
////        animation.setDuration(300);
////
////        animation.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////
//////                animation.start();
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation) {
////                getView().setLayerType(View.LAYER_TYPE_NONE, null);
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation animation) {
////
////            }
////        });
////        animation.startNow();
//////        animation.start();
//////        getView().startAnimation(animation);
////        ObjectAnimator animator = ObjectAnimator.ofFloat(getView(),View.TRANSLATION_X, -400, 0);
////
////
////        animator.setDuration(300);
////        animator.addListener(new Animator.AnimatorListener() {
////            @Override
////            public void onAnimationStart(Animator animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animator animation) {
////                getView().setLayerType(View.LAYER_TYPE_NONE, null);
////            }
////
////            @Override
////            public void onAnimationCancel(Animator animation) {
////
////            }
////
////            @Override
////            public void onAnimationRepeat(Animator animation) {
////
////            }
////        });
//////
////        animator.start();
//
//    }

    private void doSetup() {
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(count.getText().toString());
                c++;
                count.setText(String.valueOf(c));
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(count.getText().toString());
                c--;
                if (c <= 0) {
                    c = 0;
                }
                count.setText(String.valueOf(c));
            }
        });

//        // TODO: 10/13/2017 save function
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (catNum == -1) {
                    catNum = cloth.getCategory();
                }

                if (newImageURI == null) {
                    newImageURI = cloth.getImageUri();
                }

                Date newWashDate = null, newWearDate = null;
                try {
                    newWashDate = dateFormat.parse(washDate.getText().toString());
                    newWearDate = dateFormat.parse(wearDate.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("myTAG", e.getMessage());
                } finally {
                    final Date myWashDate = newWashDate;
                    final Date myWearDate = newWearDate;

                    new Runnable() {
                        @Override
                        public void run() {
                            DatabaseHelper dbHelper = new DatabaseHelper();
                            dbHelper.updateData(new Cloth(cloth.getID(),
                                    Integer.parseInt(count.getText().toString()),
                                    Integer.parseInt(cutoffDayCount.getText().toString()),
                                    catNum,
                                    myWashDate,
                                    myWearDate,
                                    newImageURI));
                        }
                    }.run();

                }


                closeFragment();
            }

        });


        count.setText(String.valueOf(cloth.getDayWearCount()));
        cutoffDayCount.setText((String.valueOf(cloth.getCutOffDayCount())));

        Picasso.with(getActivity().getApplicationContext())
                .load(cloth.getImageUri())
                .fit()
                .centerInside()
                .into(clothImage);

//        Date d = null;
//        if ((d = cloth.getLastWashDate()) != null) {
//            washDate.setText(dateFormat.format(d));
//        } else washDate.setText("NA");

//        if ((d = cloth.getLastWearDate()) != null) {
//            wearDate.setText(dateFormat.format(d));
//        } else wearDate.setText("NA");

        catNum = cloth.getCategory();
        cat.setText(Constants.catArr[catNum]);


        editCutOffCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberPickerDialog newFragment = new NumberPickerDialog();
                newFragment.setValueChangeListener(SingleClothView.this);
                newFragment.show(getActivity().getFragmentManager(), "time picker");


            }
        });

        editCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener DonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        catNum = which;
                        cat.setText(Constants.catArr[catNum]);
                        getActivity().setTitle(Constants.catArr[catNum]);

                    }
                };

                ListDialog listDialog = new ListDialog(getActivity(), Constants.catArr, "Choose dress type");
                listDialog.setOnClickListener(DonClickListener);
                listDialog.showDialog();
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.single_cloth_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void deleteCloth() {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        int deleted = databaseHelper.deleteFromTable(cloth.getID());
        final Uri uri = cloth.getImageUri();
        new Runnable() {
            @Override
            public void run() {
                File file = new File(uri.getPath());
                if (file.exists()) {
                    file.delete();
                } else {
                    Log.d("debugTag", "Failed to delete file \npath: " + uri.getPath());
                }
            }
        }.run();


        if (deleted == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            closeFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            deleteCloth();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void closeFragment() {
        SwitchHelper.animReq = true;
        SwitchHelper.OpenAllClothesFragment();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getActivity()).setBackPressListener(null);
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        cutoffDayCount.setText(String.valueOf(newVal));

    }
}
