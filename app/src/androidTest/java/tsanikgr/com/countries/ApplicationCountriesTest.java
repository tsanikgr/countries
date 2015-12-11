package tsanikgr.com.countries;

import android.test.ApplicationTestCase;

public class ApplicationCountriesTest extends ApplicationTestCase<ApplicationCountries> {

	ApplicationCountries applicationCountries;

	public ApplicationCountriesTest() {
		super(ApplicationCountries.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void testOnCreate() throws Exception {
		createApplication();
		applicationCountries = getApplication();
		assertNotNull(applicationCountries);
		assertNotNull(applicationCountries.getProvider());
	}
}