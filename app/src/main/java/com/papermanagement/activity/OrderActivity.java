package com.papermanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

    private OrderBean[] orders;

    private static final String HOST_ORDER = "http://gzzhizhuo.com:8081/order/";

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this);
        orderAdapter.setOntItemClickListner(itemClickListner);
        recyclerView.setAdapter(orderAdapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getData();
        }
    };

    /**
     * 搜索按钮
     * @param view 视图
     */
    public void onSearch(View view) {
        startActivity(new Intent(this, SearchOrderActivity.class));
    }

    OrderAdapter.OnItemClickListner itemClickListner = new OrderAdapter.OnItemClickListner() {
        @Override
        public void setOnItemClickListner(int position, OrderDataBen orderDataBen) {
            Intent intent = new Intent(getBaseContext(), OrderDetailActivity.class);
            intent.putExtra("data", orderDataBen);
            startActivity(intent);
        }
    };

    private void getData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        String factory = DataUtils.readFactory(this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_ORDER)
                .build();
        OrderService orderService = retrofit.create(OrderService.class);
        Call<OrderBean[]> call = orderService.getOrders(factory, "一号线");
        call.enqueue(new Callback<OrderBean[]>() {
            @Override
            public void onResponse(Call<OrderBean[]> call, Response<OrderBean[]> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                orders = response.body();
                for (OrderBean orderBean: orders) {
                    Log.d("OrderActivity", orderBean.toString());
                }
                orderAdapter.setItem(orders);
            }

            @Override
            public void onFailure(Call<OrderBean[]> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(getBaseContext(), getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                Log.d("OrderActivity", t.toString());
            }
        });

    }
}
