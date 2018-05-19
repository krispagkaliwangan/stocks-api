package ph.krisp.stocks.connection;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.model.Stock;

public class WebConnectionTest {

	private Map<String, String> cookies;
	
	@Before
	public void setUp() throws Exception {
		cookies = WebConnection.login();
	}

	@Test
	public void testGetAllStockCodes() {
		Map<String, Stock> stockInfo = WebConnection.getAllStockInfo(cookies);
		assertTrue(stockInfo.size() > 0);
	}

}
