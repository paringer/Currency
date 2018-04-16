package com.paringer.currency.model.rest;

import com.paringer.currency.model.rest.response.CoinListResponse;
import com.paringer.currency.model.rest.response.PriceMultiResponse;
import com.paringer.currency.model.rest.response.PriceResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Zhenya on 13.04.2018.
 */

public interface CryptoCompare {
    @GET("coinlist")
    @Headers({
        "Host: www.cryptocompare.com",
        "Connection: keep-alive",
        "Cache-Control: max-age=0",
//            "Authorization: Basic ZXN0YWZldGE6MQ==",
//            "Upgrade-Insecure-Requests: 1",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
//            "Accept-Encoding: gzip, deflate, sdch",
        "Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4",
//            "Cookie: ClientUiCulture=en; ianaTimeZoneId=Europe%2FKiev; ASP.NET_SessionId=mnrlqrag1m20dexuamjfqwsl"
    })
    Observable<CoinListResponse> coinList();

    @GET("price")
    @Headers({
        "Host: www.cryptocompare.com",
        "Connection: keep-alive",
        "Cache-Control: max-age=0",
//            "Authorization: Basic ZXN0YWZldGE6MQ==",
//            "Upgrade-Insecure-Requests: 1",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
//            "Accept-Encoding: gzip, deflate, sdch",
        "Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4",
//            "Cookie: ClientUiCulture=en; ianaTimeZoneId=Europe%2FKiev; ASP.NET_SessionId=mnrlqrag1m20dexuamjfqwsl"
    })
    Observable<PriceResponse> price(
        @Query("fsym") String fsym,
        @Query("tsyms") String tsyms
        );

    @GET("pricemulti")
    Observable<PriceMultiResponse> priceMultiRequest(
        @Query("fsyms") String fsym,
        @Query("tsyms") String tsyms,
        @Query("e") String e,
        @Query("extraParams") String extraParams,
        @Query("sign") Boolean sign,
        @Query("tryConversion") Boolean tryConversion
        );
}
