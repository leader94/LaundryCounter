package bluetowel.com.laundryhelper.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bluetowel.com.laundryhelper.R;
import bluetowel.com.laundryhelper.interfaces.DrawerLocker;
import bluetowel.com.laundryhelper.util.Constants;
import bluetowel.com.laundryhelper.util.SwitchHelper;

/**
 * Created by Pawan on 11/5/2017.
 */

public class PermissionFragment extends Fragment {
    private Button grantPerm, exit;
    private boolean permGranted = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        View view = inflater.inflate(R.layout.fragment_permission, container, false);
        grantPerm = (Button) view.findViewById(R.id.fp_b_perm);
        exit = (Button) view.findViewById(R.id.fp_b_next);

        grantPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForWritePermission();
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permGranted) {
                    SwitchHelper.OpenAllClothesFragment();
                } else {
                    getActivity().finish();
                }

            }
        });
        return view;
    }


    private void checkForWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permGranted = false;
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.WRITE_EXTERNAL_STORAGE_PERMISSION);
            } else {
                permGranted = true;
                if (permGranted) {
                    grantPerm.setVisibility(View.INVISIBLE);
                    exit.setBackgroundColor(getResources().getColor(R.color.vanDykeBrown));
                    exit.setPadding(20, 0, 20, 0);
                    exit.setText("Let's start");
                }
            }

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        switch (requestCode) {

            case Constants.WRITE_EXTERNAL_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permGranted = true;
                } else if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    permGranted = false;
                }
                break;
            }
        }

        if (permGranted) {
            grantPerm.setVisibility(View.INVISIBLE);
            exit.setBackgroundColor(getResources().getColor(R.color.vanDykeBrown));
            exit.setPadding(20, 0, 20, 0);
            exit.setText("Let's start");
        }

    }
}
