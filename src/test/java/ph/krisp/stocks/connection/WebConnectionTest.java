package ph.krisp.stocks.connection;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.model.Stock;
import ph.krisp.stocks.model.StockRawInfo;
import ph.krisp.stocks.utils.JsonUtils;

public class WebConnectionTest {

	private Map<String, String> cookies;
	
	@Before
	public void setUp() throws Exception {
		cookies = WebCon.login();
	}

	@Test
	public void testGetAllStockCodes() {
		Map<String, Stock> stockInfo = WebCon.getAllStockInfo(cookies);
		assertTrue(stockInfo.size() > 0);
		
//		for(StockInfo s : stockInfo.values()) {
//			assertNotNull(s.getCode());
//			assertNotNull(s.getClose());
//			assertNotNull(s.getChange());
//			assertNotNull(s.getPercentChange());
//			assertNotNull(s.getOpen());
//			assertNotNull(s.getLow());
//			assertNotNull(s.getHigh());
//			assertNotNull(s.getPreviousClose());
//			assertNotNull(s.getVolume());
//			assertNotNull(s.getValue());
//		}
		
		System.out.println(JsonUtils.objectToJson(stockInfo));
		System.out.println("size = " + stockInfo.size());
		
	}

}
