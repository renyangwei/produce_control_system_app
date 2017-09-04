package com.papermanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.papermanagement.R;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.adapter.GridViewAdapter;
import com.papermanagement.bean.GroupBean;
import com.papermanagement.bean.PaperManageBean;
import com.papermanagement.httpurl.GroupService;
import com.papermanagement.httpurl.PaperService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 实时数据界面
 */
public class RealTimeDateActivity extends BaseActivity {

    private static final String TAG = "RealTimeDateActivity";

    private static final String HOST_FACTORY = "http://gzzhizhuo.com:8081/factory/";
    private static final String HOST_GROUPS = "http://gzzhizhuo.com:8081/factory/";

    TextView tvFactory;

    GridView gridView;

    GridViewAdapter adapter;

    ProgressBar progressBar;

    boolean refreshing;                          //是否正在刷新

    Handler handler;

    int refreshTime;

    Button btnRefresh;

    static final int TIME_LIMIT = 5;            //刷新倒计时

    static final long TIME_INTERVAL = 1000;     //刷新间隔

    MyRunnable runnable;

    Spinner spinner;

    ImageButton ibOptions;

    PopupWindow window;

    ArrayAdapter<String> spinnerAdapter;

    ArrayList<String> listGroup;

    String fact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //是否输入厂名
        String factory = DataUtils.readFactory(this);
        if (TextUtils.equals(factory, "empty")) {
            showInputDialog();
        } else {
            fact = factory;
            readGroups(factory);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void initViews() {
        tvFactory = (TextView) findViewById(R.id.tv_factory);
        gridView = (GridView) findViewById(R.id.grid_view);
        adapter = new GridViewAdapter(this, R.layout.grid_view_cell);
        gridView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        handler = new Handler();
        btnRefresh = (Button) findViewById(R.id.btn_refresh);
        spinner = (Spinner) findViewById(R.id.spinner_group);
        spinner.setOnItemSelectedListener(itemSelectedListener);
        ibOptions = (ImageButton) findViewById(R.id.ib_options);
    }

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "you clicked position:" + position);
            btnRefresh.setText("刷新(开始)");
            refreshing = false;
            handler.removeCallbacks(runnable);

            refreshTime = 0;
            refreshing = true;
            startRefreshRegular(fact, listGroup.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /**
     * 切换点击
     * @param view 视图
     */
    public void onSwitch(View view) {
        showInputDialog();
    }

    /**
     * 刷新点击
     * @param view 视图
     */
    public void onRefresh(View view) {
        if (refreshing) {
            btnRefresh.setText("刷新(开始)");
            refreshing = false;
            handler.removeCallbacks(runnable);
        } else {
            refreshTime = 0;
            refreshing = true;
            handler.post(runnable);
        }
    }

    /**
     * 显示popWindow
     * @param view  视图
     */
    public void onShowPopWindow(View view) {
        showPopWindow();
    }

    private void showPopWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
        window = new PopupWindow(popupView, DataUtils.dip2px(this, 150), DataUtils.dip2px(this, 200));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.showAsDropDown(ibOptions, 0, 0);

    }

    /**
     * 跳转到历史数据
     * @param view  视图
     */
    public void onToHistory(View view) {
        window.dismiss();
        startActivity(new Intent(this, HistoryDataActivity.class));
    }

    /**
     * 跳转到订单信息
     * @param view  视图
     */
    public void onToOrder(View view) {
        window.dismiss();
        startActivity(new Intent(this, OrderActivity.class));
    }

    /**
     * 跳转到完工信息
     * @param view
     */
    public void onToFinish(View view) {
        window.dismiss();
        startActivity(new Intent(this, FinishActivity.class));
    }

    /**
     * 从服务端获得数据
     * @param factory 厂家名称
     */
    private void getData(String factory, String group) {
        Log.d(TAG, "getData, first factory:" + factory);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_FACTORY)
                .build();
        PaperService paperService = retrofit.create(PaperService.class);
        Call<PaperManageBean> call = paperService.getPaper(factory, group);
        call.enqueue(new Callback<PaperManageBean>() {
            @Override
            public void onResponse(Call<PaperManageBean> call, Response<PaperManageBean> response) {
                progressBar.setVisibility(View.INVISIBLE);
                PaperManageBean paperManangeBean = response.body();
                try {
                    Log.d(TAG, paperManangeBean.toString());
                    adapter.setData(DataUtils.parseInfo(paperManangeBean.getOther()));
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), getString(R.string.toast_error_format), Toast.LENGTH_SHORT).show();
                    return;
                }
                String fa = paperManangeBean.getFactory();
                Log.d(TAG, "getData, factory:" + fa);
                tvFactory.setText(fa);
                DataUtils.saveFactory(fa, RealTimeDateActivity.this);
            }

