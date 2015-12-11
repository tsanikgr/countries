package tsanikgr.com.countries.view.detail;

import android.graphics.Bitmap;

public interface IDetailActivity {
	void setToolbarColors(int vibrantColor, int darkVibrantColor, int mutedColor);
	void setCountry(String name, Bitmap flag);
	void cancelExitTransition();
}