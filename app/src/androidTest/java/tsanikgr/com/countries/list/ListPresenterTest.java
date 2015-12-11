package tsanikgr.com.countries.list;

import android.test.InstrumentationTestCase;
import android.view.View;

import tsanikgr.com.countries.model.CardViewHolder;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.provider.MockProvider;

public class ListPresenterTest extends InstrumentationTestCase {

	ListPresenter presenter;
	MockListView mockView;
	private MockProvider mockProvider;

	public void setUp() throws Exception {
		presenter = new ListPresenter(mockView = new MockListView(),mockProvider =  new MockProvider(getInstrumentation().getContext()));
	}
	public void testRequestCountries() throws Exception {
		presenter.requestCountries();
		assertTrue(mockProvider.getCountriesCalled);
		assertFalse(mockProvider.getCountriesWhenConnectedCalled);
	}

	public void testRequestCountriesNoNetwork() throws Exception {

		//assert that all appropriate calls to the view are made on startup
		//if disconnected
		mockProvider.setOffline(true);
		presenter.requestCountries();
		assertTrue(mockView.showNetworkErrorCalled);
		assertTrue(mockProvider.getCountriesCalled);
		assertTrue(mockProvider.getCountriesWhenConnectedCalled);
	}

	public void testOnCountriesReady() throws Exception {
		presenter.onCountriesReady(null);

		CountriesModel countriesEmpty = new CountriesModel();
		presenter.onCountriesReady(countriesEmpty);
		assertFalse(mockView.showCountriesCalled);

		mockProvider.getCountries(new ICountriesProvider.CountriesListener() {
			@Override
			public void onCountriesReady(CountriesModel countries) {
				presenter.onCountriesReady(countries);
				assertTrue(mockView.showCountriesCalled);
				assertFalse(mockView.showingBackButton);
				assertFalse(mockView.showNetworkErrorCalled);
			}
			@Override
			public void onNetworkError() {
			}
		});
	}
	public void testOnNetworkError() throws Exception {
		presenter.onNetworkError();
		assertTrue(mockView.showNetworkErrorCalled);
	}
	public void testOnCountryClicked() throws Exception {
		CardViewHolder viewHolder = new CardViewHolder(new View(getInstrumentation().getContext()));
		presenter.onCountryClicked(viewHolder);
		assertTrue(mockView.showCountryDetailCalled);
	}
	public void testOnInvalidRegionSelected() throws Exception {
		presenter.onRegionSelected(null);
		assertFalse(mockView.showCountriesCalled);

		//region that does not exist
		presenter.onRegionSelected("aaa");
		assertTrue(mockView.showCountriesCalled);
		assertTrue(mockView.showingBackButton);
		assertTrue(mockView.setTitleCalled);
	}

	public void testOnRegionSelected() throws Exception {
		//region that exists
		presenter.onRegionSelected("europe");
		assertTrue(mockView.showCountriesCalled);
		assertTrue(mockView.showingBackButton);
		assertTrue(mockView.setTitleCalled);
	}

	public void testOnBackPressed() throws Exception {
		presenter.onBackPressed();
		assertTrue(mockView.finishActivityCalled);
	}

	public void testOnBackPressedWhenShowingRegion() throws Exception {
		presenter.onRegionSelected("aaa");
		presenter.onBackPressed();
		assertFalse(mockView.finishActivityCalled);
		assertTrue(mockView.showCountriesCalled);
		assertFalse(mockView.showingBackButton);
		assertTrue(mockView.setDefaultTitleCalled);
	}
}