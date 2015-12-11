package tsanikgr.com.countries.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;

import tsanikgr.com.countries.Constants;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.provider.VolleyCountriesProvider;
import tsanikgr.com.countries.util.NumUtil;

public class DetailPresenter implements IDetailPresenter, Palette.PaletteAsyncListener{
	private static final String TAG = "Detail Presenter";
	private final WeakReference<IDetailView> view;
	private ICountriesProvider provider;

	private String countryCode;
	private Bitmap countryFlag;

	public DetailPresenter(@NonNull IDetailView view){
		this.view = new WeakReference<>(view);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState == null) return;
		countryCode = savedInstanceState.getString(Constants.COUNTRY_CODE);
		countryFlag = getBitmapFromByteArray(savedInstanceState.getByteArray(Constants.COUNTY_FLAG_BITMAP));
		if (countryCode == null || countryFlag == null) {
			countryCode = null;
			countryFlag = null;
		}
	}

	@Override
	public void setProvider(ICountriesProvider provider) {
		this.provider = provider;
	}

	@Override
	public void setCountry(String countryCode, Bitmap flag) {
		this.countryCode = countryCode;
		this.countryFlag = flag;
		if (countryCode == null) countryFlag = null;
	}

	@Override
	public void setCountry(String countryCode, byte[] flag) {
		this.countryCode = countryCode;
		this.countryFlag = getBitmapFromByteArray(flag);
		if (countryCode == null) countryFlag = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(Constants.COUNTRY_CODE, countryCode);
		outState.putByteArray(Constants.COUNTY_FLAG_BITMAP, getByteArrayFromBitmap(countryFlag));
	}

	@Override
	public void onCountryBorderClicked(String alpha2Code, Bitmap flagBitmap) {
		view.get().cancelExitTransition();
		setCountry(alpha2Code, flagBitmap);
		updateView();
	}

	@Override
	public void onViewCreated() {
		updateView();
	}

	private void updateView() {
		IDetailView view = this.view.get();
		if (view == null || provider == null) return;
		CountryModel country;
		try {
			country = provider.getCountry2Code(countryCode);
		} catch (VolleyCountriesProvider.CountriesNotReadyException e) {
			e.printStackTrace();
			return;
		}
		if (country == null) {
			Log.e(TAG, "updateView: Country is null");
			return;
		}

		CountryModel[] borders = getBorders(country);
		view.displayCountry(country, countryFlag, borders, provider);
		if (countryFlag != null)
			Palette.from(countryFlag).generate(this);
	}

	private CountryModel[] getBorders(CountryModel country) {
		List<String> borderStrings = country.getBorders();
		if (borderStrings == null || borderStrings.isEmpty()) return null;
		CountryModel[] borders = new CountryModel[borderStrings.size()];

		try {
			int i = 0;
			for (String border3Code : borderStrings){
				CountryModel countryModel = provider.getCountry3Code(border3Code);
				borders[i] = countryModel;
				i++;
			}
		} catch (VolleyCountriesProvider.CountriesNotReadyException e) {
			e.printStackTrace();
			return null;
		}
		return borders;
	}

	@Override
	public void onGenerated(Palette palette) {
		IDetailView view = this.view.get();
		if (view == null || palette == null) return;

		view.setToolbarColors(
				palette.getVibrantColor(Color.BLUE),
				palette.getDarkVibrantColor(Color.BLUE),
				palette.getLightMutedColor(Color.WHITE));
	}

	private Bitmap getBitmapFromByteArray(byte[] flag) {
		if (flag == null) return null;
		return BitmapFactory.decodeByteArray(flag, 0, flag.length);
	}

	private byte[] getByteArrayFromBitmap(Bitmap countryFlag) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		countryFlag.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	@Override
	public void onRegionClicked(String region) {
		if (region == null) return;

		Intent intent = new Intent();
		intent.putExtra(Constants.SELECTED_REGION, region);
		view.get().finishActivityNoAnimation(intent);
	}

	@NonNull
	public String getTimezonesString(CountryModel country) {
		List<String> timezones = country.getTimezones();
		if (timezones == null || timezones.size() == 0) return "";
		if (timezones.size() == 1) return timezones.get(0);
		return timezones.get(0) + " - " + timezones.get(timezones.size()-1);
	}

	@NonNull
	public String getLatitudeLongtitudeString(CountryModel country) {
		List<Double> latlong = country.getLatlng();
		if (latlong == null || latlong.size() != 2) return "";
		return NumUtil.toNumWithThousandsSeparator(latlong.get(0),1) + " / "
				+ NumUtil.toNumWithThousandsSeparator(latlong.get(1), 1);
	}
}
