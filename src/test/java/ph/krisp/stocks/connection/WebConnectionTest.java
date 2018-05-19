package ph.krisp.stocks.connection;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class WebConnectionTest {

	private Map<String, String> cookies;
	
	@Before
	public void setUp() throws Exception {
		cookies = WebConnection.login();
	}

	@Test
	public void testGetAllStockCodes() {
		Set<String> stockCodes = WebConnection.getAllStockCodes(cookies);
		assertTrue(stockCodes.size() > 0);
	}

}
