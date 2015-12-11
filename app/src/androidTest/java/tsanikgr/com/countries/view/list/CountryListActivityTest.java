package tsanikgr.com.countries.view.list;

import android.support.annotation.NonNull;
import android.test.UiThreadTest;

import tsanikgr.com.countries.ActivityUnitTestCase;
import tsanikgr.com.countries.list.IListView;
import tsanikgr.com.countries.list.ListPresenter;
import tsanikgr.com.countries.model.CardViewHolder;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;

public class CountryListActivityTest extends ActivityUnitTestCase<CountryListActivity> {

	CountryListActivity activity = null;
	MockListPresenter presenter;
	ICountriesProvider provider;

	public CountryListActivityTest() {
		super(CountryListActivity.class);
	}

	@UiThreadTest
	public void setUp() throws Exception {
		super.setUp();
//		ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getContext(), R.style.AppTheme_PopupOverlay);
//		setActivityContext(context);
	}

	private void prepare() {
//		Intent intent = new Intent(getInstrumentation().getTargetContext(), CountryListActivity.class);
//		startActivity(intent, null, null);
//
//		activity = getActivity();
//		assertNotNull(activity);
//
//		provider = new MockProvider(getInstrumentation().getContext());
//		activity.init(null, presenter = new MockListPresenter(activity, provider));
	}

//	public void testShowCountries() throws Exception {
//
//	}

	public void testShowCountryDetail() throws Exception {
//		prepare();
//		final Context context = getInstrumentation().getTargetContext();
//		View cardView = LayoutInflater.from(context).inflate(R.layout.card_view_small, null);
//		final CardViewHolder holder = new CardViewHolder(cardView);
//		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.error);
//		BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
//		holder.getImageView().setImageDrawable(bitmapDrawable);
//		provider.getCountries(new ICountriesProvider.CountriesListener() {
//			@Override
//			public void onCountriesReady(CountriesModel countries) {
////					testLaunchOfDetailActivity(holder);
//			}
//			@Override
//			public void onNetworkError() {
//			}
//		});
	}

	private void testLaunchOfDetailActivity(CardViewHolder holder) {
//		prepare();
//		getActivity().showCountryDetail(provider, holder);
//		final Instrumentation.ActivityMonitor receiverActivityMonitor =
//				getInstrumentation().addMonitor(CountryDetailActivity.class.getName(),
//						null, false);
//		CountryDetailActivity receiverActivity = (CountryDetailActivity)
//				receiverActivityMonitor.waitForActivityWithTimeout(2000);
//		assertNotNull("ReceiverActivity is null", receiverActivity);
//		assertEquals("Monitor for ReceiverActivity has not been called",
//				1, receiverActivityMonitor.getHits());
//		assertEquals("Activity is of wrong type",
//				CountryDetailActivity.class, receiverActivity.getClass());
//
//		getInstrumentation().removeMonitor(receiverActivityMonitor);
	}


	public void testShowNetworkError() throws Exception {
//		prepare();
//		activity.showNetworkError();
//		assertNotNull(activity.findViewById(R.id.network_error_view));
	}
	public void testShowLoadingProgressBar() throws Exception {
//		prepare();
//		activity.showLoadingProgressBar();
//		assertNotNull(activity.findViewById(R.id.loading_bar_layout));
	}

	public void testOnActivityResult() throws Exception {
//		prepare();
//		Intent data = new Intent();
//		data.putExtra(Constants.SELECTED_REGION, "Aa");
//		activity.onActivityResult(Constants.DETAIL_REQUEST_CODE, Activity.RESULT_OK, data);
//		assertTrue(presenter.onRegionSelectedCalled);
	}

	private class MockListPresenter extends ListPresenter {
		public boolean onRegionSelectedCalled;
		public MockListPresenter(@NonNull IListView view, @NonNull ICountriesProvider provider) {
			super(view, provider);
		}
		@Override
		public void requestCountries() {

		}
		@Override
		public void onCountriesReady(CountriesModel countries) {

		}
		@Override
		public void onNetworkError() {

		}
		@Override
		public void onCountryClicked(CardViewHolder cardHolder) {

		}
		@Override
		public void onRegionSelected(String region) {
			onRegionSelectedCalled = true;
		}
		@Override
		public void onBackPressed() {

		}
	}
}