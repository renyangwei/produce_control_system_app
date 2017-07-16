package com.papermanagement.httpurl;

import com.papermanagement.bean.HistoryBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *  最近一次的历史数据
 */
public interface LastHistoryService {

    @GET("/history/{factory}/last")
    Call<HistoryBean> getLastHistory(@Path("factory") String factory);
}
