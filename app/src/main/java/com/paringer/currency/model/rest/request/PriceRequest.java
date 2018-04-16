package com.paringer.currency.model.rest.request;

/**
 * Created by Zhenya on 13.04.2018.
 */

public class PriceRequest {
    String fsym; // From Symbol
    String tsyms; // To Symbols, include multiple symbols
    String e; // CCCAGG	Name of exchange. Default: CCCAGG
    String extraParams; // NotAvailable	Name of your application
    Boolean sign; // If set to true, the server will sign the requests.
    Boolean tryConversion; // If set to false, it will try to get values without using any conversion at all
}
