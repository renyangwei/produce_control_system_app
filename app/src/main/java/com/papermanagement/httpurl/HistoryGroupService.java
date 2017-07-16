package com.papermanagement.httpurl;

import com.papermanagement.bean.HistoryBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 历史数据中的产线
 */
public interface HistoryGroupService {

    @GET("/history/{factory}/groups")
    Call<HistoryBean[]> getHistoryGroup(@Path("factory") String factory);
}
