package com.papermanagement.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Toast;

import com.papermanagement.R;
import com.papermanagement.Utils.CalendarUtils;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.adapter.GridViewAdapter;
import com.papermanagement.bean.HistoryBean;
import com.papermanagement.httpurl.ForceService;
import com.papermanagement.httpurl.HistoryClassService;
import com.papermanagement.httpurl.HistoryGroupService;
import com.papermanagement.httpurl.HistoryService;
import com.papermanagement.httpurl.LastHistoryService;
import com.papermanagement.response.ForceDataResponse;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryDataActivity extends BaseActivity {

    private static final String TAG = "HistoryDataActivity";

    Button btnDate, btnGroup, btnClass, btnQuery;

    private static final String HOST_HISTORY = "http://gzzhizhuo.com:8081/history/";

    private static final String HOST_FORCE = "http://gzzhizhuo.com:8081/force/";

    String[] groupArray;

    String[] classArray;

    GridView gridViewHistory;

    GridViewAdapter adapter;

    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);
        btnDate = (Button) findViewById(R.id.btn_date);
        btnGroup = (Button) findViewById(R.id.btn_group);
        btnClass = (Button) findViewById(R.id.btn_class);
        gridViewHistory = (GridView) findViewById(R.id.grid_view_history);
        adapter = new GridViewAdapter(this, R.layout.grid_view_cell);
        gridViewHistory.setAdapter(adapter);
        btnQuery = (Button) findViewById(R.id.btn_query);
        btnQuery.setOnLongClickListener(longClickListener);
        queryLastHistory();
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            showForceRefreshDialog();
            return true;
        }
    };

    /**
     * 强制刷新弹窗
     */
    private void showForceRefreshDialog() {
        new AlertDialog.Builder(this)
                .setTitle("手动刷新")
                .setMessage("系统将重新读取数据，耗时较长")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postForceData();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 查询点击事件
     *
     * @param view 视图
     */
    public void onQuery(View view) {
        queryHistory();
    }

    /**
     * 选择日期点击事件
     *
     * @param view 视图
     */
    public void onSelectDate(View view) {
        showDateDialog();
    }


    /**
     * 选择产线
     *
     * @param view 视图
     */
    public void onSelectGroup(View view) {
        showGroupDialog();
    }

    /**
     * 选择班组
     *
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
        queryHistoryGroups();
    }

    /**
     * 选择班组弹窗
     */
    private void showClassDialog() {
        queryHistoryClass();
    }

    /**
     * 获得最近一次的历史数据
     */
    private void queryLastHistory() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("查询中...");
//        progressDialog.show();
        String factory = DataUtils.readFactory(this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_HISTORY)
                .build();
        LastHistoryService lastHistoryService = retrofit.create(LastHistoryService.class);
        Call<HistoryBean> call = lastHistoryService.getLastHistory(factory);
        call.enqueue(new Callback<HistoryBean>() {
            @Override
            public void onResponse(Call<HistoryBean> call, Response<HistoryBean> response) {
                HistoryBean historyBean = response.body();
                Log.d(TAG, historyBean.toString());
                String date = historyBean.getTime();
                if (TextUtils.isEmpty(date)) {
                    date = CalendarUtils.getYesterday();
                }
                btnDate.setText(date);
                String group = historyBean.getGroup();
                if (TextUtils.isEmpty(group)) {
                    group = "一号线";
                }
//                String group = DataUtils.readGroup(getBaseContext());
                btnGroup.setText(group);
                String clazz = historyBean.getClazz();
                if (TextUtils.isEmpty(clazz)) {
                    clazz = "A";
                }
                btnClass.setText(clazz);
//                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<HistoryBean> call, Throwable t) {
//                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(HistoryDataActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 查询历史数据中的产线
     */
    private void queryHistoryGroups() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("查询中...");
        progressDialog.show();
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
                progressDialog.dismiss();
                HistoryBean[] historyBeans = response.body();
                ArrayList<String> arrayList = new ArrayList<>();
                for (HistoryBean historyBean : historyBeans) {
                    String gp = historyBean.getGroup();
                    if (gp.contains("一")) {
                        gp = "1" + gp;
                    } else if (gp.contains("二")) {
                        gp = "2"  + gp;
                    } else if (gp.contains("三")) {
                        gp = "3"  + gp;
                    } else if (gp.contains("四")) {
                        gp = "4" + gp;
                    } else if (gp.contains("五")) {
                        gp = "5" + gp;
                    } else if (gp.contains("六")) {
                        gp = "6" + gp;
                    }
                    arrayList.add(gp);
                }
                Collections.sort(arrayList, CHINA_COMPARE);
                groupArray = new String[arrayList.size()];
                groupArray = arrayList.toArray(groupArray);
                for (int i=0; i<groupArray.length; i++) {
                    groupArray[i] = groupArray[i].substring(1);
                }
                new AlertDialog.Builder(HistoryDataActivity.this)
                        .setTitle("请选择生产线")
                        .setItems(groupArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnGroup.setText(groupArray[which]);
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(Call<HistoryBean[]> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(HistoryDataActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryHistoryClass() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("查询中...");
        progressDialog.show();
        String factory = DataUtils.readFactory(this);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_HISTORY)
                .build();
        HistoryClassService historyClassService = retrofit.create(HistoryClassService.class);
        Call<HistoryBean[]> call = historyClassService.getHistoryClass(factory);
        call.enqueue(new Callback<HistoryBean[]>() {
            @Override
            public void onResponse(Call<HistoryBean[]> call, Response<HistoryBean[]> response) {
                progressDialog.dismiss();
                HistoryBean[] historyBeans = response.body();
                ArrayList<String> arrayList = new ArrayList<>();
                for (HistoryBean historyBean : historyBeans) {
                    String clazz = historyBean.getClazz();
                    if (!TextUtils.isEmpty(clazz)) {
                        arrayList.add(clazz);
                    }
                }
                classArray = new String[arrayList.size()];
                classArray = arrayList.toArray(classArray);
                new AlertDialog.Builder(HistoryDataActivity.this)
                        .setTitle("请选择班组")
                        .setItems(classArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnClass.setText(classArray[which]);
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(Call<HistoryBean[]> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(HistoryDataActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询历史数据
     */
    private void queryHistory() {
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
                }
            }

            @Override
            public void onFailure(Call<HistoryBean> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(HistoryDataActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 发送强制刷新数据
     */
    private void postForceData() {
        String factory = DataUtils.readFactory(this);
        String date = btnDate.getText().toString().trim();
        String group = btnGroup.getText().toString().trim();
        String clazz = btnClass.getText().toString().trim();
        if (TextUtils.equals(factory, "empty") || TextUtils.equals(date, "日期") || TextUtils.equals(group, "产线") ||
                TextUtils.equals(clazz, "班组")) {
            Toast.makeText(this, "请选择参数", Toast.LENGTH_SHORT).show();
            return;
        }
        String bodyJson = "{\"Factory\":\"" + factory + "\",\"Class\":\"" + clazz +
                "\",\"Group\":\"" + group + "\", \"Time\":\"" + date + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), bodyJson);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_FORCE)
                .build();
        ForceService forceService = retrofit.create(ForceService.class);
        Call<ForceDataResponse> call = forceService.postForceData(body);
        call.enqueue(new Callback<ForceDataResponse>() {
            @Override
            public void onResponse(Call<ForceDataResponse> call, Response<ForceDataResponse> response) {
                ForceDataResponse forceDataResponse = response.body();
                Log.d(TAG, "post forceData response is " + forceDataResponse.getResponse());
                showProgress();
            }

            @Override
            public void onFailure(Call<ForceDataResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void showProgress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在刷新");
        dialog.show();
        Timer mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //要执行的代码
                forceQueryHistory(dialog);
            }
        };
        mTimer.schedule(mTimerTask, 3000);//延迟3秒执行

    }

    private void forceQueryHistory(final ProgressDialog dialog) {
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
                dialog.dismiss();
                HistoryBean historyBean = response.body();
                Log.d(TAG, "response is " + historyBean.toString());
                try {
                    adapter.setData(DataUtils.parseInfo(historyBean.getOther()));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), getString(R.string.toast_error_format), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistoryBean> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(HistoryDataActivity.this, "没有找到数据,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return super.onKeyDown(keyCode, event);
    }
}
