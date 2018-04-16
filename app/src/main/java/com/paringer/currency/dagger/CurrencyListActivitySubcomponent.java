package com.paringer.currency.dagger;

import com.paringer.currency.view.activities.CurrencyListActivity;

import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent()
public interface CurrencyListActivitySubcomponent extends AndroidInjector<CurrencyListActivity> {
  @Subcomponent.Builder
  public abstract class Builder extends AndroidInjector.Builder<CurrencyListActivity> {

    @BindsInstance
    public abstract CurrencyListActivitySubcomponent.Builder activityComponent(CurrencyListActivity activity);

    public abstract CurrencyListActivitySubcomponent build();
  }

}