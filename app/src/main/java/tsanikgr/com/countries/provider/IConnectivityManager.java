package tsanikgr.com.countries.provider;

public interface IConnectivityManager {

	interface OnConnectedListener{
		void onConnected();
	}

	boolean isConnected();
	void registerConnectivityChangedReceiver(OnConnectedListener listener);
}
