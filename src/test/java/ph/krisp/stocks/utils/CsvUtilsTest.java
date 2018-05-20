package ph.krisp.stocks.utils;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.connection.WebCon;
import ph.krisp.stocks.model.StockRawInfo;

public class CsvUtilsTest {

	private Map<String, String> cookies;
	private Map<String, StockRawInfo> stockInfo;
	
	@Before
	public void setUp() throws Exception {
		cookies = WebCon.login();
    	stockInfo = WebCon.getAllStockInfo(cookies);
	}

	@Test
	public void test() {
		
		for(StockRawInfo s : stockInfo.values()) {
			CsvUtils.updateStockFile(s);
		}
	}

}
