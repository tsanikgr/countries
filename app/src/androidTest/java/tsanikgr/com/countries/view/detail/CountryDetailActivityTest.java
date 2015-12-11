package tsanikgr.com.countries.view.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.test.UiThreadTest;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;

import tsanikgr.com.countries.ActivityUnitTestCase;
import tsanikgr.com.countries.R;

public class CountryDetailActivityTest extends ActivityUnitTestCase<CountryDetailActivity> {

	CountryDetailActivity activity;

	public CountryDetailActivityTest() {
		super(CountryDetailActivity.class);
	}
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme_NoActionBar);
		setActivityContext(context);
	}
	@UiThreadTest
	public void testOnCreateFirstTime() throws Exception {
		Intent intent = new Intent(getInstrumentation().getTargetContext(), CountryDetailActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
		assertNotNull(activity.findViewById(R.id.country_detail_container));
	}

	public void testActivityWithSavedInstancebundle(){
	}

	public void testSetCountry() throws Exception {
		Intent intent = new Intent(getInstrumentation().getTargetContext(), CountryDetailActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
		activity.setCountry("Greece", BitmapFactory.decodeResource(getInstrumentation().getTargetContext().getResources(), R.drawable.error));
		CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
		assertEquals("Greece", collapsingToolbarLayout.getTitle());
		Bitmap bitmap = ((BitmapDrawable)((ImageView) activity.findViewById(R.id.collapsing_flag)).getDrawable()).getBitmap();
		assertNotNull(bitmap);
	}
	public void testOnOptionsItemSelected() throws Exception {
		Intent intent = new Intent(getInstrumentation().getTargetContext(), CountryDetailActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
		activity.onOptionsItemSelected(backButton);

		assertTrue(isFinishCalled());
	}
	public void testOnBackPressed() throws Exception {
		Intent intent = new Intent(getInstrumentation().getTargetContext(), CountryDetailActivity.class);
		startActivity(intent, null, null);
		activity = getActivity();
		activity.onBackPressed();

		assertTrue(isFinishCalled());
	}

	MenuItem backButton = new MenuItem() {
		@Override
		public int getItemId() {
			return android.R.id.home;
		}
		@Override
		public int getGroupId() {
			return 0;
		}
		@Override
		public int getOrder() {
			return 0;
		}
		@Override
		public MenuItem setTitle(CharSequence title) {
			return null;
		}
		@Override
		public MenuItem setTitle(int title) {
			return null;
		}
		@Override
		public CharSequence getTitle() {
			return null;
		}
		@Override
		public MenuItem setTitleCondensed(CharSequence title) {
			return null;
		}
		@Override
		public CharSequence getTitleCondensed() {
			return null;
		}
		@Override
		public MenuItem setIcon(Drawable icon) {
			return null;
		}
		@Override
		public MenuItem setIcon(int iconRes) {
			return null;
		}
		@Override
		public Drawable getIcon() {
			return null;
		}
		@Override
		public MenuItem setIntent(Intent intent) {
			return null;
		}
		@Override
		public Intent getIntent() {
			return null;
		}
		@Override
		public MenuItem setShortcut(char numericChar, char alphaChar) {
			return null;
		}
		@Override
		public MenuItem setNumericShortcut(char numericChar) {
			return null;
		}
		@Override
		public char getNumericShortcut() {
			return 0;
		}
		@Override
		public MenuItem setAlphabeticShortcut(char alphaChar) {
			return null;
		}
		@Override
		public char getAlphabeticShortcut() {
			return 0;
		}
		@Override
		public MenuItem setCheckable(boolean checkable) {
			return null;
		}
		@Override
		public boolean isCheckable() {
			return false;
		}
		@Override
		public MenuItem setChecked(boolean checked) {
			return null;
		}
		@Override
		public boolean isChecked() {
			return false;
		}
		@Override
		public MenuItem setVisible(boolean visible) {
			return null;
		}
		@Override
		public boolean isVisible() {
			return false;
		}
		@Override
		public MenuItem setEnabled(boolean enabled) {
			return null;
		}
		@Override
		public boolean isEnabled() {
			return false;
		}
		@Override
		public boolean hasSubMenu() {
			return false;
		}
		@Override
		public SubMenu getSubMenu() {
			return null;
		}
		@Override
		public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
			return null;
		}
		@Override
		public ContextMenu.ContextMenuInfo getMenuInfo() {
			return null;
		}
		@Override
		public void setShowAsAction(int actionEnum) {

		}
		@Override
		public MenuItem setShowAsActionFlags(int actionEnum) {
			return null;
		}
		@Override
		public MenuItem setActionView(View view) {
			return null;
		}
		@Override
		public MenuItem setActionView(int resId) {
			return null;
		}
		@Override
		public View getActionView() {
			return null;
		}
		@Override
		public MenuItem setActionProvider(ActionProvider actionProvider) {
			return null;
		}
		@Override
		public ActionProvider getActionProvider() {
			return null;
		}
		@Override
		public boolean expandActionView() {
			return false;
		}
		@Override
		public boolean collapseActionView() {
			return false;
		}
		@Override
		public boolean isActionViewExpanded() {
			return false;
		}
		@Override
		public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
			return null;
		}
	};
}