package tsanikgr.com.countries.list;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Locale;

import tsanikgr.com.countries.model.CardViewHolder;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.provider.VolleyCountriesProvider;

public class ListPresenter implements IListPresenter, ICountriesProvider.CountriesListener{

	private final ICountriesProvider provider;
	private final WeakReference<IListView> view;
	private String countriesRegionFilter;

	public ListPresenter(@NonNull IListView view, @NonNull ICountriesProvider provider) {
		this.view = new WeakReference<>(view);
		this.provider = provider;
	}

	@Override
	public void requestCountries(){
		IListView view = this.view.get();
		if (view == null || provider == null) return;
		try {
			provider.getCountries(this);
		} catch (VolleyCountriesProvider.NoNetworkException e) {
			onNotConnectedError();
		}
	}

	@Override
	public void onCountriesReady(CountriesModel countries) {
		IListView view = this.view.get();
		if (view == null || countries == null || countries.isEmpty()) {
			return;
		}

		if (countriesRegionFilter != null) {
			countries = countries.getRegionSubset(countriesRegionFilter);
			view.showBackButton(true);
			view.setTitle(countriesRegionFilter);
		} else {
			view.showBackButton(false);
			view.setDefaultTitle();
		}

		countries.sortByLocalisedCountryName(Locale.getDefault());
		view.showCountries(countries, provider);
	}

	@Override
	public void onNetworkError() {
		IListView view = this.view.get();
		if (view == null) return;
		view.showNetworkError();
	}

	@Override
	public void onCountryClicked(CardViewHolder cardHolder) {
		IListView view = this.view.get();
		if (view == null) return;
		view.showCountryDetail(provider, cardHolder);
	}

	@Override
	public void onRegionSelected(String region) {
		if (region == null) return;
		try {
			if (countriesRegionFilter != null && region.compareTo(countriesRegionFilter) == 0) return;
			countriesRegionFilter = region;
			provider.getCountries(this);
		} catch (VolleyCountriesProvider.NoNetworkException e) {
			onNotConnectedError();
		}
	}

	private void onNotConnectedError() {
		IListView view = this.view.get();
		if (view == null) return;
		provider.getCountriesWhenConnected(this);
		view.showNetworkError();
	}

	@Override
	public void onBackPressed() {
		IListView view = this.view.get();
		try {
			if (countriesRegionFilter != null) {
				countriesRegionFilter = null;
				provider.getCountries(this);
			} else {
				view.finishActivity();
			}
		} catch (VolleyCountriesProvider.NoNetworkException e) {
			view.showNetworkError();
		}
	}
}
