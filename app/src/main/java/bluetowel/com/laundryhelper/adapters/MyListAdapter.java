package bluetowel.com.laundryhelper.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bluetowel.com.laundryhelper.R;
import bluetowel.com.laundryhelper.handler.ImageHandler;
import bluetowel.com.laundryhelper.model.Cloth;

/**
 * Created by Pawan on 10/11/2017.
 */

public class MyListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater = null;
    private ArrayList clothArrayList = new ArrayList<Cloth>();

    private class ViewHolder {
        TextView count;
        ImageView iv;
    }

    public MyListAdapter(Context context, ArrayList<Cloth> clothArrayList) {
        this.context = context;
        this.clothArrayList = clothArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clothArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return clothArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View rowView = inflater.inflate(R.layout.cloth_list_unit_item_view, null);
        viewHolder.count = (TextView) rowView.findViewById(R.id.cluiv_count);
        viewHolder.iv = (ImageView) rowView.findViewById(R.id.cluiv_cloth);


        Cloth obj = (Cloth) getItem(position);
        if (obj.getDayWearCount() >= obj.getCutOffDayCount()) {
            viewHolder.count.setTextColor(Color.RED);
        } else if (obj.getDayWearCount() == obj.getCutOffDayCount() - 1) {
            viewHolder.count.setTextColor(Color.rgb(255, 165, 0));
        } else {
            viewHolder.count.setTextColor(Color.GREEN);
        }
        viewHolder.count.setText(String.valueOf(obj.getDayWearCount()));

        ImageHandler.getSharedInstance(context)
                .load(obj.getImageUri())
//                .placeholder(R.drawable.hanger)
                .fit()
                .centerInside()
                .into(viewHolder.iv);


        return rowView;
    }
}
