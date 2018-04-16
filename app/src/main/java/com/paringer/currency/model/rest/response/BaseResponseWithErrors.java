package com.paringer.currency.model.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import org.json.JSONObject;

import javax.annotation.Nullable;

/**
 * Created by Zhenya on 13.04.2018.
 */

public class BaseResponseWithErrors {
    @Nullable
    @JsonIgnore
    public String Response;
    @Nullable
    @JsonIgnore
    public String Message;
    @Nullable
    @JsonIgnore
    public String Path;
    @Nullable
    @JsonIgnore
    public String ErrorsSummary;
    @Nullable
    @JsonIgnore
    public Integer Type;
    @Nullable
    @JsonIgnore
    public Boolean Aggregated;
    @Nullable
    @JsonIgnore
    public JSONObject DefaultWatchlist;

}
