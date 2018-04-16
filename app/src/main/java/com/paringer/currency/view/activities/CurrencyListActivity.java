package com.paringer.currency.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.paringer.currency.R;
import com.paringer.currency.model.rest.response.Coin;
import com.paringer.currency.model.rest.response.CoinDetails;
import com.paringer.currency.presenter.ActivityPresenter;
import com.paringer.currency.presenter.CoinNamesPresenter;
import com.paringer.currency.view.fragments.CoinDetailsFragment;
import com.paringer.currency.view.fragments.CoinNamesRecyclerFragment;

public class CurrencyListActivity extends MvpActivity
        implements NavigationView.OnNavigationItemSelectedListener, CoinNamesRecyclerFragment.OnListFragmentInteractionListener, CoinDetailsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityPresenter)getPresenter()).refreshCurrencyList();
                ((ActivityPresenter)getPresenter()).refreshCoinDetails("BTC", "USD,EUR,UAH");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new ActivityPresenter(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.currency_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Coin item) {
        CoinDetailsFragment coinDetailsFragment = CoinDetailsFragment.newInstance(1, item.Symbol, item.CoinName, item.ImageUrl);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if(supportFragmentManager == null) return;
        supportFragmentManager.beginTransaction()
            .addToBackStack(coinDetailsFragment.TAG)
            .setCustomAnimations(R.anim.slide_up_in, R.anim.slide_down)
            .replace(R.id.recyclerFragmentFrame,coinDetailsFragment, CoinDetailsFragment.TAG)
        .commitAllowingStateLoss();
    }

    @Override
    public void onListFragmentInteraction(CoinDetails item) {
        // do nothing for now
    }
}
