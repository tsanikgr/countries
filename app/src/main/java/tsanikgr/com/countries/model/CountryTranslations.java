package tsanikgr.com.countries.model;

public class CountryTranslations {

	private String de;
	private String es;
	private String fr;
	private String ja;
	private String it;

	public String get(String countryCode) {
		if (countryCode.compareTo("de") == 0)
			return de;
		else if (countryCode.compareTo("es") == 0)
			return es;
		else if (countryCode.compareTo("fr") == 0)
			return fr;
		else if (countryCode.compareTo("ja") == 0)
			return ja;
		else if (countryCode.compareTo("it") == 0)
			return it;

		return null;
	}

	public String getDe() {
		return de;
	}
	public void setDe(String de) {
		this.de = de;
	}
	public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public String getFr() {
		return fr;
	}
	public void setFr(String fr) {
		this.fr = fr;
	}
	public String getJa() {
		return ja;
	}
	public void setJa(String ja) {
		this.ja = ja;
	}
	public String getIt() {
		return it;
	}
	public void setIt(String it) {
		this.it = it;
	}
}