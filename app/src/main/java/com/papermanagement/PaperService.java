package com.papermanagement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by RenYangWei on 2017/2/20.
 */
public interface PaperService {
    @GET("/factory/{factory}")
    Call<PaperManangeBean> getPaper(@Path("factory") String name);

}
