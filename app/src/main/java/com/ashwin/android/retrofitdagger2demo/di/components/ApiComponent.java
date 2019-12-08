package com.ashwin.android.retrofitdagger2demo.di.components;

import com.ashwin.android.retrofitdagger2demo.MainActivity;
import com.ashwin.android.retrofitdagger2demo.di.modules.ApiModule;
import com.ashwin.android.retrofitdagger2demo.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity target);
}
