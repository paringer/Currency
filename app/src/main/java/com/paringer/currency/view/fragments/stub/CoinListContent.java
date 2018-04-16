package com.paringer.currency.view.fragments.stub;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paringer.currency.R;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.model.rest.response.CoinListResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample price for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CoinListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Coin> ITEMS = new ArrayList<Coin>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Coin> ITEM_MAP = new HashMap<String, Coin>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public static Collection<Coin> parseCachedList(Context context) {
        CoinListResponse coinListResponse = parseRawList(context);
        if (coinListResponse != null && coinListResponse.Data != null)
            return coinListResponse.Data.values();
        else
            return new ArrayList<>();
    }

    private static CoinListResponse parseRawList(Context context) {
        InputStream stream = context.getResources().openRawResource(R.raw.coinlist_);
        StringBuilder sb = new StringBuilder();
        String json = "";
        try {
            json = new DataInputStream(stream).readUTF();
            CoinListResponse coinList = new ObjectMapper().readValue(json, CoinListResponse.class);
            return coinList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addItem(Coin item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.Symbol, item);
    }

    private static Coin createDummyItem(int position) {
        Coin coin = new Coin();
        coin.Id = position;
        coin.Symbol = "Item " + String.valueOf(position);
        coin.Name = makeDetails(position);
        return coin;
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A stub item representing a piece of price.
     */
    public static class StubItem {
        public final String id;
        public final String content;
        public final String details;

        public StubItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
