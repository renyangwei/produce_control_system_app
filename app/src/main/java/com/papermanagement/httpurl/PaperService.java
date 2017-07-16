package com.papermanagement.httpurl;

import com.papermanagement.bean.PaperManageBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 查询实时数据
 */
public interface PaperService {
    @GET("/factory/{factory}")
    Call<PaperManageBean> getPaper(@Path("factory") String name, @Query("Group") String group);

}
