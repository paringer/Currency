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

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.paringer.currency.R;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.presenter.CoinNamesPresenter;
import com.paringer.currency.view.adapters.MyCoinNamesViewAdapter;
import com.paringer.currency.view.fragments.stub.CoinListContent;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CoinNamesRecyclerFragment extends MvpFragment implements Refreshable, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String TAG = CoinNamesRecyclerFragment.class.getName();

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout swipeRefreshLayout2;
    private Unbinder unbinder;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CoinNamesRecyclerFragment() {
    }

    @Override
    public MvpPresenter createPresenter() {
        return new CoinNamesPresenter(getContext());
    }

    @SuppressWarnings("unused")
    public static CoinNamesRecyclerFragment newInstance(int columnCount) {
        CoinNamesRecyclerFragment fragment = new CoinNamesRecyclerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_names_list, container, false);
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
            recyclerView.setAdapter(new MyCoinNamesViewAdapter(CoinListContent.ITEMS, mListener));

        }
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
        CoinNamesPresenter presenter = (CoinNamesPresenter) getPresenter();
        presenter.refreshCurrencyList();
        Collection<Coin> coins = CoinListContent.parseCachedList(getContext());
        List<Coin> items = presenter.prepareCoinList(coins);
        MyCoinNamesViewAdapter adapter = new MyCoinNamesViewAdapter(items, mListener);
        list.setAdapter(adapter);
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
            this.getView().requestLayout();
        }
    }

    @Override
    public void refresh() {
        if(!isAdded()) return;
        MyCoinNamesViewAdapter adapter = (MyCoinNamesViewAdapter) list.getAdapter();
        adapter.setValues(((CoinNamesPresenter)getPresenter()).getCoinList());
        adapter.notifyDataSetChanged();
        list.invalidate();
    }

    @Override
    public void onRefresh() {
        try{
            if(swipeRefreshLayout!=null)
                swipeRefreshLayout.setRefreshing(true);
            final SwipeRefreshLayout swipeRefreshLayout1 = this.swipeRefreshLayout;
            new Handler(Looper.getMainLooper()).postDelayed(()->{swipeRefreshLayout1.setRefreshing(false);}, 5000);
            ((CoinNamesPresenter)getPresenter()).refreshCurrencyList();
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
        void onListFragmentInteraction(Coin item);
    }
}
