package tsanikgr.com.countries.view.list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;

import tsanikgr.com.countries.ApplicationCountries;
import tsanikgr.com.countries.Constants;
import tsanikgr.com.countries.R;
import tsanikgr.com.countries.list.IListPresenter;
import tsanikgr.com.countries.list.IListView;
import tsanikgr.com.countries.list.ListPresenter;
import tsanikgr.com.countries.model.CardViewHolder;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.view.detail.CountryDetailActivity;
import tsanikgr.com.countries.view.detail.CountryDetailFragment;

public class CountryListActivity extends AppCompatActivity implements IListView {
	protected static final String LIST_POSITION = "ListPosition";
	protected IListPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
		super.onCreate(savedInstanceState);

		IListPresenter presenter = new ListPresenter(this, getProvider());
		init(savedInstanceState, presenter);
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

	public void init(Bundle savedInstanceState, IListPresenter presenter) {
		setContentView(R.layout.activity_countries_list);
		setDefaultTitle();
		showLoadingProgressBar();
		this.presenter = presenter;
		this.presenter.requestCountries();

		restoreListPosition(savedInstanceState);
	}

	@Override
	protected void onPause() {
		saveListPosition();
		super.onPause();
	}

	@Override
	public void showCountries(CountriesModel countries, ICountriesProvider provider) {
		if (findViewById(R.id.countries_list_view) == null)
			setActiveView(R.layout.content_list_view);
		ListView listView = (ListView) findViewById(R.id.countries_list_view);

		ListAdapter adapter = (ListAdapter)listView.getAdapter();
		if (adapter == null) adapter = new ListAdapter(this, provider);
		adapter.setCountries(countries);
		listView.setAdapter(adapter);
	}

	@Override
	public void showCountryDetail(ICountriesProvider provider, CardViewHolder cardHolder) {
		boolean hasTwoPanes = findViewById(R.id.country_detail_container) != null;
		if (hasTwoPanes) showCountryDetailInFragment(provider, cardHolder);
		else startDetailActivity(cardHolder);
	}

	@Override
	public void showNetworkError() {
		if (findViewById(R.id.network_error_view) == null)
			setActiveView(R.layout.network_error_view);
	}

	@Override
	public void showLoadingProgressBar() {
		if (findViewById(R.id.loading_bar_layout) == null)
			setActiveView(R.layout.loading_progress_bar);
	}

	@Override
	public void showBackButton(boolean show) {
		ActionBar actionBar = getSupportActionBar();
		if (actionBar == null) return;
		actionBar.setDisplayHomeAsUpEnabled(show);
	}

	@Override
	public void setTitle(String title) {
		if (title == null) title = getTitle().toString();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle(title);
	}

	@Override
	public void setDefaultTitle() {
		setTitle(getTitle().toString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.DETAIL_REQUEST_CODE && data != null) {
			presenter.onRegionSelected(data.getStringExtra(Constants.SELECTED_REGION));
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
//		if (id == R.id.menu_item_use_recycler) {
//			presenter.useRecyclerView();
//		} else
		if (id == android.R.id.home) {
			presenter.onBackPressed();
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		presenter.onBackPressed();
	}

	@Override
	public void finishActivity() {
		super.finish();
	}

	public void onCountryClicked(View view) {
		presenter.onCountryClicked((CardViewHolder) view.getTag());
	}

	protected View findActiveView() {
		View activeView;
		if ((activeView = findViewById(R.id.countries_list_view)) != null) return activeView;
		if ((activeView = findViewById(R.id.loading_bar_layout)) != null) return activeView;
		if ((activeView = findViewById(R.id.network_error_view)) != null) return activeView;
		return null;
	}

	protected void setActiveView(int newViewLayoutId) {
		View activeView = findActiveView();
		if (activeView == null) return;

		ViewGroup parent = (ViewGroup) activeView.getParent();
		int index = parent.indexOfChild(activeView);
		parent.removeView(activeView);
		View newView = getLayoutInflater().inflate(newViewLayoutId, parent, false);
		parent.addView(newView, index);
	}

	private void startDetailActivity(CardViewHolder cardHolder) {
		Intent intent = getDetailActivityIntent(cardHolder.getCode(), cardHolder.getFlagBitmap());
		String sharedName = getString(R.string.transition_image);
		Bundle transitionBundle = null;
		if (cardHolder.getImageView() != null) {
			Pair<View, String> pair = new Pair<>((View) cardHolder.getImageView(), sharedName);
			transitionBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair).toBundle();
		}
		ActivityCompat.startActivityForResult(this, intent, Constants.DETAIL_REQUEST_CODE, transitionBundle);
	}

	public void showCountryDetailInFragment(ICountriesProvider provider, CardViewHolder cardHolder) {
		CountryDetailFragment fragment = new CountryDetailFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.country_detail_container, fragment)
				.commit();

		fragment.init(provider, cardHolder.getCode(), cardHolder.getFlagBitmap());
	}

	private Intent getDetailActivityIntent(String code, Bitmap flag) {
		Intent intent = new Intent(this, CountryDetailActivity.class);
		intent.putExtra(Constants.COUNTRY_CODE, code);
		if (flag != null) addBitmapToIntent(intent, flag);
		return intent;
	}

	private void addBitmapToIntent(Intent intent, Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		intent.putExtra(Constants.COUNTY_FLAG_BITMAP, byteArray);
	}

	public void restoreListPosition(Bundle savedInstanceState) {
		if (savedInstanceState == null) return;
		int scrollY = savedInstanceState.getInt(LIST_POSITION);
		setScrollY(scrollY);
	}

	public void setScrollY(int scrollY) {
		View active = findActiveView();
		if (active == null || active.getId() != R.id.countries_list_view) return;
		active.scrollTo(active.getScrollX(), scrollY);
	}

	protected void saveListPosition() {
		View active = findActiveView();
		if (active == null || active.getId() != R.id.countries_list_view) return;

		int scrollY = active.getScrollY();
		Bundle state = new Bundle();
		state.putInt(LIST_POSITION, scrollY);
		onSaveInstanceState(state);
	}

	//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_countries_list, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
}
