package tsanikgr.com.countries.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumUtil {

	public static String toNumWithThousandsSeparator(int num) {
		DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(Locale.getDefault());
		nf.applyPattern("###,###");
		return nf.format(num);
	}

	public static String toNumWithThousandsSeparator(double num, int decimal) {
		StringBuilder pattern = new StringBuilder("###,###");

		if (decimal > 0) pattern.append(".");
		while (decimal > 0) {
			pattern.append("#");
			decimal--;
		}

		DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(Locale.getDefault());
		nf.applyPattern(pattern.toString());
		return nf.format(num);
	}
}
