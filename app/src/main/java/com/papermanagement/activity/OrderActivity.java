package com.papermanagement.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.papermanagement.R;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.adapter.OrderAdapter;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;
import com.papermanagement.httpurl.OrderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 订单信息
 */
public class OrderActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private OrderAdapter orderAdapter;

    private ProgressBar progressBar;

    private static final String HOST_ORDER = "http://gzzhizhuo.com:8081/order/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this);
        recyclerView.setAdapter(orderAdapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        getData();
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        String factory = DataUtils.readFactory(this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_ORDER)
                .build();
        OrderService orderService = retrofit.create(OrderService.class);
        Call<OrderBean[]> call = orderService.getOrders(factory);
        call.enqueue(new Callback<OrderBean[]>() {
            @Override
            public void onResponse(Call<OrderBean[]> call, Response<OrderBean[]> response) {
                progressBar.setVisibility(View.INVISIBLE);
                OrderBean[] orders = response.body();
                orderAdapter.setItem(orders);
            }

            @Override
            public void onFailure(Call<OrderBean[]> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                Log.d("OrderActivity", t.toString());
            }
        });

    }
}
