package tsanikgr.com.countries;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import tsanikgr.com.countries.provider.IConnectivityManager;
import tsanikgr.com.countries.provider.ICountriesProvider;
import tsanikgr.com.countries.provider.VolleyCountriesProvider;

public class ApplicationCountries extends Application implements IConnectivityManager {

	private ICountriesProvider countriesProvider;
	private BroadcastReceiver connectivityChangedReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		setCountriesProvider(new VolleyCountriesProvider(this, this));
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		if (connectivityChangedReceiver != null)
			unregisterReceiver(connectivityChangedReceiver);

		countriesProvider.onTerminate();
	}

	public void setCountriesProvider(ICountriesProvider countriesProvider) {
		this.countriesProvider = countriesProvider;
	}

	public ICountriesProvider getProvider() {
		return countriesProvider;
	}

	@Override
	public boolean isConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	@Override
	public void registerConnectivityChangedReceiver(OnConnectedListener listener) {
		if (connectivityChangedReceiver != null) return;

		connectivityChangedReceiver = new ConnectivityChangedReceiver(listener);
		registerReceiver(connectivityChangedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}

	public class ConnectivityChangedReceiver extends BroadcastReceiver {

		private OnConnectedListener listener;

		public ConnectivityChangedReceiver(OnConnectedListener listener){
			this.listener = listener;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			if (isConnected()) listener.onConnected();
		}
	}
}