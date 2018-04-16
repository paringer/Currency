package com.paringer.currency.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paringer.currency.R;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.view.fragments.CoinNamesRecyclerFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Coin} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyCoinNamesViewAdapter extends RecyclerView.Adapter<MyCoinNamesViewAdapter.ViewHolder> {

    private List<Coin> mValues;
    private final OnListFragmentInteractionListener mListener;

    public void setValues(List<Coin> mValues) {
        this.mValues = mValues;
    }

    public List<Coin> getmValues() {
        return mValues;
    }

    public MyCoinNamesViewAdapter(List<Coin> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_coin_name_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSymbolView.setText(mValues.get(position).Symbol);
        holder.mCoinNameView.setText(mValues.get(position).CoinName);
        Picasso.get().load(mValues.get(position).ImageUrl).into(holder.mCoinImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSymbolView;
        public final TextView mCoinNameView;
        public final ImageView mCoinImageView;
        public Coin mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSymbolView = (TextView) view.findViewById(R.id.symbol);
            mCoinNameView = (TextView) view.findViewById(R.id.coinName);
            mCoinImageView = (ImageView) view.findViewById(R.id.coinImageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCoinNameView.getText() + "'";
        }
    }
}
