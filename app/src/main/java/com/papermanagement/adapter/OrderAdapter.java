package com.papermanagement.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.papermanagement.R;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;

/**
 * 订单适配
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder> {

    public interface OnItemClickListner {
        void setOnItemClickListner(int position, OrderDataBen orderDataBen);
    }

    private OnItemClickListner itemClickListner;

    private Context mContext;

    private OrderBean[] mOrders;

    public OrderAdapter(Context context) {
        mContext = context;
    }

    public void setItem(OrderBean[] orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        String json = mOrders[position].getOrderDataBen();
        Gson gson = new Gson();
        final OrderDataBen orderDataBen = gson.fromJson(json, OrderDataBen.class);
        holder.tvScxh.setText(orderDataBen.getScxh());
        holder.tvKhjc.setText(orderDataBen.getKhjc());
        holder.tvMxbh.setText(orderDataBen.getMxbh());
        holder.tvFinishTime.setText(orderDataBen.getFinishTime());
        holder.rlOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListner != null) {
                    itemClickListner.setOnItemClickListner(position, orderDataBen);
                }
            }
        });
        if (TextUtils.equals(orderDataBen.getKs(), "2")) {
            holder.tvScxh.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.tvKhjc.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.tvMxbh.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            holder.tvFinishTime.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else {
            holder.tvScxh.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
            holder.tvKhjc.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
            holder.tvMxbh.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
            holder.tvFinishTime.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        }
    }

    public void setOntItemClickListner(OnItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public int getItemCount() {
        if (mOrders == null) {
            return 0;
        }
        return mOrders.length;
    }

    class Holder extends RecyclerView.ViewHolder {

        private RelativeLayout rlOrderItem;

        private TextView tvScxh;    //序号

        private TextView tvKhjc;    //客户简称

        private TextView tvMxbh;    //订单编号

        private TextView tvFinishTime;  //预计完成时间

        public Holder(View itemView) {
            super(itemView);
            rlOrderItem = (RelativeLayout) itemView.findViewById(R.id.rl_order_item);
            tvScxh = (TextView) itemView.findViewById(R.id.tv_scxh);
            tvKhjc = (TextView) itemView.findViewById(R.id.tv_khjc);
            tvMxbh = (TextView) itemView.findViewById(R.id.tv_mxbh);
            tvFinishTime = (TextView) itemView.findViewById(R.id.tv_finishtime);
        }
    }
}
