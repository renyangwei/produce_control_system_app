package com.papermanagement.httpurl;

import com.papermanagement.response.ForceDataResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 上传搜索订单参数
 */

public interface SearchOrderRequestService {

    @POST("/search/request/")
    Call<ForceDataResponse> postOrderRequest(@Body RequestBody data);

}
