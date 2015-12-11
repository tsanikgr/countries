package tsanikgr.com.countries.util;

import android.test.AndroidTestCase;

public class NumUtilTest extends AndroidTestCase {

	public void testToNumWithThousandsSeparator() throws Exception {
		assertTrue(NumUtil.toNumWithThousandsSeparator(10000.0,2).compareTo("10,000") == 0);
		assertTrue(NumUtil.toNumWithThousandsSeparator(-1000.023, 2).compareTo("-1,000.02") == 0);
	}
	public void testToNumWithThousandsSeparator1() throws Exception {
		assertTrue(NumUtil.toNumWithThousandsSeparator(10000).compareTo("10,000") == 0);
		assertTrue(NumUtil.toNumWithThousandsSeparator(-1000).compareTo("-1,000") == 0);
	}
}