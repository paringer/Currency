package com.paringer.currency.view.fragments.stub;

import com.paringer.currency.model.rest.response.CoinDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample price for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CoinDetailsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<CoinDetails> ITEMS = new ArrayList<CoinDetails>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, CoinDetails> ITEM_MAP = new HashMap<String, CoinDetails>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createCoinDetailsItem(i));
        }
    }

    private static void addItem(CoinDetails item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.symbol, item);
    }

    private static CoinDetails createCoinDetailsItem(int position) {
        return new CoinDetails(String.valueOf(position), (float) position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
