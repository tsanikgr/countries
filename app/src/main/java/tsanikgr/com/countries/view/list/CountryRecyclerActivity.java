package tsanikgr.com.countries.view.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tsanikgr.com.countries.R;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.provider.ICountriesProvider;

public class CountryRecyclerActivity extends CountryListActivity {

	@Override
	public void showCountries(CountriesModel countries, ICountriesProvider provider) {
		if (findViewById(R.id.countries_recycler_view) == null)
			setActiveView(R.layout.content_recycler_view);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.countries_recycler_view);

		RecyclerAdapter adapter = (RecyclerAdapter)recyclerView.getAdapter();
		if (adapter == null) adapter = new RecyclerAdapter(provider);
		adapter.setCountries(countries);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected View findActiveView() {
		View activeView;
		if ((activeView = findViewById(R.id.countries_recycler_view)) != null) return activeView;
		return super.findActiveView();
	}

	@Override
	public void setScrollY(int scrollY) {
		View active = findActiveView();
		if (active == null || active.getId() != R.id.countries_recycler_view) return;
		active.scrollTo(active.getScrollX(), scrollY);
		super.setScrollY(scrollY);
	}

	@Override
	protected void saveListPosition() {
		View active = findActiveView();
		if (active == null || active.getId() != R.id.countries_list_view) return;

		int scrollY = active.getScrollY();
		Bundle state = new Bundle();
		state.putInt(LIST_POSITION, scrollY);
		onSaveInstanceState(state);

		super.saveListPosition();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_countries_list, menu);
//		menu.findItem(R.id.menu_item_use_recycler).setVisible(false);
//		menu.findItem(R.id.menu_item_use_list).setVisible(true);
//		return super.onCreateOptionsMenu(menu);
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.menu_item_use_list) {
//			presenter.useListView();
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
