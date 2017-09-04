package com.papermanagement.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.papermanagement.R;
import com.papermanagement.Utils.CalendarUtils;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.bean.HistoryBean;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.httpurl.HistoryGroupService;
import com.papermanagement.httpurl.SearchRequestService;
import com.papermanagement.httpurl.SearchResultService;
import com.papermanagement.response.ForceDataResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFinishInfoActivity extends BaseActivity {

    private EditText etSearchData;

    private static final String HOST_SEARCH_ORDER_REQUEST = "http://gzzhizhuo.com:8081/";

    private static final String HOST_HISTORY = "http://gzzhizhuo.com:8081/history/";

    private ProgressBar progressBar;

    private Spinner spinner;

    private String group = "一号线";

    private String[] groups;

    private Button btnStartTime, btnFinishTime, btnStartDate, btnFinishDate;

    private TimePickerDialog timePickerDialog;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_finish_info);
        etSearchData = (EditText) findViewById(R.id.et_search_data);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        spinner = (Spinner) findViewById(R.id.spinner_group);
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        btnStartTime = (Button) findViewById(R.id.btn_start_time);
        btnStartTime.setText(CalendarUtils.getTodayTime());
        btnStartDate = (Button) findViewById(R.id.btn_start_date);
        btnStartDate.setText(CalendarUtils.getYesterday());

        btnFinishTime = (Button) findViewById(R.id.btn_finish_time);
        btnFinishTime.setText(CalendarUtils.getTodayTime());
        btnFinishDate = (Button) findViewById(R.id.btn_finish_date);
        btnFinishDate.setText(CalendarUtils.getToDay());
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            group = groups[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        getGroup();
    }

    /**
     * 选择开始时间
     * @param view 视图
     */
    public void onSelectStartTime(View view) {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                btnStartTime.setText(CalendarUtils.formatCalendar(hourOfDay) + ":" + CalendarUtils.formatCalendar(minute));
            }
        }, CalendarUtils.getHour(), CalendarUtils.getMinute(), true);
        timePickerDialog.show();
    }

    /**
     * 选择开始时间
     * @param view 视图
     */
    public void onSelectStartDate(View view) {
        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                btnStartDate.setText(year + "-" + CalendarUtils.formatCalendar(monthOfYear + 1) + "-" + CalendarUtils.formatCalendar(dayOfMonth));
            }
        }, CalendarUtils.getYear(), CalendarUtils.getMonth(), CalendarUtils.getDayOfMonth());
        datePickerDialog.show();
    }


    /**
     * 选择完工时间
     * @param view
     */
    public void onSelectFinishTime(View view) {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                btnFinishTime.setText(CalendarUtils.formatCalendar(hourOfDay) + ":" + CalendarUtils.formatCalendar(minute));
            }
        }, CalendarUtils.getHour(), CalendarUtils.getMinute(), true);
        timePickerDialog.show();
    }

    /**
     * 选择完工时间
     * @param view
     */
    public void onSelectFinishDate(View view) {
        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                btnFinishDate.setText(year + "-" + CalendarUtils.formatCalendar(monthOfYear + 1) + "-" + CalendarUtils.formatCalendar(dayOfMonth));
            }
        }, CalendarUtils.getYear(), CalendarUtils.getMonth(), CalendarUtils.getDayOfMonth());
        datePickerDialog.show();
    }

    /**
     * 获取该工厂有几条产线
     */
    private void getGroup() {
        progressBar.setVisibility(View.VISIBLE);
        String factory = DataUtils.readFactory(this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_HISTORY)
                .build();
        HistoryGroupService historyGroupService = retrofit.create(HistoryGroupService.class);
        Call<HistoryBean[]> call = historyGroupService.getHistoryGroup(factory);
        call.enqueue(new Callback<HistoryBean[]>() {
            @Override
            public void onResponse(Call<HistoryBean[]> call, Response<HistoryBean[]> response) {
                progressBar.setVisibility(View.INVISIBLE);
                HistoryBean[] historyBeans = response.body();
                ArrayList<String> arrayList = new ArrayList<>();
                for (HistoryBean historyBean : historyBeans) {
                    arrayList.add(historyBean.getGroup());
                }
                groups = new String[arrayList.size()];
                groups = arrayList.toArray(groups);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchFinishInfoActivity.this, android.R.layout.simple_spinner_item, groups);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<HistoryBean[]> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("SeachOrderActivity", t.toString());
                Toast.makeText(getBaseContext(), "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 上传搜索订单参数
     * @param view 视图
     */
    public void onSearchOrder(View view) {
        String searchData = etSearchData.getText().toString().trim();
        if (TextUtils.isEmpty(searchData)) {
            Toast.makeText(this, "请输入参数", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        String startTime = btnStartDate.getText().toString() + " " + btnStartTime.getText().toString();
        String finishTime = btnFinishDate.getText().toString() + " " + btnFinishTime.getText().toString();
        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("Cname", DataUtils.readFactory(this));
            bodyJson.put("Data", searchData);
            bodyJson.put("Group", group);
            bodyJson.put("Type", "finish_info");
            bodyJson.put("StartTime", startTime);
            bodyJson.put("FinishTime", finishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), bodyJson.toString());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_SEARCH_ORDER_REQUEST)
                .build();
        SearchRequestService searchRequestService = retrofit.create(SearchRequestService.class);
        Call<ForceDataResponse> call = searchRequestService.postOrderRequest(body);
        call.enqueue(new Callback<ForceDataResponse>() {
            @Override
            public void onResponse(Call<ForceDataResponse> call, Response<ForceDataResponse> response) {
                ForceDataResponse forceDataResponse = response.body();
                Log.d("searchOrderActivity", "resp:" + forceDataResponse.getResponse());
                //上传成功,5秒以后
                timer.start();
            }

            @Override
            public void onFailure(Call<ForceDataResponse> call, Throwable t) {
                Log.e("searchOrderActivity", t.toString());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    CountDownTimer timer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            getSearchResult();
        }
    };

    /**
     * 获取搜索结果
     */
    private void getSearchResult() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_SEARCH_ORDER_REQUEST)
                .build();
        SearchResultService searchResultService = retrofit.create(SearchResultService.class);
        Call<OrderBean[]> call = searchResultService.getOrderSearchResult(DataUtils.readFactory(this), group, "finish_info");
        call.enqueue(new Callback<OrderBean[]>() {
            @Override
            public void onResponse(Call<OrderBean[]> call, Response<OrderBean[]> response) {
                progressBar.setVisibility(View.INVISIBLE);
                OrderBean[] orderBeans = response.body();
                ArrayList<OrderBean> list = new ArrayList<>();
                for (OrderBean orderBean: orderBeans) {
                    list.add(orderBean);
                    Log.d("searchOrderActivity", "orderBean:" + orderBean.toString());
                }
                if (orderBeans.length > 0) {
                    //搜索成功,跳转界面
                    Intent intent = new Intent(SearchFinishInfoActivity.this, SearchFinishInfoResultActivity.class);
                    intent.putExtra("data", list);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "没有搜索到数据", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderBean[]> call, Throwable t) {
                Log.e("searchOrderActivity", t.toString());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
