package com.argz.issue3935;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("fetchAppCheckToken")
    Call<ResponseBody> appToken(@Body DataRequest body);
}