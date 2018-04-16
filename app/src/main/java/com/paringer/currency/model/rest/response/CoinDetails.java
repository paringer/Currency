package com.paringer.currency.model.rest.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A dummy item representing a piece of price.
 */
public class CoinDetails implements Parcelable {
    public final String symbol;
    public final Float price;
    public final String details;

    public CoinDetails(String symbolTo, Float price, String symbolFrom) {
        this.symbol = symbolTo;
        this.price = price;
        this.details = symbolFrom;
    }

    protected CoinDetails(Parcel in) {
        symbol = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
        details = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(symbol);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(price);
        }
        dest.writeString(details);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoinDetails> CREATOR = new Creator<CoinDetails>() {
        @Override
        public CoinDetails createFromParcel(Parcel in) {
            return new CoinDetails(in);
        }

        @Override
        public CoinDetails[] newArray(int size) {
            return new CoinDetails[size];
        }
    };

    @Override
    public String toString() {
        return String.valueOf(price);
    }
}
