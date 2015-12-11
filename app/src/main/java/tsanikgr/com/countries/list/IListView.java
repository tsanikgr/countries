package tsanikgr.com.countries.list;

import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.model.CardViewHolder;

public interface IListView {
	void showCountries(CountriesModel countries, ICountriesProvider provider);
	void showCountryDetail(ICountriesProvider provider, CardViewHolder cardHolder);
	void showBackButton(boolean show);
	void setTitle(String title);
	void setDefaultTitle();
	void finishActivity();
	void showNetworkError();
	void showLoadingProgressBar();
}