package com.argz.issue3935;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckProvider;
import com.google.firebase.appcheck.AppCheckProviderFactory;

public class YourCustomAppCheckProviderFactory implements AppCheckProviderFactory {

    private static YourCustomAppCheckProviderFactory yourCustomAppCheckProviderFactory;
    private final Context applicationContext;

    public YourCustomAppCheckProviderFactory(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static YourCustomAppCheckProviderFactory getInstance(Context applicationContext){
        if (yourCustomAppCheckProviderFactory == null)
            yourCustomAppCheckProviderFactory = new YourCustomAppCheckProviderFactory(applicationContext);
        return yourCustomAppCheckProviderFactory;
    }

    @NonNull
    @Override
    public AppCheckProvider create(@NonNull FirebaseApp firebaseApp) {
        return new YourCustomAppCheckProvider(applicationContext);
    }
}