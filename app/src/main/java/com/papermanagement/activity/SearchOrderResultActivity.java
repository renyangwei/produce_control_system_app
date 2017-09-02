package com.papermanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.papermanagement.R;
import com.papermanagement.adapter.OrderAdapter;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;

import java.util.ArrayList;

public class SearchOrderResultActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order_result);
        ArrayList<OrderBean> list = (ArrayList<OrderBean>) getIntent().getSerializableExtra("data");
        OrderBean[] orderBeans = list.toArray(new OrderBean[0]);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this);
        orderAdapter.setOntItemClickListner(itemClickListner);
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.setItem(orderBeans);
    }

    OrderAdapter.OnItemClickListner itemClickListner = new OrderAdapter.OnItemClickListner() {
        @Override
        public void setOnItemClickListner(int position, OrderDataBen orderDataBen) {
            Intent intent = new Intent(getBaseContext(), OrderDetailActivity.class);
            intent.putExtra("data", orderDataBen);
            startActivity(intent);
        }
    };

}
