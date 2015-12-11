package tsanikgr.com.countries.view.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import tsanikgr.com.countries.R;
import tsanikgr.com.countries.detail.DetailPresenter;
import tsanikgr.com.countries.detail.IDetailPresenter;
import tsanikgr.com.countries.detail.IDetailView;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.util.NumUtil;
import tsanikgr.com.countries.model.CardViewHolder;

public class CountryDetailFragment extends Fragment implements IDetailView{

	IDetailPresenter presenter;

	View container;
	LinearLayout bordersLinearLayout;
	Button region;
	TextView subRegion;
	TextView area;
	TextView population;
	TextView gini;
	TextView currency;
	TextView capital;
	TextView longLat;
	TextView timezone;

	public CountryDetailFragment(){
		presenter = new DetailPresenter(this);
	}

	public void setPresenter(IDetailPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter.onCreate(savedInstanceState);
	}

	public void init(ICountriesProvider provider, @NonNull String code, @NonNull Bitmap flagBitmap) {
		presenter.setProvider(provider);
		setCountry(code, flagBitmap);
	}

	public void init(ICountriesProvider provider, @Nullable String code, @Nullable byte[] flagBitmap) {
		presenter.setProvider(provider);
		if (code == null) return;
		setCountry(code, flagBitmap);
	}

	public void setCountry(String countryCode, byte[] flag) {
		presenter.setCountry(countryCode, flag);
	}

	public void setCountry(String countryCode, Bitmap flag) {
		presenter.setCountry(countryCode, flag);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.content_detail, container, false);
		initViews(container, rootView);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presenter.onViewCreated();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		presenter.onSaveInstanceState(outState);
	}

	@Override
	public void finishActivityNoAnimation(Intent intent) {
		getActivity().setResult(Activity.RESULT_OK, intent);
		getActivity().finish();
	}

	@Override
	public void cancelExitTransition() {
		if (getActivity() instanceof IDetailActivity) {
			((IDetailActivity) getActivity()).cancelExitTransition();
		}
	}

	@Override
	public void setToolbarColors(int vibrantColor, int darkVibrantColor, int mutedColor) {
		if (getActivity() instanceof IDetailActivity) {
			((IDetailActivity) getActivity()).setToolbarColors(vibrantColor, darkVibrantColor, mutedColor);
		}
	}

	private void initViews(View container, View rootView) {
		this.container = container;
		bordersLinearLayout = (LinearLayout) rootView.findViewById(R.id.borders_linear_layout);
		region = (Button) rootView.findViewById(R.id.region_button);
		subRegion = (TextView)rootView.findViewById(R.id.textView_sub_region);
		area = (TextView)rootView.findViewById(R.id.textView_area);
		population = (TextView)rootView.findViewById(R.id.textView_population);
		gini = (TextView)rootView.findViewById(R.id.textView_gini);
		currency = (TextView)rootView.findViewById(R.id.textView_currency);
		capital = (TextView)rootView.findViewById(R.id.textView_capital);
		longLat = (TextView)rootView.findViewById(R.id.textView_lat_long);
		timezone = (TextView)rootView.findViewById(R.id.textView_timezones);
	}

	@Override
	public void displayCountry(final CountryModel country, Bitmap flag, CountryModel[] borders, ICountriesProvider provider) {
		setSubRegion(country.getSubregion());
		setArea(String.valueOf(NumUtil.toNumWithThousandsSeparator(country.getArea(), 0)));
		setPopulation(String.valueOf(NumUtil.toNumWithThousandsSeparator(country.getPopulation())));
		setGini(String.valueOf(NumUtil.toNumWithThousandsSeparator(country.getGini(), 2)));
		setCurrency(String.valueOf(country.getCurrencies().get(0)));
		setCapital(String.valueOf(country.getCapital()));
		String latLong = presenter.getLatitudeLongtitudeString(country);
		setLongLat(latLong);
		String timezones = presenter.getTimezonesString(country);
		setTimezone(timezones);
		setRegion(country.getRegion());
		addBorderCountries(borders, provider);

		if (getActivity() instanceof IDetailActivity) {
			((IDetailActivity) getActivity()).setCountry(country.getLocalisedName(Locale.getDefault()), flag);
			container.scrollTo(0, 0);
		}
	}

	private void addBorderCountries(CountryModel[] borders, ICountriesProvider provider) {

		bordersLinearLayout.removeAllViewsInLayout();
		if (borders == null) {
			bordersLinearLayout.setVisibility(View.INVISIBLE);
			container.findViewById(R.id.borders_view).setVisibility(View.INVISIBLE);
			return;
		} else {
			container.findViewById(R.id.borders_view).setVisibility(View.VISIBLE);
			bordersLinearLayout.setVisibility(View.VISIBLE);
		}

		LayoutInflater inflater = LayoutInflater.from(bordersLinearLayout.getContext());
		for (final CountryModel country : borders) {
			if (country == null) continue;
			inflater.inflate(R.layout.card_view_small, bordersLinearLayout);
			final View cardContainer = bordersLinearLayout.getChildAt(bordersLinearLayout.getChildCount()-1);
			final CardViewHolder viewHolder = new CardViewHolder(cardContainer);
			cardContainer.setTag(viewHolder);
			viewHolder.setTextViews(country);
			provider.requestFlagImage(viewHolder.getImageView(), country.getAlpha2Code());
			cardContainer.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					presenter.onCountryBorderClicked(country.getAlpha2Code(), viewHolder.getFlagBitmap());
				}
			});
		}
	}

	private void setRegion(final String region) {
		if (region == null) {
			this.region.setVisibility(View.INVISIBLE);
		} else {
			this.region.setVisibility(View.VISIBLE);
			this.region.setText(region);
			this.region.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					presenter.onRegionClicked(region);
				}
			});
		}
	}
	private void setSubRegion(String subRegion) {
		if (subRegion == null || subRegion.compareTo("")==0) this.subRegion.setText("-");
		else this.subRegion.setText(subRegion);
	}
	private void setArea(String area) {
		if (area == null || area.compareTo("0") == 0) this.area.setText("-");
		else this.area.setText(area);
	}
	private void setPopulation(String population) {
		if (population == null || population.compareTo("0") == 0) this.population.setText("-");
		else this.population.setText(population);
	}
	private void setGini(String gini) {
		if (gini == null || gini.compareTo("0") == 0) this.gini.setText("-");
		else this.gini.setText(gini);
	}
	private void setCurrency(String currency) {
		if (currency == null || currency.compareTo("") == 0) this.currency.setText("-");
		else this.currency.setText(currency);
	}
	private void setCapital(String capital) {
		if (capital == null || capital.compareTo("") == 0) this.capital.setText("-");
		else this.capital.setText(capital);
	}
	private void setLongLat(String longLat) {
		if (longLat == null || longLat.compareTo("") == 0) this.longLat.setText("-");
		else this.longLat.setText(longLat);
	}
	private void setTimezone(String timezone) {
		if (timezone == null || timezone.compareTo("") == 0) this.timezone.setText("-");
		else this.timezone.setText(timezone);
	}
}
