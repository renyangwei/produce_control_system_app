package com.papermanagement.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.papermanagement.R;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.adapter.GridViewAdapter;
import com.papermanagement.bean.OrderDataBen;

public class OrderDetailActivity extends BaseActivity {

    private GridView gridViewOrderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        OrderDataBen orderDataBen = (OrderDataBen) getIntent().getSerializableExtra("data");
        TextView tvOrderDetailTitle = (TextView) findViewById(R.id.tv_order_detail);
        tvOrderDetailTitle.setText("订单详情(" + orderDataBen.getKhjc() + ")");
        gridViewOrderDetail = (GridView) findViewById(R.id.grid_view_order_detail);
        GridViewAdapter adapter = new GridViewAdapter(this, R.layout.grid_view_cell);
        gridViewOrderDetail.setAdapter(adapter);
        adapter.setData(orderDataBen.toList());

        TextView tvSm2 = (TextView) findViewById(R.id.tv_sm2);
        tvSm2.setText("压线:" + DataUtils.isEmpty(orderDataBen.getSm2()));
        TextView tvDdsm = (TextView) findViewById(R.id.tv_ddsm);
        tvDdsm.setText("说明:" + DataUtils.isEmpty(orderDataBen.getDdms()));
    }
}
