package ph.krisp.stocks.utils;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.connection.Investagrams;
import ph.krisp.stocks.model.StockRaw;

public class CsvUtilsTest {

	
	@Before
	public void setUp() throws Exception {
		Investagrams.login();
		
	}

	@Test
	public void testUpdateStockFile() {
		
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
