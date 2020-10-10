package com.paringer.currency.model.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.json.JSONObject;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Zhenya on 13.04.2018.
 */

public class CoinListResponse extends BaseResponseWithErrors{
    @Nullable
    @JsonIgnore
    public String Response;
    public String Message;
    public String BaseImageUrl;
    public String BaseLinkUrl;
    public Integer Type;

    public Map<String, Coin> Data;

    @Nullable
    @JsonIgnore
    public String DefaultWatchlist;
    @Nullable
    @JsonIgnore
    public String SponsoredNews;
    @Nullable
    @JsonIgnore
    public String SponosoredNews;

    @Nullable
    @JsonIgnore
    public String ContentCreatedOn;
    @Nullable
    @JsonIgnore
    public String RateLimit;
    @Nullable
    @JsonIgnore
    public String HasWarning;

}
