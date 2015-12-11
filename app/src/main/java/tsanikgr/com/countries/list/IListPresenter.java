package tsanikgr.com.countries.list;

import tsanikgr.com.countries.model.CardViewHolder;

public interface IListPresenter {
	void requestCountries();
	void onCountryClicked(CardViewHolder countryViewHolder);
	void onRegionSelected(String region);
	void onBackPressed();
}
