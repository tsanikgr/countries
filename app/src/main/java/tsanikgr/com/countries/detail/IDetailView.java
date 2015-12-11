package tsanikgr.com.countries.detail;

import android.content.Intent;
import android.graphics.Bitmap;

import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;

public interface IDetailView {
	void setToolbarColors(int vibrantColor, int darkVibrantColor, int mutedColor);
	void displayCountry(CountryModel country, Bitmap countryFlag, CountryModel[] borders, ICountriesProvider provider);
	void finishActivityNoAnimation(Intent intent);
	void cancelExitTransition();
}
