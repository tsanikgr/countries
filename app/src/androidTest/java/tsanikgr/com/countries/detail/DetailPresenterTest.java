package tsanikgr.com.countries.detail;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

import tsanikgr.com.countries.Constants;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.MockProvider;

public class DetailPresenterTest extends InstrumentationTestCase {

	DetailPresenter detailPresenter;
	MockDetailView mockView;

	public void setUp() {
		mockView = new MockDetailView();
		detailPresenter = new DetailPresenter(mockView);
		detailPresenter.setProvider(new MockProvider(getInstrumentation().getContext()));
	}

	public void testOnCreate() throws Exception {
		detailPresenter.onCreate(null);
		detailPresenter.onCreate(new Bundle());
		Bundle bundle = new Bundle();
		bundle.putString(Constants.COUNTRY_CODE, "en");
		bundle.putByteArray(Constants.COUNTY_FLAG_BITMAP, null);
		detailPresenter.onCreate(bundle);
		Bundle bundle1 = new Bundle();
		bundle1.putString(Constants.COUNTRY_CODE, "en");
		bundle1.putByteArray(Constants.COUNTY_FLAG_BITMAP, new byte[10]);
		detailPresenter.onCreate(bundle1);
	}

	public void testOnCountryBorderClicked() throws Exception {
		detailPresenter.onCountryBorderClicked("en", null);
		assertTrue(mockView.cancelExitTransitionCalled = true);
		assertTrue(mockView.displayCountryCalled = true);
	}
	public void testOnViewCreated() throws Exception {
		detailPresenter.setCountry("GB", new byte[4]);
		detailPresenter.onViewCreated();
		assertTrue(mockView.displayCountryCalled);
	}
	public void testOnRegionClicked() throws Exception {
		detailPresenter.onRegionClicked(null);
		assertFalse(mockView.finishActivityNoAnimationCalled);
		detailPresenter.onRegionClicked("asia");
		assertTrue(mockView.finishActivityNoAnimationCalled);
	}
	public void testGetTimezonesString() throws Exception {
		CountryModel countryModel = new CountryModel();
		List<String> timezones = null;
		countryModel.setTimezones(timezones);
		assertNotNull(detailPresenter.getTimezonesString(countryModel));

		timezones = new ArrayList<>();
		countryModel.setTimezones(timezones);
		assertNotNull(detailPresenter.getTimezonesString(countryModel));

		timezones.add("12:00 + ABC");
		countryModel.setTimezones(timezones);
		assertTrue(detailPresenter.getTimezonesString(countryModel).compareTo("12:00 + ABC") == 0);

		timezones.add("06:00 C");
		countryModel.setTimezones(timezones);
		assertTrue(detailPresenter.getTimezonesString(countryModel).length() > 11);
	}

	public void testGetLatitudeLongtitudeString() throws Exception {
		CountryModel countryModel = new CountryModel();
		List<Double> longlat = null;
		countryModel.setLatlng(longlat);
		assertNotNull(detailPresenter.getLatitudeLongtitudeString(countryModel));

		longlat = new ArrayList<>();
		countryModel.setLatlng(longlat);
		assertNotNull(detailPresenter.getLatitudeLongtitudeString(countryModel));

		longlat.add(10.0);
		countryModel.setLatlng(longlat);
		assertTrue(detailPresenter.getLatitudeLongtitudeString(countryModel).length() == 0);
	}
}