package tsanikgr.com.countries.detail;

import android.content.Intent;
import android.graphics.Bitmap;

import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;

public class MockDetailView implements IDetailView {

	public boolean setToolbarsCalled, displayCountryCalled, finishActivityNoAnimationCalled, cancelExitTransitionCalled;
	@Override
	public void setToolbarColors(int vibrantColor, int darkVibrantColor, int mutedColor) {
		setToolbarsCalled = true;
	}
	@Override
	public void displayCountry(CountryModel country, Bitmap countryFlag, CountryModel[] borders, ICountriesProvider provider) {
		displayCountryCalled = true;
	}
	@Override
	public void finishActivityNoAnimation(Intent intent) {
		finishActivityNoAnimationCalled = true;
	}
	@Override
	public void cancelExitTransition() {
		cancelExitTransitionCalled = true;
	}
}
