package com.argz.issue3935;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.appcheck.FirebaseAppCheck;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseAppCheck firebaseAppCheck;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAppCheck();
        initFirestoreBtns();
    }

    private void initAppCheck() {
        firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(YourCustomAppCheckProviderFactory.getInstance(this));
    }

    private void initFirestoreBtns() {
        findViewById(R.id.btn_get_token).setOnClickListener(view -> {
            retrieveTokenAuto();
        });
    }

    private void retrieveTokenAuto(){
        firebaseAppCheck.getToken(true).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token = task.getResult().getToken();
                Log.d(TAG, "token received: " + token);
            } else {
                Log.d(TAG, "token retrieval failed: ");
            }
        });

        firebaseAppCheck.addAppCheckListener(token -> {
            Log.d(TAG, "onAppCheckTokenChanged: " + token.getToken());
            Log.d(TAG, "onAppCheckTokenChanged: " + token.getExpireTimeMillis());
        });

    }
}

