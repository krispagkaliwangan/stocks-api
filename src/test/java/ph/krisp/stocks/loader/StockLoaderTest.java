package ph.krisp.stocks.loader;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.model.StockRecord;
import ph.krisp.stocks.utils.JsonUtils;

public class StockLoaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAmountKeyset() {
		System.out.println(JsonUtils.objectToJson(StockLoader.getAmountKeySet()));
		assertEquals(36, StockLoader.getAmountKeySet().size());
	}
	
	@Test
	public void testLatestHelper() {
		StockLoader.loadStockRecords(5);
		System.out.println(JsonUtils.objectToJson(StockLoader.getLatestHelper()));
		System.out.println(JsonUtils.objectToJson(StockLoader.getLatestDate()));
	}
	
	@Test
	public void test() {
		int depth = 2;
		List<StockRecord> records = StockLoader.loadSingleStockRecord("NOW", depth);
		
		assertEquals(depth, records.size());
		
		for(StockRecord rec : records) {
			System.out.println(JsonUtils.objectToJson(rec));
		}
		
		
		
		
	}

}
