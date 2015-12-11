package tsanikgr.com.countries.provider;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;

public class MockProvider implements ICountriesProvider {

	Context context;
	CountriesModel countries;
	public boolean getCountriesCalled;
	public boolean getCountriesWhenConnectedCalled;
	private boolean offline = false;

	/** needs the test assets context to load the json file */
	public MockProvider(Context context) {
		this.context = context;
		countries = null;
	}

	@Override
	public void getCountries(CountriesListener listener) throws VolleyCountriesProvider.NoNetworkException {
		getCountriesCalled = true;

		if (offline) throw new VolleyCountriesProvider.NoNetworkException();
		try {
			if (countries == null)
				getCountriesFromFile();

			listener.onCountriesReady(countries);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void requestFlagImage(NetworkImageView imageView, String alpha2Code) {
	}

	@Override
	public CountryModel getCountry2Code(String alpha2Code) throws VolleyCountriesProvider.CountriesNotReadyException {
		if (countries == null)
			try {
				getCountriesFromFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return countries.getFrom2Code(alpha2Code);
	}

	@Override
	public CountryModel getCountry3Code(String border3Code) throws VolleyCountriesProvider.CountriesNotReadyException {
		if (countries == null)
			try {
				getCountriesFromFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return countries.getFrom3Code(border3Code);
	}

	@Override
	public void onTerminate() {
	}

	@Override
	public void getCountriesWhenConnected(CountriesListener listener) {
		getCountriesWhenConnectedCalled = true;
	}

	private void getCountriesFromFile() throws IOException {
		String jsonString = loadJsonFromFile("countries.json");
		Gson gson = new Gson();
		countries = gson.fromJson(jsonString, CountriesModel.class);
	}

	private String loadJsonFromFile(String fileName) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		InputStream inputStream = context.getAssets().open(fileName);
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

		String str;
		while ((str = in.readLine()) != null) {
			stringBuilder.append(str);
		}

		in.close();
		return stringBuilder.toString();
	}
	public void setOffline(boolean offline) {
		this.offline = offline;
	}
}
