package com.papermanagement.httpurl;

import com.papermanagement.response.ForceDataResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 强制刷新
 */
public interface ForceService {

    @POST("/force/")
    Call<ForceDataResponse> postForceData(@Body RequestBody forceData);
}
