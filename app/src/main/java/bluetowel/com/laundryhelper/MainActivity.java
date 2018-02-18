package bluetowel.com.laundryhelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bluetowel.com.laundryhelper.database.DatabaseHelper;
import bluetowel.com.laundryhelper.fragments.AllClothes;
import bluetowel.com.laundryhelper.interfaces.DrawerLocker;
import bluetowel.com.laundryhelper.listners.BackPressListener;
import bluetowel.com.laundryhelper.util.Constants;
import bluetowel.com.laundryhelper.util.SwitchHelper;
import bluetowel.com.laundryhelper.util.Util;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    //    private String selectedImagePath;
    public BackPressListener backPressListener;
    public static Context context;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Target target;


    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        ;
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = getBaseContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SwitchHelper.setContext(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {


                SwitchHelper.OpenPermissionFragment();
            } else {
                SwitchHelper.OpenAllClothesFragment();
            }
        }


        doSetup();


    }


    private void doSetup() {
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                final String timestamp = dateFormat.format(new Date());
                final String filename = timestamp + ".png";

                Context context = getApplicationContext();
                final File path = context.getExternalFilesDir(Constants.saveLocation);
                File file = new File(path, filename);

                try {
                    FileOutputStream stream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    stream.close();
                } catch (Exception e) {
                    Log.e("ErrorTag", e.toString());
                }


                URI uri = file.toURI();

                Uri myUri = Uri.parse(uri.toString());
//                                    Cloth obj=new Cloth(0,myUri);


                Calendar cal = Calendar.getInstance();
                Date dateToday = cal.getTime();

//                                    // TODO: 10/11/2017 edit this to suitable values in future
                DatabaseHelper dbHelper = new DatabaseHelper();


                dbHelper.addValuesToTable(myUri.toString(), 0, 0, 3, null, null);

                AllClothes.checkDBForUpdate();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(MainActivity.this, "Failed to add image", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

//    private boolean checkWriteExternalPermission()
//    {
//
//        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
//        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
//        return (res == PackageManager.PERMISSION_GRANTED);
//    }
//


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { //Assinging on corresponding import
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Util.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = Util.getPickImageResultUri(this, data);
            //Uri selectedImageUri = data.getData();

            //Uri tempUri = getImageUri()

            //checks camera becoz data of path of camera is stored in cache and gallery is fetched using cursor
            //isCamera is given value by the above used function Api.getPickImageResultUri
            //selectedImagePath = Api.isCamera ? Api.getCaptuerdImageOutputPath(this) : getPath(selectedImageUri);
            //cropped image stored in cache so no need of above checking, directly fetching from the path
//            selectedImagePath = Util.getCaptuerdImageOutputPath(this);

            try {

                if (selectedImageUri != null) {

                    Picasso picasso = new Picasso.Builder(MainActivity.this).loggingEnabled(true).listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    }).build();


                    picasso.invalidate(selectedImageUri);
                    picasso
                            .load(selectedImageUri)
                            .resize(Constants.imageStorageWidth, 0)
                            .into(target);
//                    Picasso
//                            .with(MainActivity.this)
//                            .invalidate(selectedImageUri);
//
//                    Picasso
//                            .with(MainActivity.this)
//                            .load(selectedImageUri)
//                            .resize(Constants.imageStorageWidth, 0)
//                            .into(target);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    public void setBackPressListener(BackPressListener backPressListener) {
        this.backPressListener = backPressListener;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (backPressListener != null) {
            backPressListener.onBackPress();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            SwitchHelper.OpenAllClothesFragment();
        } else if (id == R.id.nav_wash_req) {
            SwitchHelper.OpenWashRequiredFragment();
        }
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
