package com.paringer.currency.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.paringer.currency.BuildConfig;
import com.paringer.currency.model.rest.CryptoCompare;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.model.rest.response.CoinListResponse;
import com.paringer.currency.view.fragments.Refreshable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

public class CoinNamesPresenter extends MvpBasePresenter {

    private Context mContext;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .build();
    private ArrayList<Coin> mCoinList = new ArrayList<>();

    public ArrayList<Coin> getCoinList() {
        return mCoinList;
    }

    public CoinNamesPresenter(Context context) {
        mContext = context;
    }

    public void refreshCurrencyList(){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();

        CryptoCompare api = retrofit.create(CryptoCompare.class);
        api.coinList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<CoinListResponse>() {
                @Override
                public void accept(CoinListResponse coinList) {
                    mCoinList = new ArrayList<>();
                    List<Coin> coins = prepareCoinList(coinList.Data.values());
                    mCoinList.addAll(coins);
                    if(getView() instanceof Refreshable)
                        ((Refreshable)getView()).completeRefresh();
                    if(getView() instanceof Refreshable) {
                        ((Refreshable)getView()).refresh();
                    }
                    if(BuildConfig.DEBUG)
                    Toast.makeText(mContext, "COIN " + coinList.Data.size(), Toast.LENGTH_SHORT).show();
                    if(BuildConfig.DEBUG)
                    Log.d("COIN", "" + coinList.Data.size());

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
                    Log.e("COIN ",  exception.getLocalizedMessage());
                }}

            );
    }

    @NonNull
    public List<Coin> prepareCoinList(Collection<Coin> coins) {
        ArrayList<Coin> arr = new ArrayList();
        arr.addAll(coins);
        Collections.sort(arr, (x, y) -> x.Id - y.Id);
        for ( Coin x : arr){
            x.ImageUrl = BASE_URL_IMAGES + x.ImageUrl;
        }
        return arr;
    }

}
