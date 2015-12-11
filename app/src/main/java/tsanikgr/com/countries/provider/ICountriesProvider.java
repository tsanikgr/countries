package tsanikgr.com.countries.provider;

import com.android.volley.toolbox.NetworkImageView;

import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;

public interface ICountriesProvider {

	interface CountriesListener {
		void onCountriesReady(CountriesModel countries);
		void onNetworkError();
	}

	void getCountries(CountriesListener listener) throws VolleyCountriesProvider.NoNetworkException;
	void getCountriesWhenConnected(CountriesListener listener);
	void requestFlagImage(NetworkImageView imageView, String alpha2Code);
	void onTerminate();
	CountryModel getCountry2Code(String alpha2Code) throws VolleyCountriesProvider.CountriesNotReadyException;
	CountryModel getCountry3Code(String border3Code) throws VolleyCountriesProvider.CountriesNotReadyException;
}
