package com.paringer.currency.model.rest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.paringer.currency.BuildConfig;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.model.rest.response.CoinDetails;
import com.paringer.currency.model.rest.response.CoinListResponse;
import com.paringer.currency.model.rest.response.PriceMultiResponse;
import com.paringer.currency.model.rest.response.PriceResponse;
import com.paringer.currency.view.fragments.Refreshable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.paringer.currency.model.rest.CryptoCompareConstants.BASE_URL;
import static com.paringer.currency.model.rest.CryptoCompareConstants.BASE_URL_IMAGES;

/**
 * Created by Zhenya on 13.05.2018.
 */

@Singleton
public class NetworkManager {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build();

    private final Context mContext;

    private ArrayList<Coin> mCoinList = new ArrayList<>();
    public ArrayList<Coin> getCoinList() {
        return mCoinList;
    }

    private ArrayList<CoinDetails> mCoinDetailsList = new ArrayList<>();
    public ArrayList<CoinDetails> getCoinDetailsList() {
        return mCoinDetailsList;
    }

    public static class ThrowableConsumer implements Consumer<Throwable>{
        private final Context mContext;
        private final Runnable mLambda;
        private final MvpView mView;
        public MvpView getView() {
            return mView;
        }

        ThrowableConsumer(Context context, MvpView mvpView, Runnable lambda){
            mContext = context;
            mView = mvpView;
            mLambda = lambda;
        }

        @Override
        public void accept(Throwable exception) throws Exception {
            if(getView() instanceof Refreshable) ((Refreshable)getView()).completeRefresh();
            if (mLambda != null) mLambda.run();
            if(BuildConfig.DEBUG) Toast.makeText(mContext, "COIN " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            if(BuildConfig.DEBUG) Log.e("COIN ",  "Exception",exception);
        }
    }
    public NetworkManager(Context context) {

        mContext = context;
    }

    public void refreshCurrencyListInternal(Context context, MvpView view){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();

        CryptoCompare api = retrofit.create(CryptoCompare.class);

        api.coinList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<CoinListResponse>() {
                           @Override
                           public void accept(CoinListResponse coinList) {
                               mCoinList = new ArrayList<>();
                               List<Coin> coins = prepareCoinList(coinList.Data.values());
                               mCoinList.addAll(coins);
                               if(view instanceof Refreshable) {
                                   ((Refreshable)view).completeRefresh();
                                   ((Refreshable)view).refresh();
                               }
                               if(BuildConfig.DEBUG)
                                   Toast.makeText(mContext, "COIN " + coinList.Data.size(), Toast.LENGTH_SHORT).show();
                               if(BuildConfig.DEBUG)
                                   Log.d("COIN", "" + coinList.Data.size());
                           }
                       },
                new ThrowableConsumer(context, view, null)

            );
    }

    public void refreshMultipleCoinDetailsInternal(final String fromSymbols, final String compareToSymbol, Context context, MvpView view){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CryptoCompareConstants.BASE_URL3)
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
                                   Toast.makeText(context, "COIN " + priceDetails.RAW.size() +" :: " + priceDetails.RAW.get(fromSymbols.split(",", 2)[0]).get(compareToSymbol.split(",", 2)[0]).PRICE, Toast.LENGTH_SHORT).show();
                               if(BuildConfig.DEBUG)
                                   Log.d("COIN", "" + priceDetails.RAW.size()+" :BTC: "+ priceDetails.RAW.get(fromSymbols.split(",", 2)[0]).get(compareToSymbol.split(",", 2)[0]).PRICE);
                           }
                       },
                new ThrowableConsumer(context, view, null)

            );
    }

    public void refreshCoinDetailsInternal(final String symbol, final String compareToSymbol, Context context, MvpView view){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CryptoCompareConstants.BASE_URL3)
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

                               if(view instanceof Refreshable) {
                                   ((Refreshable) view).completeRefresh();
                                   ((Refreshable) view).refresh();
                               }

                               if(BuildConfig.DEBUG)
                                   Toast.makeText(mContext, "COIN " + priceDetails.size() +" :: " + priceDetails.get(compareToSymbol.split(",", 2)[0]), Toast.LENGTH_SHORT).show();
                               if(BuildConfig.DEBUG)
                                   Log.d("COIN", "" + priceDetails.size()+" :BTC: "+ priceDetails.get(compareToSymbol.split(",", 2)[0]));
                           }
                       },
                new ThrowableConsumer(context, view, null)
            );
    }


    @NonNull
    public static List<Coin> prepareCoinList(Collection<Coin> coins) {
        ArrayList<Coin> arr = new ArrayList();
        arr.addAll(coins);
        Collections.sort(arr, (x, y) -> x.Id - y.Id);
        for ( Coin x : arr){
            x.ImageUrl = BASE_URL_IMAGES + x.ImageUrl;
        }
        return arr;
    }

}
