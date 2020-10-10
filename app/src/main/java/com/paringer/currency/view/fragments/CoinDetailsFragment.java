package com.paringer.currency.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.paringer.currency.R;
import com.paringer.currency.presenter.CoinDetailsPresenter;
import com.paringer.currency.view.adapters.MyCoinDetailsViewAdapter;
import com.paringer.currency.model.rest.response.CoinDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CoinDetailsFragment extends MvpFragment implements Refreshable, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_COIN_SYMBOL = "coin-symbol";
    private static final String ARG_COIN_NAME = "coin-name";
    private static final String ARG_COIN_IMAGE = "coin-image";
    public static final String COMPARE_TO_SYMBOLS = "USD,EUR,UAH,BTC";
    public static final String TAG = CoinDetailsFragment.class.getName();

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private String mImageUrl;
    private String mSymbol;
    private String mCoinName;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.coinImageView)
    ImageView imageView;
    @BindView(R.id.symbol)
    TextView coinSymbol;
    @BindView(R.id.coinName)
    TextView coinName;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout swipeRefreshLayout2;
    private Unbinder unbinder;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CoinDetailsFragment() {
    }

    @Override
    public MvpPresenter createPresenter() {
        return new CoinDetailsPresenter(getContext());
    }

    @SuppressWarnings("unused")
    public static CoinDetailsFragment newInstance(int columnCount, String coinSymbol, String coinName, String coinImageUrl) {
        CoinDetailsFragment fragment = new CoinDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_COIN_SYMBOL, coinSymbol);
        args.putString(ARG_COIN_NAME, coinName);
        args.putString(ARG_COIN_IMAGE, coinImageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mSymbol = getArguments().getString(ARG_COIN_SYMBOL);
            mCoinName = getArguments().getString(ARG_COIN_NAME);
            mImageUrl = getArguments().getString(ARG_COIN_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_details_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Set the adapter
        if (list instanceof RecyclerView) {
            Context context = list.getContext();
            RecyclerView recyclerView = (RecyclerView) list;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(new MyCoinDetailsViewAdapter(new ArrayList<>(), mListener));
        }
        coinName.setText(mCoinName);
        coinSymbol.setText(mSymbol);

        Picasso.get().load(mImageUrl).into(imageView); // "http://i.imgur.com/DvpvklR.png"
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout2 = swipeRefreshLayout;
        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((CoinDetailsPresenter)getPresenter()).refreshCoinDetails(mSymbol, COMPARE_TO_SYMBOLS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void completeRefresh() {
        if (swipeRefreshLayout == null){
            if(swipeRefreshLayout2!=null)
                swipeRefreshLayout2.setRefreshing(false);
            unbinder = ButterKnife.bind(this, getView());
            this.onStart();
        }else{
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.invalidate();
            if(getView() != null) getView().requestLayout();
        }
    }

    @Override
    public void refresh() {
        if(!isAdded()) return;
        MyCoinDetailsViewAdapter adapter = (MyCoinDetailsViewAdapter) list.getAdapter();
        if(adapter == null) adapter = new MyCoinDetailsViewAdapter(new ArrayList<>(), (OnListFragmentInteractionListener) getContext());
        adapter.setValues(((CoinDetailsPresenter)getPresenter()).getCoinDetailsList());
        adapter.notifyDataSetChanged();
        list.invalidate();
    }

    @Override
    public void onRefresh() {
        try {
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(true);
            final SwipeRefreshLayout swipeRefreshLayout1 = this.swipeRefreshLayout;
            new Handler(Looper.getMainLooper()).postDelayed(()->{swipeRefreshLayout1.setRefreshing(false);}, 5000);
            ((CoinDetailsPresenter) getPresenter()).refreshCoinDetails(mSymbol, COMPARE_TO_SYMBOLS);
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(CoinDetails item);
    }
}
