package com.paringer.currency.dagger;

    import android.app.Application;
    import android.content.Context;

    import com.paringer.currency.view.activities.CurrencyListActivity;

    import dagger.Binds;
    import dagger.Module;

@Module
public abstract class AppModule {

    // same as provides but this returns injected parameter
    @Binds
    abstract Context bindContext(Application application);
//    @Binds
//    abstract Activity bindActivity(Activity activity);
    @Binds
    abstract CurrencyListActivity bindMyActivity(CurrencyListActivity activity);

//    @Provides()
//    static Provider<CurrencyListActivity> provide(){
//        return new Provider<CurrencyListActivity>(){
//            @Override
//            public CurrencyListActivity get() {
//                return new CurrencyListActivity();
//            }
//        };
//    }

}

