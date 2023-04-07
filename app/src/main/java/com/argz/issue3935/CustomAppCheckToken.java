package com.argz.issue3935;

import androidx.annotation.NonNull;

import com.google.firebase.appcheck.AppCheckToken;

public class CustomAppCheckToken extends AppCheckToken {
    private final String token;
    private final long expiration;

    public CustomAppCheckToken(String token, long expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    @NonNull
    @Override
    public String getToken() {
        return token;
    }

    @Override
    public long getExpireTimeMillis() {
        return expiration;
    }
}