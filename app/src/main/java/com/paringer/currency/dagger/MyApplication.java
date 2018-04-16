package com.paringer.currency.dagger;

import android.app.Activity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerApplication;

public class MyApplication extends DaggerApplication implements HasActivityInjector {
    @Inject
    public DispatchingAndroidInjector<Activity> activityInjector;
    @Inject
    public AppComponent appComponent;

    @Override
    public void onCreate() {
//        activityInjector = DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(Collections.<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> emptyMap());
//        DaggerApplication_MembersInjector.injectActivityInjector(this, (Provider<DispatchingAndroidInjector<Activity>>) emptyMap());
//        DaggerApplication_MembersInjector.injectActivityInjector(this, activityInjector);
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
//        DaggerAppComponent.builder().build.inject(this);
        // DO THIS FIRST. Otherwise injects might be null!
//        ((MyApplication) this.getApplicationContext())
//            .getApplicationComponent()
//            .newActivityComponentBuilder()
//            .activity(this)
//            .build()
//            .inject(this);
//        AndroidInjection.inject(this);
        // activityInjector is null

        super.onCreate();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
