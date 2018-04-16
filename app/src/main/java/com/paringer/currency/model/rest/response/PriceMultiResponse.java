package com.paringer.currency.model.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Zhenya on 13.04.2018.
 */

public class PriceMultiResponse extends BaseResponseWithErrors {
    public Map < String, Map < String, Price> > RAW;
    @Nullable
    @JsonIgnore
    public String DefaultWatchlist;
}
