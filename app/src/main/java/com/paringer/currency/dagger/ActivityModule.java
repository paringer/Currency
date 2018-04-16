package com.paringer.currency.dagger;

import android.app.Activity;

import com.paringer.currency.view.activities.CurrencyListActivity;
import com.paringer.currency.view.fragments.CoinNamesRecyclerFragment;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = CurrencyListActivitySubcomponent.class)
public abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(CurrencyListActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindCurrencyListActivityInjectorFactory(CurrencyListActivitySubcomponent.Builder builder);

//    @ContributesAndroidInjector
//    abstract BaseFragment contributeBaseFragmentInjector();

    @ContributesAndroidInjector
    abstract CoinNamesRecyclerFragment contributeRecyclerFragmentInjector();

//    @ContributesAndroidInjector
//    abstract CurrencyListActivity contributeCurrencyListActivityInjector();

    @Provides
    @Singleton
    public static CurrencyListActivity provideCurrencyListActivity(){
        return new CurrencyListActivity();
    }
}