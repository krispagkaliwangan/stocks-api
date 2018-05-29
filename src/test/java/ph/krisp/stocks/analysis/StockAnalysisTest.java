package ph.krisp.stocks.analysis;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
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
	public void testFilterByPercentCloseOverRange() {
		StockAnalysis sa = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		
		Map<String, List<StockRecord>> result
			= sa.filterByPercentCloseOverRange(new BigDecimal("0.9"));
		
		System.out.println("size=" + result.size());
		System.out.println(JsonUtils.objectToJson(result.keySet()));
	}
	
	@Test
	public void testSingleStockAnalysis() {
		// load single stock
		String key = "ISM";
		List<StockRecord> value = StockLoader.loadStockRecord(key, 40);
		Map<String, List<StockRecord>> input = new HashMap<>();
		input.put(key, value);
		
		// start analysis
		StockAnalysis sa = new StockAnalysis(input);
		Map<String, List<StockRecord>> longestRange = sa.filterByLongestRange();
		
		System.out.println("longest range size=" + longestRange.size());
		System.out.println(JsonUtils.objectToJson(longestRange.keySet()));
	}
	
	@Test
	public void testVolumeSpikeAndLongestRange() {
		// gain only
		StockAnalysis sa1 = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		Map<String, List<StockRecord>> gainOnly
			= sa1.filterByInfo("%Change", new BigDecimal("3"));
		
		// volume spike
		StockAnalysis sa2 = new StockAnalysis(gainOnly);
		Map<String, List<StockRecord>> volumeSpike
			= sa2.filterByVolumeSpike(new BigDecimal("1.25"));
		
		// longest range
		StockAnalysis sa3 = new StockAnalysis(volumeSpike);
		Map<String, List<StockRecord>> longestRange = sa3.filterByLongestRange();
		
		
		
		// print
		System.out.println("gain only size=" + gainOnly.size());
		System.out.println("volume spike size=" + volumeSpike.size());
		System.out.println("longest range size=" + longestRange.size());
		
		System.out.println(JsonUtils.objectToJson(longestRange.keySet()));
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
