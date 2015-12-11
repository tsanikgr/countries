package tsanikgr.com.countries.detail;

import android.graphics.Bitmap;
import android.os.Bundle;

import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;

public interface IDetailPresenter {

	void onCreate(Bundle savedInstanceState);
	void setProvider(ICountriesProvider provider);
	void setCountry(String countryCode, byte[] flag);
	void setCountry(String countryCode, Bitmap flag);
	void onViewCreated();
	void onSaveInstanceState(Bundle outState);
	String getLatitudeLongtitudeString(CountryModel country);
	String getTimezonesString(CountryModel country);
	void onRegionClicked(String region);
	void onCountryBorderClicked(String alpha2Code, Bitmap flagBitmap);
}
