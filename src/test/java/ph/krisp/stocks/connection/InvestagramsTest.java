package ph.krisp.stocks.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.model.StockRaw;
import ph.krisp.stocks.utils.JsonUtils;
import ph.krisp.stocks.utils.WebUtils;

public class InvestagramsTest {

	
	@Before
	public void setUp() throws Exception {
		Investagrams.login();
		assertEquals(60, WebUtils.getAllKeySet().size());
	}

	@Test
	public void testGetAllStockCodes() {
		Map<String, StockRaw> stockInfo = Investagrams.getAllStockInfo();
		assertTrue(stockInfo.size() > 0);
		
		System.out.println(JsonUtils.objectToJson(stockInfo));
		System.out.println("size = " + stockInfo.size());
		
	}
	
	@Test
	public void testGetStockCode() {
		try {
			StockRaw stock = Investagrams.getStock("SMPH");
			System.out.println(
					JsonUtils.objectToJson(stock.keySet()));
			
			assertEquals(60, stock.keySet().size());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
