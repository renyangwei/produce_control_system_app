package com.papermanagement.httpurl;

import com.papermanagement.bean.HistoryBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 查询历史数据
 */
public interface HistoryService {

    @GET("/history/{factory}")
    Call<HistoryBean> getHistory(@Path("factory") String name, @Query("date") String date,
                                 @Query("class") String clazz, @Query("group") String group);
}
