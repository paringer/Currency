package com.paringer.currency.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paringer.currency.R;
import com.paringer.currency.view.fragments.CoinDetailsFragment.OnListFragmentInteractionListener;
import com.paringer.currency.model.rest.response.CoinDetails;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CoinDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCoinDetailsViewAdapter extends RecyclerView.Adapter<MyCoinDetailsViewAdapter.ViewHolder> {

    private List<CoinDetails> mValues;
    private final OnListFragmentInteractionListener mListener;

    public List<CoinDetails> getmValues() {
        return mValues;
    }

    public void setValues(List<CoinDetails> mValues) {
        this.mValues = mValues;
    }

    public MyCoinDetailsViewAdapter(List<CoinDetails> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_coin_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.msymbolToView.setText(mValues.get(position).symbol);
        holder.mContentView.setText(String.format("%.10f", mValues.get(position).price));
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
        public final TextView msymbolToView;
        public final TextView mContentView;
        public CoinDetails mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            msymbolToView = (TextView) view.findViewById(R.id.symbolTo);
            mContentView = (TextView) view.findViewById(R.id.price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
