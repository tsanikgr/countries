package tsanikgr.com.countries.view.detail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.test.InstrumentationTestCase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import tsanikgr.com.countries.R;
import tsanikgr.com.countries.detail.IDetailPresenter;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.provider.MockProvider;

public class CountryDetailFragmentTest extends InstrumentationTestCase {

	CountryDetailFragment fragment;
	MockPresenter presenter = new MockPresenter();

	class MockPresenter implements IDetailPresenter {
		public boolean onCreateCalled;
		public boolean setCountryCalled;
		public boolean setProviderCalled;
		public boolean onRegionClickedCalled;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			onCreateCalled = true;
		}
		@Override
		public void setProvider(ICountriesProvider provider) {
			setProviderCalled = true;
		}
		@Override
		public void setCountry(String countryCode, byte[] flag) {
			setCountryCalled = true;
		}
		@Override
		public void setCountry(String countryCode, Bitmap flag) {
			setCountryCalled = true;
		}
		@Override
		public void onViewCreated() {

		}
		@Override
		public void onSaveInstanceState(Bundle outState) {

		}
		@Override
		public String getLatitudeLongtitudeString(CountryModel country) {
			return null;
		}
		@Override
		public String getTimezonesString(CountryModel country) {
			return null;
		}
		@Override
		public void onRegionClicked(String region) {
			onRegionClickedCalled = true;
		}
		@Override
		public void onCountryBorderClicked(String alpha2Code, Bitmap flagBitmap) {

		}
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		fragment = new CountryDetailFragment();
		fragment.setPresenter(presenter);
	}

	public void testOnCreateNoSavedInstance() throws Exception {
		fragment.onCreate(null);
		assertTrue(presenter.onCreateCalled);
	}
	public void testOnCreateWithSavedInstance() throws Exception {
		fragment.onCreate(new Bundle());
		assertTrue(presenter.onCreateCalled);
	}

	public void testInit() throws Exception {
		fragment.init(null, "aa", new byte[10]);
		assertTrue(presenter.setProviderCalled);
		assertTrue(presenter.setCountryCalled);
	}

	private ViewGroup prepareAndGetContainer(){
		fragment.onCreate(null);
		fragment.init(null, "gb", new byte[10]);
		final ViewGroup container = (ViewGroup) LayoutInflater.from(getInstrumentation().getTargetContext()).inflate(R.layout.content_detail,null);
		fragment.onCreateView(LayoutInflater.from(getInstrumentation().getTargetContext()), container, null);
		return container;
	}

	public void testDisplayCountry() throws Exception {
		final ViewGroup container = prepareAndGetContainer();
		final MockProvider provider = new MockProvider(getInstrumentation().getContext());
		provider.getCountries(new ICountriesProvider.CountriesListener() {
			@Override
			public void onCountriesReady(CountriesModel countries) {
				Bitmap bitmap = BitmapFactory.decodeResource(getInstrumentation().getTargetContext().getResources(), R.drawable.error);
				CountryModel[] borders = new CountryModel[2];
				borders[0] = countries.getFrom2Code("gb");
				borders[1] = countries.getFrom2Code("");  //null

				fragment.displayCountry(countries.getFrom2Code("GB"), bitmap, borders, provider);

				assertEquals("Athens", ((TextView)container.findViewById(R.id.textView_capital)).getText());
				assertEquals("South Europe", ((TextView)container.findViewById(R.id.textView_sub_region)).getText());
				//....
			}
			@Override
			public void onNetworkError() {
				fail();
			}
		});
	}
	public void testOnRegionClicked() throws Exception {
		final ViewGroup container = prepareAndGetContainer();
		final MockProvider provider = new MockProvider(getInstrumentation().getContext());
		provider.getCountries(new ICountriesProvider.CountriesListener() {
			@Override
			public void onCountriesReady(CountriesModel countries) {
				Bitmap bitmap = BitmapFactory.decodeResource(getInstrumentation().getTargetContext().getResources(), R.drawable.error);
				CountryModel[] borders = new CountryModel[2];
				borders[0] = countries.getFrom2Code("gb");
				borders[1] = countries.getFrom2Code("");  //null

				fragment.displayCountry(countries.getFrom2Code("GB"), bitmap, borders, provider);
				container.findViewById(R.id.region_button).performClick();
			}
			@Override
			public void onNetworkError() {
				fail();
			}
		});
	}
}