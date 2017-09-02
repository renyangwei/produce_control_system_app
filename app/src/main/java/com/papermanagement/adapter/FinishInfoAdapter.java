package com.papermanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.papermanagement.R;
import com.papermanagement.bean.FinishTimeBean;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;

/**
 * 完工资料适配器
 */
public class FinishInfoAdapter extends RecyclerView.Adapter<FinishInfoAdapter.Holder>{

    public interface OnItemClickListner {
        void setOnItemClickListner(int position, FinishTimeBean finishTimeBean);
    }

    private OnItemClickListner itemClickListner;

    private Context mContext;

    private OrderBean[] mOrders;

    public FinishInfoAdapter(Context context) {
        mContext = context;
    }

    public void setItem(OrderBean[] orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }

    public OrderBean getItem(int position) {
        return mOrders[position];
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.finish_info_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        String json = mOrders[position].getOrderDataBen();
        Gson gson = new Gson();
        final FinishTimeBean finishTimeBean = gson.fromJson(json, FinishTimeBean.class);
        holder.tvMxbh.setText(finishTimeBean.getMxbh());
        holder.tvKhjc.setText(finishTimeBean.getKhjc());
        holder.tvStartTime.setText(mOrders[position].getStartTime());
        holder.tvFinishTime.setText(mOrders[position].getFinishTime());
        holder.rlOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListner != null) {
                    itemClickListner.setOnItemClickListner(position, finishTimeBean);
                }
            }
        });
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

        private TextView tvMxbh;    //订单编号

        private TextView tvKhjc;    //客户

        private TextView tvStartTime; //开始时间

        private TextView tvFinishTime;  //完成时间

        public Holder(View itemView) {
            super(itemView);
            rlOrderItem = (RelativeLayout) itemView.findViewById(R.id.rl_order_item);
            tvMxbh = (TextView) itemView.findViewById(R.id.tv_mxbh);
            tvKhjc = (TextView) itemView.findViewById(R.id.tv_khjc);
            tvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            tvFinishTime = (TextView) itemView.findViewById(R.id.tv_finish_time);
        }
    }
}
