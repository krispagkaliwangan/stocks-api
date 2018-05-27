package ph.krisp.stocks.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.connection.Investagrams;
import ph.krisp.stocks.model.StockRaw;

public class CsvUtilsTest {

	
	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testGetFileNames() {
		assertTrue(CsvUtils.getFileNames().size() > 0);
	}
	
	@Test
	public void testLoadStock() {
		String stockCode = "NOW";
		List<StockRaw> stocks = CsvUtils.loadStock(stockCode, 2);
		
		for(StockRaw s : stocks) {
			System.out.println(JsonUtils.objectToJson(s));
		}
	}
	
	@Test
	public void testUpdateStockFile() {
		Investagrams.login();
		try {
			StockRaw stock = Investagrams.getStock("SMPH");
//			System.out.println(JsonUtils.objectToJson(stock));
			CsvUtils.updateStockFile(stock);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
