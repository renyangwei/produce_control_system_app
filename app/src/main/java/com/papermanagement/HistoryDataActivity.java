package com.papermanagement;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryDataActivity extends AppCompatActivity {

    private static final String TAG = "HistoryDataActivity";

    Button btnDate, btnGroup, btnClass;

    private static final String HOST_HISTORY = "http://180.76.163.58:8081/history/";

    String[] groupArray = {"一号线", "二号线", "三号线", "四号线", "五号线", "六号线"};

    String[] classArray = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M"};

    GridView gridViewHistory;

    GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);
        btnDate = (Button) findViewById(R.id.btn_date);
        btnDate.setText(CalendarUtils.getYesterday());
        btnGroup = (Button) findViewById(R.id.btn_group);
        btnGroup.setText(groupArray[0]);
        btnClass = (Button) findViewById(R.id.btn_class);
        btnClass.setText(classArray[0]);
        gridViewHistory = (GridView) findViewById(R.id.grid_view_history);
        adapter = new GridViewAdapter(this, R.layout.grid_view_cell);
        gridViewHistory.setAdapter(adapter);
    }

    /**
     * 查询点击事件
     * @param view 视图
     */
    public void onQuery(View view) {
        queryHistroy();
    }

    /**
     * 选择日期点击事件
     * @param view 视图
     */
    public void onSelectDate(View view) {
        showDateDialog();
    }


    /**
     * 选择产线
     * @param view 视图
     */
    public void onSelectGroup(View view) {
        showGroupDialog();
    }

    /**
     * 选择班组
     * @param view 视图
     */
    public void onSelectClass(View view) {
        showClassDialog();
    }
    /**
     * 显示选择日期的弹窗
     */
    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month = CalendarUtils.formatCalendar(monthOfYear + 1);
                String day = CalendarUtils.formatCalendar(dayOfMonth);
                String date = year + "-" + month + "-" + day;
                btnDate.setText(date);
            }
        }, CalendarUtils.getYear(), CalendarUtils.getMonth(), CalendarUtils.getDayOfMonth());
        datePickerDialog.show();
    }

    /**
     * 选择产线弹窗
     */
    private void showGroupDialog() {
        new AlertDialog.Builder(this)
                .setTitle("请选择产线")
                .setItems(groupArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnGroup.setText(groupArray[which]);
                    }
                })
                .show();
    }

    /**
     * 选择班组弹窗
     */
    private void showClassDialog() {
        new AlertDialog.Builder(this)
                .setTitle("请选择班组")
                .setItems(classArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnClass.setText(classArray[which]);
                    }
                })
                .show();
    }

    /**
     * 查询历史数据
     */
    private void queryHistroy() {
        String factory = DataUtils.readFactory(this);
        String date = btnDate.getText().toString().trim();
        String group = btnGroup.getText().toString().trim();
        String clazz = btnClass.getText().toString().trim();
        if (TextUtils.equals(factory, "empty") || TextUtils.equals(date, "日期") || TextUtils.equals(group, "产线") ||
                TextUtils.equals(clazz, "班组")) {
            Toast.makeText(this, "请选择参数", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_HISTORY)
                .build();
        HistoryService historyService = retrofit.create(HistoryService.class);
        Call<HistoryBean> call = historyService.getHistory(factory, date, clazz, group);
        call.enqueue(new Callback<HistoryBean>() {
            @Override
            public void onResponse(Call<HistoryBean> call, Response<HistoryBean> response) {
                HistoryBean historyBean = response.body();
                Log.d(TAG, "response is " + historyBean.toString());
                try {
                    adapter.setData(DataUtils.parseInfo(historyBean.getOther()));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), getString(R.string.toast_error_format), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<HistoryBean> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(HistoryDataActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
