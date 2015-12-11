package tsanikgr.com.countries.view.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import tsanikgr.com.countries.R;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.model.CardViewHolder;

public class ListAdapter extends ArrayAdapter<CountryModel> implements ICountriesAdapter, SectionIndexer{

	private final ICountriesProvider provider;
	Map<Integer, Integer> positionIndexer;
	Map<String, Integer> sectionsMap;
	String[] sections;

	public ListAdapter(Context context, ICountriesProvider provider) {
		super(context, R.layout.card_view_small);
		this.provider = provider;
	}

	@Override
	public void setCountries(CountriesModel countries) {
		clear();
		for (CountryModel country : countries) add(country);
		initSections();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null && convertView.getTag() instanceof CardViewHolder)
			return recycleView(position, convertView);
		else
			return createNewView(position, parent);
	}

	private View createNewView(int position, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		final View cardContainer = inflater.inflate(R.layout.card_view_small, parent,false);
		CardViewHolder viewHolder = new CardViewHolder(cardContainer);
		cardContainer.setTag(viewHolder);
		CountryModel country = getItem(position);
		viewHolder.setTextViews(country);
		provider.requestFlagImage(viewHolder.getImageView(), country.getAlpha2Code());
		return cardContainer;
	}

	private View recycleView(int position, View convertView) {
		CardViewHolder viewHolder = (CardViewHolder) convertView.getTag();
		CountryModel country = getItem(position);
		viewHolder.setTextViews(country);
		provider.requestFlagImage(viewHolder.getImageView(), country.getAlpha2Code());
		return convertView;
	}

	private void initSections() {
		int count = 0;
		sectionsMap = new LinkedHashMap<>();
		positionIndexer = new LinkedHashMap<>();
		for (int i = 0 ; i < getCount() ; i++) {
			CountryModel country = getItem(i);
			String section = country.getLocalisedName(Locale.getDefault()).substring(0,1);
			if (!sectionsMap.containsKey(section)){
				sectionsMap.put(section, i);
				count++;
			}
			positionIndexer.put(i, count);
		}

		sections = new String[count];
		Iterator<String> iterator = sectionsMap.keySet().iterator();
		int position = 0;
		while (iterator.hasNext()) {
			sections[position] = iterator.next();
			position++;
		}
	}

	@Override
	public String[] getSections() {
		return sections;
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		return sectionsMap.get(sections[sectionIndex]);
	}

	@Override
	public int getSectionForPosition(int position) {
//		return positionIndexer.get(position);
		return 1;
	}
}