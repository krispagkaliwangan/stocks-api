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
	public void test() {
		int depth = 2;
		List<StockRecord> records = StockLoader.loadStockRecords("NOW", depth);
		
		assertEquals(depth, records.size());
		
		for(StockRecord rec : records) {
			System.out.println(JsonUtils.objectToJson(rec));
		}
		
		
		
		
	}

}