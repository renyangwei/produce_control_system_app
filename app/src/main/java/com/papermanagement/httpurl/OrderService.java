package com.papermanagement.httpurl;

import com.papermanagement.bean.OrderBean;
import com.papermanagement.bean.OrderDataBen;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 订单
 */
public interface OrderService {
    @GET("/order")
    Call<OrderBean[]> getOrders(@Query("factory") String factory);
}
