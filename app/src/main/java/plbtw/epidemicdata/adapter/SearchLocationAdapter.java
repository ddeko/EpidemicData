package plbtw.epidemicdata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import plbtw.epidemicdata.R;


/**
 * Created by dedeeko on 8/7/15.
 */
public class SearchLocationAdapter extends BaseAdapter<String>{

    private String[] values;

    public SearchLocationAdapter(Context context,String[] values) {
        super(context);
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_store_element, parent, false);
            holder = new ViewHolder();
            holder.locationName = (TextView) convertView.findViewById(R.id.store_location);
            holder.addItemText = (TextView) convertView.findViewById(R.id.store_add_store);
            convertView.setTag(holder);
        }  else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.locationName.setText(values[position].toString());
        holder.addItemText.setText("");
        holder.locationName.setVisibility(View.VISIBLE);
        holder.addItemText.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView locationName;
        TextView addItemText;
    }
}