package com.paringer.currency.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.paringer.currency.BuildConfig;
import com.paringer.currency.model.rest.CryptoCompare;
import com.paringer.currency.model.rest.response.CoinDetails;
import com.paringer.currency.model.rest.response.PriceMultiResponse;
import com.paringer.currency.model.rest.response.PriceResponse;
import com.paringer.currency.view.fragments.Refreshable;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

public class CoinDetailsPresenter extends MvpBasePresenter {

    private Context mContext;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build();
    private ArrayList<CoinDetails> mCoinDetailsList = new ArrayList<>();
    public ArrayList<CoinDetails> getCoinDetailsList() {
        return mCoinDetailsList;
    }

    public CoinDetailsPresenter(Context context) {
        mContext = context;
    }

    public void refreshCoinDetails(final String symbol, final String compareToSymbol){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL3)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        CryptoCompare api = retrofit.create(CryptoCompare.class);
        api.price(symbol, compareToSymbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PriceResponse>() {
                               @Override
                               public void accept(PriceResponse priceDetails) {
                                   mCoinDetailsList = new ArrayList<>();
                                   for(Map.Entry<String, Float> entry : priceDetails.entrySet()){
                                       mCoinDetailsList.add(new CoinDetails(entry.getKey(), entry.getValue(), symbol));
                                   }

                                   if(getView() instanceof Refreshable)
                                       ((Refreshable)getView()).completeRefresh();
                                   if(getView() instanceof Refreshable)
                                       ((Refreshable)getView()).refresh();

                                   if(BuildConfig.DEBUG)
                                       Toast.makeText(mContext, "COIN " + priceDetails.size() +" :: " + priceDetails.get(compareToSymbol.split(",", 2)[0]), Toast.LENGTH_SHORT).show();
                                   if(BuildConfig.DEBUG)
                                       Log.d("COIN", "" + priceDetails.size()+" :BTC: "+ priceDetails.get(compareToSymbol.split(",", 2)[0]));
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable exception) {
                                if(getView() instanceof Refreshable)
                                    ((Refreshable)getView()).completeRefresh();
                                if(BuildConfig.DEBUG)
                                Toast.makeText(mContext, "COIN " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                if(BuildConfig.DEBUG)
                                Log.e("COIN ",  "Exception",exception);
                            }}
                );
    }

    public void refreshMultipleCoinDetails(final String fromSymbols, final String compareToSymbol){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL3)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();

        CryptoCompare api = retrofit.create(CryptoCompare.class);
        api.priceMultiRequest(fromSymbols, compareToSymbol,null,null,null,null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<PriceMultiResponse>() {
                           @Override
                           public void accept(PriceMultiResponse priceDetails) {
                               if(BuildConfig.DEBUG)
                                   Toast.makeText(mContext, "COIN " + priceDetails.RAW.size() +" :: " + priceDetails.RAW.get(fromSymbols.split(",", 2)[0]).get(compareToSymbol.split(",", 2)[0]).PRICE, Toast.LENGTH_SHORT).show();
                               if(BuildConfig.DEBUG)
                                   Log.d("COIN", "" + priceDetails.RAW.size()+" :BTC: "+ priceDetails.RAW.get(fromSymbols.split(",", 2)[0]).get(compareToSymbol.split(",", 2)[0]).PRICE);
                           }
                       },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable exception) {
                        if(BuildConfig.DEBUG)
                            Toast.makeText(mContext, "COIN " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        if(BuildConfig.DEBUG)
                            Log.e("COIN ",  exception.getLocalizedMessage());
                    }}

            );
    }

}
