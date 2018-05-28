package ph.krisp.stocks.analysis;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.model.StockRecord;
import ph.krisp.stocks.utils.JsonUtils;

public class StockAnalysisTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLongestRange() {
		StockAnalysis longestRange
		= new StockAnalysis(StockLoader.loadAllStockRecord(10));
		
		Map<String, List<StockRecord>> filtered
		= longestRange.filterByLongestRange();

		
		System.out.println(JsonUtils.objectToJson(filtered.keySet()));
		System.out.println("size=" + filtered.size());
	}
	
	
	@Test
	public void testVolumeSpike() {
		StockAnalysis volumeSpike
			= new StockAnalysis(StockLoader.loadAllStockRecord(40));
		
		Map<String, List<StockRecord>> filtered
			= volumeSpike.filterByVolumeSpike(new BigDecimal("1.5"));

		System.out.println(JsonUtils.objectToJson(filtered.keySet()));
		System.out.println("size=" + filtered.size());
	}
	
	@Test
	public void test() {
		StockAnalysis stockAnalysis = new StockAnalysis(StockLoader.loadAllStockRecord(1));
		Map<String, List<StockRecord>> filteredStocks
			= stockAnalysis.filterByInfo("%Change", new BigDecimal("10"));
		
		System.out.println(JsonUtils.objectToJson(filteredStocks));
		System.out.println(JsonUtils.objectToJson(filteredStocks.keySet()));
		
	}

}
