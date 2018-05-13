package com.paringer.currency.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.paringer.currency.BuildConfig;
import com.paringer.currency.model.rest.CryptoCompare;
import com.paringer.currency.model.rest.CryptoCompareConstants;
import com.paringer.currency.model.rest.NetworkManager;
import com.paringer.currency.model.rest.response.CoinListResponse;
import com.paringer.currency.model.rest.response.PriceMultiResponse;
import com.paringer.currency.model.rest.response.PriceResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.paringer.currency.model.rest.CryptoCompareConstants.*;

/**
 * Created by Zhenya on 13.04.2018.
 */

public class ActivityPresenter extends MvpBasePresenter {

    @Inject
    Context mContext;
    @Inject
    NetworkManager networkManager;

    public ActivityPresenter(Context context) {
        mContext = context;
        networkManager = new NetworkManager(context);
    }

    public void refreshCurrencyList(){
        networkManager.refreshCurrencyListInternal(mContext, getView());
   }

    public void refreshMultipleCoinDetails(final String fromSymbols, final String compareToSymbol){
        networkManager.refreshMultipleCoinDetailsInternal(fromSymbols, compareToSymbol, mContext, getView());
    }

    public void refreshCoinDetails(final String symbol, final String compareToSymbol){
        networkManager.refreshCoinDetailsInternal(symbol, compareToSymbol, mContext, getView());
    }

}
