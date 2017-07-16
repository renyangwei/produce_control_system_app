package com.papermanagement.httpurl;

import com.papermanagement.bean.HistoryBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 获得历史数据中的班组
 */
public interface HistoryClassService {

    @GET("/history/{factory}/class")
    Call<HistoryBean[]> getHistoryClass(@Path("factory") String factory);
}
