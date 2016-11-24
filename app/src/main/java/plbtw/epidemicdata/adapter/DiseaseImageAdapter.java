package plbtw.epidemicdata.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import plbtw.epidemicdata.R;
import plbtw.epidemicdata.utils.FunctionUtil;
import plbtw.epidemicdata.utils.ImageUtil;


public class DiseaseImageAdapter extends BaseAdapter<String> {
    private int deviceWidth;    // cache the device width

    public DiseaseImageAdapter(Activity activity) {
        super(activity);

        deviceWidth = FunctionUtil.getDeviceSize(activity).x;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_disease_image, parent, false);

            holder = new ViewHolder();
            holder.root = (LinearLayout)convertView.findViewById(R.id.list_profile_root);
            holder.diseaseImage = (ImageView)convertView.findViewById(R.id.list_profile_food_image);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        final String imageUrl = getItem(position);

        if(imageUrl!=null) {
            if (!imageUrl.isEmpty())
                ImageUtil.display(context, imageUrl, holder.diseaseImage, false);
            else
                ImageUtil.display(context, R.drawable.no_image, holder.diseaseImage, false);
        }

        // dynamically calculate food item size
        AbsListView.LayoutParams gridViewParams = (AbsListView.LayoutParams)holder.root.getLayoutParams();
        gridViewParams.width = deviceWidth / 3;
        gridViewParams.height = gridViewParams.width;
        holder.root.setLayoutParams(gridViewParams);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        LinearLayout root;
        ImageView diseaseImage;
    }
}
