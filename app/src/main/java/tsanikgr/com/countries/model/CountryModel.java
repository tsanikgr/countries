package tsanikgr.com.countries.model;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

public class CountryModel{

	private String name;
	private List<String> topLevelDomain;
	private String alpha2Code;
	private String alpha3Code;
	private List<String> currencies;
	private List<String> callingCodes;
	private String capital;
	private List<String> altSpellings;
	private String relevance;
	private String region;
	private String subregion;
	private List<String> languages;
	private CountryTranslations translations;
	private int population;
	private List<Double> latlng;
	private String demonym;
	private double area;
	private double gini;
	private List<String> timezones;
	private List<String> borders;
	private String nativeName;

	public String getName() {
		return name;
	}

	public String getLocalisedName(@NonNull Locale locale){
		String deviceLocale = locale.getLanguage();
		if (deviceLocale == null) return name;
		if (deviceLocale.length() > 2) deviceLocale = deviceLocale.substring(0, 2).toLowerCase();
		if (deviceLocale.compareTo("en") == 0) return name;

		String localisedName = translations.get(deviceLocale);
		return localisedName == null ? name : localisedName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTopLevelDomain() {
		return topLevelDomain;
	}

	public void setTopLevelDomain(List<String> topLevelDomain) {
		this.topLevelDomain = topLevelDomain;
	}

	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	public List<String> getCallingCodes() {
		return callingCodes;
	}

	public void setCallingCodes(List<String> countryCodes) {
		this.callingCodes = countryCodes;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public List<String> getAltSpellings() {
		return altSpellings;
	}

	public void setAltSpellings(List<String> altSpellings) {
		this.altSpellings = altSpellings;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public CountryTranslations getTranslations() {
		return translations;
	}

	public void setTranslations(CountryTranslations translations) {
		this.translations = translations;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getGini() {
		return gini;
	}

	public void setGini(double gini) {
		this.gini = gini;
	}

	public List<String> getTimezones() {
		return timezones;
	}

	public void setTimezones(List<String> timezones) {
		this.timezones = timezones;
	}

	public List<Double> getLatlng() {
		return latlng;
	}

	public void setLatlng(List<Double> latlng) {
		this.latlng = latlng;
	}

	public String getDemonym() {
		return demonym;
	}

	public void setDemonym(String denonym) {
		this.demonym = denonym;
	}

	public List<String> getBorders() {
		return borders;
	}

	public void setBorders(List<String> borders) {
		this.borders = borders;
	}

	public List<String> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	@Override
	public String toString() {
		return getName();
	}
}