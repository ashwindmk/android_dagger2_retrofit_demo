package com.ashwin.android.retrofitdagger2demo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {
    @GET("users/1")
    Call<ResponseBody> getUser();
}
