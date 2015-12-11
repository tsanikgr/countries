package tsanikgr.com.countries.provider;

import android.test.InstrumentationTestCase;

import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;

public class VolleyCountriesProviderTest extends InstrumentationTestCase implements IConnectivityManager {

	VolleyCountriesProvider provider;
	private boolean onCountriesReadyCalled;

	public void setUp() throws Exception {
		provider = new VolleyCountriesProvider(getInstrumentation().getContext(), this);
	}

	@Override
	public boolean isConnected() {
		return true;
	}
	@Override
	public void registerConnectivityChangedReceiver(OnConnectedListener listener) {

	}

	public void testGetCountries() {
		try {
			provider.getCountries(new ICountriesProvider.CountriesListener() {
				@Override
				public void onCountriesReady(CountriesModel countries) {
					assertFalse(countries.isEmpty());
				}
				@Override
				public void onNetworkError() {
					fail();
				}
			});
		} catch (VolleyCountriesProvider.NoNetworkException e) {
			fail();
		}
	}
	public void testGetCountriesOffline() {
		final boolean[] registerConnectivityReceiver = {false};
		provider = new VolleyCountriesProvider(getInstrumentation().getContext(), new IConnectivityManager() {
			@Override
			public boolean isConnected() {
				return false;
			}
			@Override
			public void registerConnectivityChangedReceiver(OnConnectedListener listener) {
				registerConnectivityReceiver[0] = true;
			}
		});
		try {
			provider.getCountries(new ICountriesProvider.CountriesListener() {
				@Override
				public void onCountriesReady(CountriesModel countries) {
					fail();
				}
				@Override
				public void onNetworkError() {
					fail();
				}
			});
		} catch (Exception e) {
			assertTrue(e instanceof VolleyCountriesProvider.NoNetworkException);
		}
		assertTrue(registerConnectivityReceiver[0]);
	}
	public void testGetCountry2Code() throws Exception {
		try {
			provider.getCountry2Code("gb");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof VolleyCountriesProvider.CountriesNotReadyException);
		}

		provider.getCountries(new ICountriesProvider.CountriesListener() {
			@Override
			public void onCountriesReady(CountriesModel countries) {
				try {
					CountryModel country;
					country = provider.getCountry2Code(null);
					assertTrue(country == null);
					country = provider.getCountry2Code("aaaa");
					assertTrue(country == null);
					country = provider.getCountry2Code("GB");
					assertFalse(country == null);
				} catch (VolleyCountriesProvider.CountriesNotReadyException e) {
					fail();
				}
			}
			@Override
			public void onNetworkError() {
				fail();
			}
		});
	}
	public void testGetCountry3Code() throws Exception {
		try {
			provider.getCountry2Code("gb");
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof VolleyCountriesProvider.CountriesNotReadyException);
		}

		provider.getCountries(new ICountriesProvider.CountriesListener() {
			@Override
			public void onCountriesReady(CountriesModel countries) {
				try {
					CountryModel country;
					country = provider.getCountry3Code(null);
					assertTrue(country == null);
					country = provider.getCountry3Code("aaaa");
					assertTrue(country == null);
					country = provider.getCountry3Code("gre");
					assertFalse(country == null);
				} catch (VolleyCountriesProvider.CountriesNotReadyException e) {
					fail();
				}
			}
			@Override
			public void onNetworkError() {
				fail();
			}
		});
	}

	public void testOnConnected() {

		try {
			provider.getCountries(new ICountriesProvider.CountriesListener() {
				@Override
				public void onCountriesReady(CountriesModel countries) {
					testOnConnectedHelper();
				}
				@Override
				public void onNetworkError() {
					fail();
				}
			});

		} catch (VolleyCountriesProvider.NoNetworkException e) {
			fail();
		}
	}

	private void testOnConnectedHelper() {
		onCountriesReadyCalled = false;
		provider.getCountriesWhenConnected(new ICountriesProvider.CountriesListener() {
			@Override
			public void onCountriesReady(CountriesModel countries) {
				onCountriesReadyCalled = true;
			}
			@Override
			public void onNetworkError() {
				fail();
			}
		});
		provider.onConnected();
		assertTrue(onCountriesReadyCalled);
	}
}