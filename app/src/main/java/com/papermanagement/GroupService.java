package com.papermanagement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 查询产线
 */
public interface GroupService {

    @GET("/factory/{factory}/groups")
    Call<GroupBean> getGroups(@Path("factory") String name);
}
