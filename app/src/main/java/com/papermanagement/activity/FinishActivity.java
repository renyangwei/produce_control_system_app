package com.papermanagement.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.papermanagement.R;
import com.papermanagement.Utils.CalendarUtils;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.adapter.FinishInfoAdapter;
import com.papermanagement.adapter.OrderAdapter;
import com.papermanagement.bean.FinishTimeBean;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;
import com.papermanagement.httpurl.FinishInfoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 完工资料
 */
public class FinishActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private FinishInfoAdapter finishInfoAdapter;

    private Button btnStartTime, btnFinishTime;

    private  DatePickerDialog startTimeDialog, finishTimeDialog;

    private ProgressBar progressBar;

    private static final String HOST_FINISH_INFO = "http://192.168.0.111:8081/finish_info/";

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        finishInfoAdapter = new FinishInfoAdapter(this);
        finishInfoAdapter.setOntItemClickListner(itemClickListner);
        recyclerView.setAdapter(finishInfoAdapter);
        btnStartTime = (Button) findViewById(R.id.btn_start_time);
        btnStartTime.setText(CalendarUtils.getToDay());
        btnFinishTime = (Button) findViewById(R.id.btn_finish_time);
        btnFinishTime.setText(CalendarUtils.getToDay());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    FinishInfoAdapter.OnItemClickListner itemClickListner = new FinishInfoAdapter.OnItemClickListner() {
        @Override
        public void setOnItemClickListner(int position, FinishTimeBean finishTimeBean) {
            Intent intent = new Intent(getBaseContext(), FinishDetailActivity.class);
            finishTimeBean.setStartTime(finishInfoAdapter.getItem(position).getStartTime());
            finishTimeBean.setFinishTime(finishInfoAdapter.getItem(position).getFinishTime());
            intent.putExtra("data", finishTimeBean);
            startActivity(intent);
        }
    };

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            queryFinishInfo();
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        queryFinishInfo();
    }

    /**
     * 选择完成时间
     * @param view 视图
     */
    public void onFinishTime(View view) {
        finishTimeDialog();
    }

    /**
     * 选择开始时间
     * @param view 视图
     */
    public void onStartTime(View view) {
        showStartTimeDialog();
    }

    /**
     * 搜索按钮
     * @param view
     */
    public void onFinishSearch(View view) {
        startActivity(new Intent(this, SearchFinishInfoActivity.class));
    }

    /**
     * 显示选择日期的弹窗
     */
    private void showStartTimeDialog() {
        if (startTimeDialog == null) {
            startTimeDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String month = CalendarUtils.formatCalendar(monthOfYear + 1);
                    String day = CalendarUtils.formatCalendar(dayOfMonth);
                    String date = year + "-" + month + "-" + day;
                    btnStartTime.setText(date);
                }
            }, CalendarUtils.getYear(), CalendarUtils.getMonth(), CalendarUtils.getDayOfMonth());
        }
        startTimeDialog.show();
    }

    /**
     * 显示选择日期的弹窗
     */
    private void finishTimeDialog() {
        if (finishTimeDialog == null) {
            finishTimeDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String month = CalendarUtils.formatCalendar(monthOfYear + 1);
                    String day = CalendarUtils.formatCalendar(dayOfMonth);
                    String date = year + "-" + month + "-" + day;
                    btnFinishTime.setText(date);
                }
            }, CalendarUtils.getYear(), CalendarUtils.getMonth(), CalendarUtils.getDayOfMonth());
        }
        finishTimeDialog.show();
    }

    /**
     * 查询
     */
    private void queryFinishInfo() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        String factory = DataUtils.readFactory(this);
        String startTime = btnStartTime.getText().toString();
        String finishTime = btnFinishTime.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_FINISH_INFO)
                .build();
        FinishInfoService finishInfoService = retrofit.create(FinishInfoService.class);
        Call<OrderBean[]> call = finishInfoService.getFinishInfo(factory, startTime, finishTime, "一号线");
        call.enqueue(new Callback<OrderBean[]>() {
            @Override
            public void onResponse(Call<OrderBean[]> call, Response<OrderBean[]> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                OrderBean[] orders = response.body();
                for (OrderBean orderBean: orders) {
                    Log.d("FinishActivity", orderBean.toString());
                }
                finishInfoAdapter.setItem(orders);
            }

            @Override
            public void onFailure(Call<OrderBean[]> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(getBaseContext(), getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                Log.d("FinishActivity", t.toString());
            }
        });
    }
}
