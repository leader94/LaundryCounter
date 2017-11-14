package bluetowel.com.laundryhelper.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Pawan on 11/14/2017.
 */

public class ListDialog {

    private DialogInterface.OnClickListener onClickListener;
    private Context context;
    private String[] arr;
    private String title;

    public ListDialog(Context context, String[] arr, String title) {
        this.context = context;
        this.arr = arr;
        this.title = title;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickListener.onClick(dialog, which);
                dialog.dismiss();
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

