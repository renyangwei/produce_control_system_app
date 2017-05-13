package com.papermanagement;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String HOST = "http://180.76.163.58:8081/factory/";
    //10.0.2.2
    //180.76.163.58

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //是否输入厂名
        String factory = readFactory();
        if (TextUtils.equals(factory, "empty")) {
            showInputDialog();
        } else {
            refreshTime = 0;
            refreshing = true;
            startRefreshRegular(factory);
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
    }

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
     * 从服务端获得数据
     * @param factory 厂家名称
     */
    private void getData(String factory) {
        Log.d(TAG, "getData, first factory:" + factory);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                        //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST)
                .build();
        PaperService paperService = retrofit.create(PaperService.class);
        Call<PaperManangeBean> call = paperService.getPaper(factory);
        call.enqueue(new Callback<PaperManangeBean>() {
            @Override
            public void onResponse(Call<PaperManangeBean> call, Response<PaperManangeBean> response) {
                progressBar.setVisibility(View.INVISIBLE);
                PaperManangeBean paperManangeBean = response.body();
                Log.d(TAG, paperManangeBean.toString());
                try {
                    adapter.setData(DataUtil.parseInfo(paperManangeBean.getOther()));
                } catch (Exception e) {
//                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), getString(R.string.toast_error_format), Toast.LENGTH_SHORT).show();
                    return;
                }
                String fa = paperManangeBean.getFactory();
                Log.d(TAG, "getData, factory:" + fa);
                tvFactory.setText(fa);
                saveFactory(fa);
            }

            @Override
            public void onFailure(Call<PaperManangeBean> call, Throwable t) {
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

    private void saveFactory(String factory) {
        SharedPreferences sharedPreferences = getSharedPreferences("Factory", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("factory", factory);
        editor.apply();
    }

    private String readFactory() {
        SharedPreferences sharedPreferences = getSharedPreferences("Factory", MODE_PRIVATE);
        return sharedPreferences.getString("factory", "empty");
    }

    /**
     * 输入厂名弹窗
     */
    private void showInputDialog() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
        final EditText editText = new EditText(this);
        editText.setTypeface(Typeface.create("", R.style.MyEditText));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setTextColor(getResources().getColor(android.R.color.black, null));
        } else {
            editText.setTextColor(getResources().getColor(android.R.color.black));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(editText);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setCancelable(false);
        builder.setNegativeButton(getText(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (runnable != null) {
                    handler.post(runnable);
                }
            }
        });
        builder.setPositiveButton(getText(R.string.dialog_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refreshTime = 0;
                refreshing = true;
                String fa = editText.getText().toString().trim();
                Log.d(TAG, "dialog positive btn:" + fa);
                startRefreshRegular(fa);
            }
        });
        builder.show();
    }

    /**
     * 开始刷新
     * @param factory 厂家
     */
    private void startRefreshRegular(String factory) {
        Log.d(TAG, "startRefreshRegular, factory:" + factory);
        if (runnable == null) {
            runnable = new MyRunnable(factory);
        } else {
            runnable.setFactory(factory);
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

        public MyRunnable(String factory) {
            mFactory = factory;
        }

        public void setFactory(String factory) {
            this.mFactory = factory;
        }

        @Override
        public void run() {
            btnRefresh.setText("刷新(" + refreshTime + ")");
            if (refreshTime <= 0) {
                refreshTime = TIME_LIMIT;
                getData(mFactory);
            } else {
                refreshTime -= 1;
            }
            handler.postDelayed(runnable, TIME_INTERVAL);
        }
    }
}