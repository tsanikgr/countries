package tsanikgr.com.countries.model;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountriesModel extends ArrayList<CountryModel> implements Comparator<CountryModel> {

	Map<String, Integer> code2ToIndex;
	Map<String, Integer> code3ToIndex;
	Locale locale;

	public CountriesModel() {
		locale = Locale.getDefault();
		code3ToIndex = new HashMap<>();
		code2ToIndex = new HashMap<>();
	}

	@Override
	public boolean add(CountryModel object) {
		code2ToIndex.put(object.getAlpha2Code(), size());
		code3ToIndex.put(object.getAlpha3Code(), size());
		return super.add(object);
	}

	public void sortByLocalisedCountryName(Locale locale) {
		this.locale = locale;
		Collections.sort(this, this);
		code2ToIndex.clear();
		code3ToIndex.clear();
		for (int i = 0; i < size(); i++) {
			code2ToIndex.put(get(i).getAlpha2Code(), i);
			code3ToIndex.put(get(i).getAlpha3Code(), i);
		}
	}

	public CountryModel getFrom2Code(String alpha2code) {
		if (alpha2code == null) return null;
		return code2ToIndex.get(alpha2code) == null ? null : get(code2ToIndex.get(alpha2code));
	}

	public CountryModel getFrom3Code(String alpha3code) {
		if (alpha3code == null) return null;
		return code3ToIndex.get(alpha3code) == null ? null : get(code3ToIndex.get(alpha3code));
	}

	public CountriesModel getRegionSubset(String countriesRegionFilter) {
		CountriesModel subset = new CountriesModel();

		for (CountryModel country : this) {
			if (country.getRegion().toLowerCase().compareTo(countriesRegionFilter.toLowerCase()) == 0)
				subset.add(country);
		}
		return subset;
	}

	@Override
	public int compare(CountryModel lhs, CountryModel rhs) {
		Collator collator = Collator.getInstance(locale);
		collator.setStrength(Collator.PRIMARY);
		return collator.compare(lhs.getLocalisedName(locale), rhs.getLocalisedName(locale));
	}
}
