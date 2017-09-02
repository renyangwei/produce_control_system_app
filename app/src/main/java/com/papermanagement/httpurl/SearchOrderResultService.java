package com.papermanagement.httpurl;

import com.papermanagement.bean.OrderBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  订单搜索结果
 */

public interface SearchOrderResultService {

    @GET("/search/result")
    Call<OrderBean[]> getOrderSearchResult(@Query("cname") String factory, @Query("group") String group, @Query("type") String type);
}
