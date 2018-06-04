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
	public void testAllFilters() {
		BigDecimal gainParam = new BigDecimal("3"); // in percent
		BigDecimal volumeSpikeParam = new BigDecimal("1.25"); // value * 100
		BigDecimal percentRangeParam = new BigDecimal("0.75"); // value * 100
		BigDecimal nearResistanceParam = new BigDecimal("-0.0005"); // 1+value * 100
		
		// gain only
		StockAnalysis go = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		Map<String, List<StockRecord>> gainOnly
			= go.filterByInfo("%Change", gainParam);
		
		// volume spike
		StockAnalysis vs = new StockAnalysis(gainOnly);
		Map<String, List<StockRecord>> volumeSpike
			= vs.filterByVolumeSpike(volumeSpikeParam);
		
		// longest range
		StockAnalysis lr = new StockAnalysis(volumeSpike);
		Map<String, List<StockRecord>> longestRange = lr.filterByLongestRange();
		
		// percent close range
		StockAnalysis cr = new StockAnalysis(longestRange);
		Map<String, List<StockRecord>> percentRange
			= cr.filterByPercentCloseOverRange(percentRangeParam);
		
		// near resistance
		StockAnalysis nr = new StockAnalysis(percentRange);
		Map<String, List<StockRecord>> nearResistance
			= nr.filterByResistance1(nearResistanceParam);
		
		System.out.println("gain only size=" + gainOnly.size());
		System.out.println("volume spike size=" + volumeSpike.size());
		System.out.println("longest range size=" + longestRange.size());
		System.out.println("percent close range size=" + percentRange.size());
		System.out.println("near resistance size=" + nearResistance.size());
		
		System.out.println("Gain Only:");
		System.out.println(JsonUtils.objectToJson(gainOnly.keySet()));
		System.out.println("Volume Spike:");
		System.out.println(JsonUtils.objectToJson(volumeSpike.keySet()));
		System.out.println("Longest Range:");
		System.out.println(JsonUtils.objectToJson(longestRange.keySet()));
		System.out.println("Percent Range:");
		System.out.println(JsonUtils.objectToJson(percentRange.keySet()));
		System.out.println("Near Resistance:");
		System.out.println(JsonUtils.objectToJson(nearResistance.keySet()));

	}
	
	@Test
	public void testResistanceAndVSpike() {
		// gain only
		StockAnalysis go = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		Map<String, List<StockRecord>> gainOnly
			= go.filterByInfo("%Change", new BigDecimal("0.005"));
		
		// volume spike
		StockAnalysis vs = new StockAnalysis(gainOnly);
		Map<String, List<StockRecord>> volumeSpike
			= vs.filterByVolumeSpike(new BigDecimal("1.25"));
		
		// near resistance
		StockAnalysis nr = new StockAnalysis(volumeSpike);
		Map<String, List<StockRecord>> nearResistance
			= nr.filterByResistance1(new BigDecimal("-0.0005"));
		
		System.out.println("volume spike size=" + volumeSpike.size());
		System.out.println("near resistance size=" + nearResistance.size());
		
		System.out.println("Volume Spike:");
		System.out.println(JsonUtils.objectToJson(volumeSpike.keySet()));
		System.out.println("Near Resistance");
		System.out.println(JsonUtils.objectToJson(nearResistance.keySet()));
	}
	
	@Test
	public void testResistance1() {
		StockAnalysis sa = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		Map<String, List<StockRecord>> nearResistance
			= sa.filterByResistance1(new BigDecimal("-0.005"));
		
		System.out.println("Near Resistance");
		System.out.println(JsonUtils.objectToJson(nearResistance.keySet()));
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

		String key = "IDC";
		List<StockRecord> value = StockLoader.loadStockRecord(key, 40);
		Map<String, List<StockRecord>> input = new HashMap<>();
		input.put(key, value);
		
		// start analysis
		StockAnalysis sa = new StockAnalysis(input);
		Map<String, List<StockRecord>> longestRange
			= sa.filterByPercentCloseOverRange(new BigDecimal("0.5"));
		
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
