package ph.krisp.stocks.connection;

import static org.junit.Assert.*;

import java.io.IOException;
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
		WebCon.login();
	}

	@Test
	public void testGetAllStockCodes() {
		Map<String, Stock> stockInfo = WebCon.getAllStockInfo();
		assertTrue(stockInfo.size() > 0);
		
		System.out.println(JsonUtils.objectToJson(stockInfo));
		System.out.println("size = " + stockInfo.size());
		
	}
	
	@Test
	public void testGetStockCode() {
		try {
			Stock stock = WebCon.getStock("SMPH");
			System.out.println(JsonUtils.objectToJson(stock));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
