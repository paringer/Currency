package com.paringer.currency.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.paringer.currency.BuildConfig;
import com.paringer.currency.model.rest.CryptoCompare;
import com.paringer.currency.model.rest.NetworkManager;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.model.rest.response.CoinListResponse;
import com.paringer.currency.view.activities.CurrencyListActivity;
import com.paringer.currency.view.fragments.Refreshable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.paringer.currency.model.rest.CryptoCompareConstants.*;

/**
 * Created by Zhenya on 13.04.2018.
 */

public class CoinNamesPresenter extends MvpBasePresenter {
    @Inject
    protected Context mContext;
    @Inject
    NetworkManager networkManager;

    public ArrayList<Coin> getCoinList() {
        return networkManager.getCoinList();
    }

    public CoinNamesPresenter(Context context) {
        mContext = context;
        networkManager = new NetworkManager(context);
    }

    public void refreshCurrencyList(){
        networkManager.refreshCurrencyListInternal(mContext, getView());
    }
}
