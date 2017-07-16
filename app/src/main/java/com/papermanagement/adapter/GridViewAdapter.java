package com.papermanagement.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.papermanagement.R;
import com.papermanagement.Utils.DataUtils;

import java.util.ArrayList;

/**
 * 实时数据适配器
 */
public class GridViewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> mArrayList;

    private Context mContext;

    private int mResource;

    private static final float CELL_HEIGHT = 55f;

    public GridViewAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        mArrayList = new ArrayList<>();
    }

    public void setData(ArrayList<String> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int size = mArrayList.size();
        if (size%4 == 0) {
            return size;
        }
        return (size/4 + 1)*4;
    }

    @Override
    public String getItem(int position) {
        if (position >= mArrayList.size()) {
            return "";
        }
        return mArrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, DataUtils.dip2px(mContext, CELL_HEIGHT));
            linearLayout.setLayoutParams(params);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, linearLayout);
            viewHolder = new ViewHolder();
            viewHolder.tvCell = (TextView) convertView.findViewById(R.id.tv_grid_view_cell);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCell.setText(getItem(position));
        if (position / 4 % 2 == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.tvCell.setBackgroundColor(mContext.getColor(R.color.colorGridViewCell_1));
            } else {
                viewHolder.tvCell.setBackgroundColor(
                        mContext.getResources().getColor(R.color.colorGridViewCell_1));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewHolder.tvCell.setBackgroundColor(mContext.getColor(R.color.colorGridViewCell));
            } else {
                viewHolder.tvCell.setBackgroundColor(
                        mContext.getResources().getColor(R.color.colorGridViewCell));
            }
        }
        return convertView;
    }
}

class ViewHolder {

    TextView tvCell;

}
