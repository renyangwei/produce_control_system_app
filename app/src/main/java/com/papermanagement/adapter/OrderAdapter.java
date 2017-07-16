package com.papermanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.papermanagement.R;
import com.papermanagement.activity.OrderDetailActivity;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;

/**
 * 订单适配
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder> {

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
        holder.rlOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("data", mOrders[position]);
                mContext.startActivity(intent);
            }
        });
        String json = mOrders[position].getOrderDataBen();
        Gson gson = new Gson();
        OrderDataBen orderDataBen = gson.fromJson(json, OrderDataBen.class);
        holder.tvKhjc.setText(orderDataBen.getKhjc());
        holder.tvFinishTime.setText(orderDataBen.getFinishTime());
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

        private TextView tvKhjc;    //客户简称

        private TextView tvFinishTime;  //预计完成时间

        public Holder(View itemView) {
            super(itemView);
            rlOrderItem = (RelativeLayout) itemView.findViewById(R.id.rl_order_item);
            tvKhjc = (TextView) itemView.findViewById(R.id.tv_khjc);
            tvFinishTime = (TextView) itemView.findViewById(R.id.tv_finishtime);
        }
    }
}
