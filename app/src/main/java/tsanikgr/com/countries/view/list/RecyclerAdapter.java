package tsanikgr.com.countries.view.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.R;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.model.CardViewHolder;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardRecyclerViewHolder> implements ICountriesAdapter {

	CountriesModel countries = null;

	private final ICountriesProvider provider;

	public RecyclerAdapter(ICountriesProvider provider) {
		this.provider = provider;
	}

	@Override
	public void setCountries(CountriesModel countries) {
		this.countries = countries;
		notifyDataSetChanged();
	}

	@Override
	public CardRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View cardView = inflater.inflate(R.layout.card_view, parent, false);
		return new CardRecyclerViewHolder(cardView);
	}

	@Override
	public void onBindViewHolder(CardRecyclerViewHolder holder, int position) {
		holder.set(countries.get(position), provider);
	}

	@Override
	public int getItemCount() {
		return countries == null ? 0 : countries.size();
	}

	public static class CardRecyclerViewHolder extends RecyclerView.ViewHolder {

		private final CardViewHolder cardHolder;
		public CardRecyclerViewHolder(View itemView) {
			super(itemView);
			this.cardHolder = new CardViewHolder(itemView);
			itemView.setTag(cardHolder);
		}

		public void set(CountryModel country, ICountriesProvider provider) {
			cardHolder.setTextViews(country);
			provider.requestFlagImage(cardHolder.getImageView(), country.getAlpha2Code());
		}
	}
}
