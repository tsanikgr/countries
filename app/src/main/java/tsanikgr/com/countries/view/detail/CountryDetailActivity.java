package tsanikgr.com.countries.view.detail;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import tsanikgr.com.countries.ApplicationCountries;
import tsanikgr.com.countries.Constants;
import tsanikgr.com.countries.R;
import tsanikgr.com.countries.provider.ICountriesProvider;

public class CountryDetailActivity extends AppCompatActivity implements IDetailActivity {

	private boolean hasPostponedTransition;
	private boolean returnTransitionCancelled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
			hasPostponedTransition = true;
			postponeEnterTransition();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country_detail);
		init();

		CountryDetailFragment fragment;
		if (savedInstanceState == null)
			fragment = createFragment();  // fragment needs to be created & added manually to the container *only* the first time
		else
			fragment = (CountryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.country_detail_container);

		ICountriesProvider provider = getProvider();
		initialiseFragment(fragment, provider);
	}

	private ICountriesProvider getProvider() {
		try {
			ApplicationCountries applicationCountries = (ApplicationCountries) getApplication();
			return applicationCountries.getProvider();
		}catch (ClassCastException e) {
			//ignore, only for testing
		}
		return null;
	}

	private void init() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) return;
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private CountryDetailFragment createFragment() {
		CountryDetailFragment fragment = new CountryDetailFragment();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.country_detail_container, fragment)
				.commit();
		return fragment;
	}

	public void initialiseFragment(CountryDetailFragment fragment, ICountriesProvider provider) {
		String countryCode = getIntent().getStringExtra(Constants.COUNTRY_CODE);
		byte[] flag = getIntent().getByteArrayExtra(Constants.COUNTY_FLAG_BITMAP);
		getIntent().removeExtra(Constants.COUNTRY_CODE);
		getIntent().removeExtra(Constants.COUNTY_FLAG_BITMAP);
		fragment.init(provider, countryCode, flag);
	}

	@Override
	public void setToolbarColors(int vibrantColor, int darkVibrantColor, int mutedColor){
		CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
		toolbar.setContentScrimColor(vibrantColor);
		toolbar.setExpandedTitleColor(mutedColor);
		toolbar.setCollapsedTitleTextColor(mutedColor);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			toolbar.setStatusBarScrimColor(darkVibrantColor);
		}
	}

	@Override
	public void setCountry(String name, Bitmap flag) {
		CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
		if (collapsingToolbarLayout == null) return;

		expandToolbar((AppBarLayout) collapsingToolbarLayout.getParent());
		collapsingToolbarLayout.setTitle(name);

		if (flag == null) return;
		ImageView view = (ImageView)findViewById(R.id.collapsing_flag);
		view.setImageBitmap(flag);

		if (hasPostponedTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			startPostponedEnterTransition();
			hasPostponedTransition = false;
		}
	}

	@Override
	public void cancelExitTransition() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setSharedElementReturnTransition(null);
			returnTransitionCancelled = true;
		}
	}

	private void expandToolbar(AppBarLayout appBarLayout) {
		CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
		AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
		if(behavior!=null) {
			behavior.setTopAndBottomOffset(0);
			behavior.onNestedPreScroll((CoordinatorLayout) appBarLayout.getParent(), appBarLayout, null, 0, 1, new int[2]);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			if (returnTransitionCancelled) finish();
			else supportFinishAfterTransition();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		if (returnTransitionCancelled) finish();
		else super.onBackPressed();
	}
}
