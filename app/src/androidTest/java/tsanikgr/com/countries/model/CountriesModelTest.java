package tsanikgr.com.countries.model;

import android.test.AndroidTestCase;

import org.junit.Before;

import java.util.Locale;

public class CountriesModelTest extends AndroidTestCase{

	CountriesModel countries;

	@Before
	public void setUp(){
		countries = new CountriesModel();
	}

	public void testSort() throws Exception {
		CountryModel country1 = getCountryModel("Afghanistan" , "Zzzzghanistan","Afganistán","Afghanistan","アフガニスタン","Afghanistan");
		CountryModel country2 = getCountryModel("Germany","Deutschland","Alemania","Allemagne","ドイツ","Germania");
		CountryModel country3 = getCountryModel("Bat", "Abc", "Afzz", "Afz", null, "");

		countries.add(country1);
		countries.add(country2);
		countries.add(country3);

		testSortLocaleEN();
		testSortLocaleDE();
		testSortLocaleUnkown();
		testSortLocaleNull();
		testSortLocaleEmpty();
	}

	private void testSortLocaleEN() {
		countries.sortByLocalisedCountryName(Locale.ENGLISH);
		assertTrue(countries.get(0).getName().compareTo("Afghanistan") == 0);
		assertTrue(countries.get(1).getName().compareTo("Bat") == 0);
		assertTrue(countries.get(2).getName().compareTo("Germany") == 0);
	}

	private void testSortLocaleDE() {
		countries.sortByLocalisedCountryName(Locale.GERMANY);
		assertTrue(countries.get(0).getName().compareTo("Bat") == 0);
		assertTrue(countries.get(1).getName().compareTo("Germany") == 0);
		assertTrue(countries.get(2).getName().compareTo("Afghanistan") == 0);
	}

	private void testSortLocaleUnkown() {
		countries.sortByLocalisedCountryName(Locale.US);
		assertTrue(countries.get(0).getName().compareTo("Afghanistan") == 0);
		assertTrue(countries.get(1).getName().compareTo("Bat") == 0);
		assertTrue(countries.get(2).getName().compareTo("Germany") == 0);
	}

	private void testSortLocaleNull() {
		try{
			countries.sortByLocalisedCountryName(Locale.JAPAN);
		}catch (Exception e) {
			fail();
			throw e;
		}
	}
	private void testSortLocaleEmpty() {
		try{
			countries.sortByLocalisedCountryName(Locale.ITALY);
		}catch (Exception e) {
			fail();
			throw e;
		}
	}

	private CountryModel getCountryModel(String name, String deName, String esName, String frName, String jaName, String itName) {
		CountryModel country = new CountryModel();
		CountryTranslations translations = new CountryTranslations();
		translations.setDe(deName);
		translations.setEs(esName);
		translations.setFr(frName);
		translations.setJa(jaName);
		translations.setIt(itName);
		country.setTranslations(translations);
		country.setName(name);
		return country;
	}

	public void testGetFrom2Code() throws Exception {
		CountryModel country1 = getCountryModel("Afghanistan" , "Zzzzghanistan","Afganistán","Afghanistan","アフガニスタン","Afghanistan");
		CountryModel country2 = getCountryModel("Germany","Deutschland","Alemania","Allemagne","ドイツ","Germania");
		CountryModel country3 = getCountryModel("Bat", "Abc", "Afzz", "Afz", null, "");
		country1.setAlpha2Code("aa");

		countries.add(country1);
		countries.add(country2);
		countries.add(country3);
		countries.sortByLocalisedCountryName(Locale.GERMANY);

		assertTrue(countries.getFrom2Code("aa") == country1);
	}

	public void testGetFrom3Code() throws Exception {
		CountryModel country1 = getCountryModel("Afghanistan" , "Zzzzghanistan","Afganistán","Afghanistan","アフガニスタン","Afghanistan");
		CountryModel country2 = getCountryModel("Germany","Deutschland","Alemania","Allemagne","ドイツ","Germania");
		CountryModel country3 = getCountryModel("Bat", "Abc", "Afzz", "Afz", null, "");
		country1.setAlpha2Code("aaa");

		countries.add(country1);
		countries.add(country2);
		countries.add(country3);
		countries.sortByLocalisedCountryName(Locale.GERMANY);

		assertTrue(countries.getFrom2Code("aaa") == country1);
	}

	public void testGetRegionSubset() throws Exception {
		CountriesModel europeLowercaseCountries = countries.getRegionSubset("europe");
		for (CountryModel countryModel : europeLowercaseCountries) {
			assertTrue(countryModel.getRegion().toLowerCase().compareTo("europe") == 0);
		}
		CountriesModel asiaCountries = countries.getRegionSubset("Asia");
		for (CountryModel countryModel : asiaCountries) {
			assertTrue(countryModel.getRegion().toLowerCase().compareTo("asia") == 0);
		}
	}
}