package com.papermanagement.httpurl;

import com.papermanagement.bean.OrderBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 完工资料
 */

public interface FinishInfoService {
    @GET("/finish_info")
    Call<OrderBean[]> getFinishInfo(@Query("factory") String factory, @Query("start_time") String startTime, @Query("finish_time") String finishTime);
}
