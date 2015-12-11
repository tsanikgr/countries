package tsanikgr.com.countries.list;

import tsanikgr.com.countries.model.CardViewHolder;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;

public class MockListView implements IListView {

	public boolean setDefaultTitleCalled;
	public boolean showLoadingProgressBarCalled;
	public boolean showNetworkErrorCalled;
	public boolean showingBackButton;
	public boolean setTitleCalled;
	public boolean showCountriesCalled;
	public boolean showCountryDetailCalled;
	public boolean finishActivityCalled;

	@Override
	public void showCountries(CountriesModel countries, ICountriesProvider provider) {
		showCountriesCalled = true;
	}
	@Override
	public void showCountryDetail(ICountriesProvider provider, CardViewHolder cardHolder) {
		showCountryDetailCalled = true;
	}
	@Override
	public void showBackButton(boolean show) {
		showingBackButton = show;
	}
	@Override
	public void setTitle(String title) {
		setTitleCalled = true;
	}
	@Override
	public void setDefaultTitle() {
		setDefaultTitleCalled = true;
	}
	@Override
	public void finishActivity() {
		finishActivityCalled = true;
	}
	@Override
	public void showNetworkError() {
		showNetworkErrorCalled = true;
	}
	@Override
	public void showLoadingProgressBar() {
		showLoadingProgressBarCalled = true;
	}
}
