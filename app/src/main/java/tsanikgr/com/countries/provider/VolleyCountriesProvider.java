package tsanikgr.com.countries.provider;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import tsanikgr.com.countries.R;
import tsanikgr.com.countries.model.CountriesModel;
import tsanikgr.com.countries.model.CountryModel;
import tsanikgr.com.countries.model.VolleyGsonRequest;
import tsanikgr.com.countries.util.VolleyBitmapLruCache;

public class VolleyCountriesProvider implements ICountriesProvider, IConnectivityManager.OnConnectedListener {

	private static final String BASE_IMAGE_URL = "http://www.geonames.org/flags/x/";
	private static final String COUNTRIES_URL = "https://restcountries-v1.p.mashape.com/all";
	private final RequestQueue requestQueue;
	private final ImageLoader imageLoader;
	private final IConnectivityManager connectivityManager;

	protected CountriesModel countries;
	private CountriesListener listenerToNotifyOnConnected;

	public static class CountriesNotReadyException extends Exception {}
	public static class NoNetworkException extends Exception {}

	public VolleyCountriesProvider(Context context, IConnectivityManager connectivityManager) {
		this.connectivityManager = connectivityManager;
		requestQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(requestQueue, new VolleyBitmapLruCache(context));
		connectivityManager.registerConnectivityChangedReceiver(this);
	}

	@Override
	public void getCountries(final CountriesListener listener) throws NoNetworkException {
		if(!connectivityManager.isConnected())
			throw new NoNetworkException();

		if (countries != null) listener.onCountriesReady(countries);
		else requestQueue.add(getCountriesRequest(listener));
	}

	@Override
	public void requestFlagImage(NetworkImageView imageView, String alpha2Code) {
		imageView.setImageUrl(getImageUrl(alpha2Code),imageLoader);
		imageView.setDefaultImageResId(R.drawable.loading);
		imageView.setErrorImageResId(R.drawable.error);
	}

	private String getImageUrl(String alpha2code) {
		return BASE_IMAGE_URL + alpha2code.toLowerCase() + ".gif";
	}

	@Override
	public CountryModel getCountry2Code(String alpha2Code) throws CountriesNotReadyException {
		if (alpha2Code == null) return null;
		if (countries == null) throw new CountriesNotReadyException();
		return countries.getFrom2Code(alpha2Code);
	}

	@Override
	public CountryModel getCountry3Code(String alpha3Code) throws CountriesNotReadyException {
		if (alpha3Code == null) return null;
		if (countries == null) throw new CountriesNotReadyException();
		return countries.getFrom3Code(alpha3Code);
	}

	@Override
	public void onTerminate() {
		requestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
		requestQueue.stop();
	}

	@Override
	public void getCountriesWhenConnected(CountriesListener listener) {
		listenerToNotifyOnConnected = listener;
	}

	@Override
	public void onConnected() {
		if (listenerToNotifyOnConnected == null) return;
		try {
			getCountries(listenerToNotifyOnConnected);
			listenerToNotifyOnConnected = null;
		} catch (NoNetworkException e) {
			e.printStackTrace();
		}
	}

	public VolleyGsonRequest getCountriesRequest(final CountriesListener listener) {
		return new VolleyGsonRequest<CountriesModel>(COUNTRIES_URL, CountriesModel.class, null,
				new Response.Listener<CountriesModel>(){
					@Override
					public void onResponse(CountriesModel response) {
						if (listener == null) return;
						countries = response;
						listener.onCountriesReady(response);
					}},
				new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onNetworkError();
						error.printStackTrace();
					}}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String>  params = new HashMap<>();
				params.put("X-Mashape-Key", "kEYTa2nUBAmshggF0ot0w6THPMdxp13Dk3jjsndS15jSAXS9R5");
				params.put("Accept", "application/json");

				return params;
			}
		};
	}
}