            @Override
            public void onFailure(Call<PaperManageBean> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                String err = t.toString();
                Log.e(TAG, err);
                if (err.contains("MalformedJsonException")) {
                    Toast.makeText(getBaseContext(), getString(R.string.toast_input), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.toast_error), Toast.LENGTH_SHORT).show();
                }
                btnRefresh.setText("刷新(开始)");
                refreshing = false;
                handler.removeCallbacks(runnable);
            }
        });
    }

    /**
     * 查询产线
     * @param factory
     */
    private void readGroups(String factory) {
        Log.d(TAG, "readGroups factory is" + factory);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_GROUPS)
                .build();
        GroupService groupService = retrofit.create(GroupService.class);
        Call<GroupBean> call = groupService.getGroups(factory);
        call.enqueue(new Callback<GroupBean>() {
            @Override
            public void onResponse(Call<GroupBean> call, Response<GroupBean> response) {
                //处理没有返回的情况
                progressBar.setVisibility(View.INVISIBLE);
                GroupBean groupBean = response.body();
                String groupStr = groupBean.getGroup();
                Log.d(TAG, "response is " + groupStr);
                if (TextUtils.isEmpty(groupStr)) {
                    Log.d(TAG, "groupStr is empty");
                    Toast.makeText(RealTimeDateActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                listGroup = new ArrayList<>();
                String[] groups = groupStr.split(",");
                for (String str : groups) {
                    if (!TextUtils.isEmpty(str)) {
                        listGroup.add(str);
                    }
                }
                spinnerAdapter = new ArrayAdapter<>(RealTimeDateActivity.this,
                        android.R.layout.simple_spinner_item, listGroup);
                spinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onFailure(Call<GroupBean> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.toString());
                Toast.makeText(RealTimeDateActivity.this, "出错了，请重试", Toast.LENGTH_SHORT).show();
                btnRefresh.setText("刷新(开始)");
                refreshing = false;
                handler.removeCallbacks(runnable);
            }
        });

    }

    /**
     * 输入厂名弹窗
     */
    private void showInputDialog() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        final EditText editText = new EditText(this);
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        editText.setTypeface(Typeface.create("", R.style.MyEditText));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setTextColor(getResources().getColor(android.R.color.black, null));
        } else {
            editText.setTextColor(getResources().getColor(android.R.color.black));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(RealTimeDateActivity.this);
        builder.setView(editText);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setCancelable(false);
        builder.setNegativeButton(getText(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (window != null) {
                    window.dismiss();
                }
            }
        });
        builder.setPositiveButton(getText(R.string.dialog_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refreshTime = 0;
                refreshing = true;
                fact = editText.getText().toString().trim();
                Log.d(TAG, "dialog positive btn:" + fact);
                if (TextUtils.isEmpty(fact)) {
                    return;
                }
                //查询产线
                readGroups(fact);
                if (window != null) {
                    window.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * 开始刷新
     * @param factory 厂家
     */
    private void startRefreshRegular(String factory, String group) {
        Log.d(TAG, "startRefreshRegular, factory:" + factory);
        if (runnable == null) {
            runnable = new MyRunnable(factory, group);
        } else {
            runnable.setFactory(factory);
            runnable.setGroup(group);
        }

        if (handler != null) {
            btnRefresh.setText("刷新(" + refreshTime + ")");
            handler.postDelayed(runnable, TIME_INTERVAL);
        }
    }

    /**
     * 刷新线程
     */
    class MyRunnable implements Runnable {

        String mFactory;

        String mGroup;

        public MyRunnable(String factory, String group) {
            mFactory = factory;
            mGroup = group;
        }

        public void setFactory(String factory) {
            this.mFactory = factory;
        }

        public void setGroup(String group) {
            this.mGroup = group;
        }

        @Override
        public void run() {
            btnRefresh.setText("刷新(" + refreshTime + ")");
            if (refreshTime <= 0) {
                refreshTime = TIME_LIMIT;
                getData(mFactory, mGroup);
            } else {
                refreshTime -= 1;
            }
            handler.postDelayed(runnable, TIME_INTERVAL);
        }
    }
}