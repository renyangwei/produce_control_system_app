package com.papermanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.papermanagement.R;
import com.papermanagement.Utils.DataUtils;
import com.papermanagement.bean.OrderBean;
import com.papermanagement.httpurl.SearchOrderRequestService;
import com.papermanagement.httpurl.SearchOrderResultService;
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

public class SearchOrderActivity extends BaseActivity {

    private EditText etSearchData;

    private static final String HOST_SEARCH_ORDER_REQUEST = "http://172.23.0.132:8081/";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);
        etSearchData = (EditText) findViewById(R.id.et_search_data);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    /**
     * 上传搜索订单参数
     * @param view 视图
     */
    public void onSearchOrder(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String searchData = etSearchData.getText().toString().trim();
        if (TextUtils.isEmpty(searchData)) {
            Toast.makeText(this, "请输入参数", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("Cname", DataUtils.readFactory(this));
            bodyJson.put("Data", searchData);
            bodyJson.put("Group", "一号线");
            bodyJson.put("Type", "order");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), bodyJson.toString());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(HOST_SEARCH_ORDER_REQUEST)
                .build();
        SearchOrderRequestService searchOrderRequestService = retrofit.create(SearchOrderRequestService.class);
        Call<ForceDataResponse> call = searchOrderRequestService.postOrderRequest(body);
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
        SearchOrderResultService searchOrderResultService = retrofit.create(SearchOrderResultService.class);
        Call<OrderBean[]> call = searchOrderResultService.getOrderSearchResult(DataUtils.readFactory(this), "一号线", "order");
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
                    Intent intent = new Intent(SearchOrderActivity.this, SearchOrderResultActivity.class);
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
