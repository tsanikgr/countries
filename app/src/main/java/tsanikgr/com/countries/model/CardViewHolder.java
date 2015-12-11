package tsanikgr.com.countries.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Locale;

import tsanikgr.com.countries.R;
import tsanikgr.com.countries.util.NumUtil;

public class CardViewHolder {

	NetworkImageView imageView;
	TextView name;
	TextView population;
	TextView region;
	private String code;

	public CardViewHolder(View itemView) {
		imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_flag);
		name = (TextView) itemView.findViewById(R.id.textView_country_name);
		region = (TextView) itemView.findViewById(R.id.textView_region);
		population = (TextView) itemView.findViewById(R.id.textView_population);

		if (itemView instanceof CardView) initCardView((CardView) itemView);
		else if (itemView instanceof FrameLayout) initCardView((CardView)((FrameLayout) itemView).getChildAt(0));
	}

	private void initCardView(CardView cardView) {
		cardView.setCardElevation(4f);
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			cardView.setPreventCornerOverlap(false);
		}
	}


	public void setTextViews(final CountryModel countryModel) {
		code = countryModel.getAlpha2Code();
		name.setText(countryModel.getLocalisedName(Locale.getDefault()));
		region.setText(countryModel.getRegion());
		population.setText(NumUtil.toNumWithThousandsSeparator(countryModel.getPopulation()));
	}

	public String getName() {
		return name.getText().toString();
	}

	public String getCode() {
		return code;
	}

	public Bitmap getFlagBitmap() {
		if (imageView != null && imageView.getDrawable() instanceof BitmapDrawable) {
			return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
		}
		return null;
	}

	public NetworkImageView getImageView() {
		return imageView;
	}
}