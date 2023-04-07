package com.argz.issue3935;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.appcheck.AppCheckProvider;
import com.google.firebase.appcheck.AppCheckToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YourCustomAppCheckProvider implements AppCheckProvider {
    private static final String TAG = "YourCustomAppCheckProvi";
    private final Context applicationContext;

    public YourCustomAppCheckProvider(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Task<AppCheckToken> getToken() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-issue-testing-827bc.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService retroService = retrofit.create(APIService.class);

        Call<ResponseBody> call = null;
        try {
            call = retroService.appToken(new DataRequest(new JSONObject().put("data", null)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TaskCompletionSource<AppCheckToken> taskCompletionSource = new TaskCompletionSource<>();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<okhttp3.ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String token = jsonObject.getJSONObject("result").getString("token");
                    int expirationFromServer = jsonObject.getJSONObject("result").getInt("expiresAt");
                    long expMillis = expirationFromServer * 1000L - 60000;

                    Log.d(TAG, "onResponse: " + token);

                    taskCompletionSource.setResult(new CustomAppCheckToken(token,expMillis));
                } catch (IOException | JSONException e) {
                    taskCompletionSource.setException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(MainActivity.TAG, "onFailure: " + t.getMessage());
            }
        });

        return taskCompletionSource.getTask();
    }
}