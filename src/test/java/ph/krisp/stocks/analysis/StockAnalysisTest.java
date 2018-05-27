package ph.krisp.stocks.analysis;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.utils.JsonUtils;

public class StockAnalysisTest {

	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		StockAnalysis stockAnalysis = new StockAnalysis(StockLoader.loadAllStockRecord(1));
		Set<String> filteredStocks = stockAnalysis.filterByInfo("%Change", new BigDecimal("10.24"));
		
		System.out.println(JsonUtils.objectToJson(filteredStocks));
		
	}

}
