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
	public void testRefactoredAllFilters() {
		// set analysis parameters
		BigDecimal gainParam = new BigDecimal("3"); // in percent
		BigDecimal volumeSpikeParam = new BigDecimal("1.25"); // value * 100
		BigDecimal percentRangeParam = new BigDecimal("0.75"); // value * 100
		BigDecimal nearResistanceParam = new BigDecimal("-0.0005"); // 1+value * 100
		
		// loads 2 months of data
		StockAnalysis analysis = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		
		analysis
				.filterByInfo("%Change", gainParam)
				.filterByVolumeSpike(volumeSpikeParam)
				.filterByLongestRange()
				.filterByPercentCloseOverRange(percentRangeParam)
				.filterByResistance1(nearResistanceParam)
				.getOutput();
		
		System.out.println("output size=" + analysis.getOutputSize());
		System.out.println(JsonUtils.objectToJson(analysis.getOutputKeys()));
	}
	
	@Test
	public void testProcessor() {
		BigDecimal param1 = new BigDecimal("1"); // in percent
		BigDecimal param2 = new BigDecimal("3"); // in percent
		BigDecimal param3 = new BigDecimal("5"); // in percent
		StockAnalysis analysis = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		
		Map<String, List<StockRecord>> processed = analysis
														.testFilter("%Change", param1)
														.testFilter("%Change", param2)
														.testFilter("%Change", param3)
														.getOutput();

		System.out.println("gain only size=" + processed.size());
	}
	
	@Test
	public void testAllFilters() {
		BigDecimal gainParam = new BigDecimal("3"); // in percent
		BigDecimal volumeSpikeParam = new BigDecimal("1.25"); // value * 100
		BigDecimal percentRangeParam = new BigDecimal("0.75"); // value * 100
		BigDecimal nearResistanceParam = new BigDecimal("-0.0005"); // 1+value * 100
		
		// gain only
		StockAnalysis go = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		StockAnalysis gainOnly = go.filterByInfo("%Change", gainParam);
		
		// volume spike
		StockAnalysis vs = new StockAnalysis(gainOnly);
		StockAnalysis volumeSpike
			= vs.filterByVolumeSpike(volumeSpikeParam);
		
		// longest range
		StockAnalysis lr = new StockAnalysis(volumeSpike);
		StockAnalysis longestRange = lr.filterByLongestRange();
		
		// percent close range
		StockAnalysis cr = new StockAnalysis(longestRange);
		StockAnalysis percentRange
			= cr.filterByPercentCloseOverRange(percentRangeParam);
		
		// near resistance
		StockAnalysis nr = new StockAnalysis(percentRange);
		StockAnalysis nearResistance
			= nr.filterByResistance1(nearResistanceParam);
		
		System.out.println("gain only size=" + gainOnly.getOutputSize());
		System.out.println("volume spike size=" + volumeSpike.getOutputSize());
		System.out.println("longest range size=" + longestRange.getOutputSize());
		System.out.println("percent close range size=" + percentRange.getOutputSize());
		System.out.println("near resistance size=" + nearResistance.getOutputSize());
		
		System.out.println("Gain Only:");
		System.out.println(JsonUtils.objectToJson(gainOnly.getOutputKeys()));
		System.out.println("Volume Spike:");
		System.out.println(JsonUtils.objectToJson(volumeSpike.getOutputKeys()));
		System.out.println("Longest Range:");
		System.out.println(JsonUtils.objectToJson(longestRange.getOutputKeys()));
		System.out.println("Percent Range:");
		System.out.println(JsonUtils.objectToJson(percentRange.getOutputKeys()));
		System.out.println("Near Resistance:");
		System.out.println(JsonUtils.objectToJson(nearResistance.getOutputKeys()));

	}
	
	@Test
	public void testResistanceAndVSpike() {
		// gain only
		StockAnalysis go = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		StockAnalysis gainOnly
			= go.filterByInfo("%Change", new BigDecimal("0.005"));
		
		// volume spike
		StockAnalysis vs = new StockAnalysis(gainOnly);
		StockAnalysis volumeSpike
			= vs.filterByVolumeSpike(new BigDecimal("1.25"));
		
		// near resistance
		StockAnalysis nr = new StockAnalysis(volumeSpike);
		StockAnalysis nearResistance
			= nr.filterByResistance1(new BigDecimal("-0.0005"));
		
		System.out.println("volume spike size=" + volumeSpike.getOutputSize());
		System.out.println("near resistance size=" + nearResistance.getOutputSize());
		
		System.out.println("Volume Spike:");
		System.out.println(JsonUtils.objectToJson(volumeSpike.getOutputKeys()));
		System.out.println("Near Resistance");
		System.out.println(JsonUtils.objectToJson(nearResistance.getOutputKeys()));
	}
	
	@Test
	public void testResistance1() {
		StockAnalysis sa = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		StockAnalysis nearResistance
			= sa.filterByResistance1(new BigDecimal("-0.005"));
		
		System.out.println("Near Resistance");
		System.out.println(JsonUtils.objectToJson(nearResistance.getOutputKeys()));
	}
	
	@Test
	public void testFilterByPercentCloseOverRange() {
		StockAnalysis sa = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		
		StockAnalysis result
			= sa.filterByPercentCloseOverRange(new BigDecimal("0.9"));
		
		System.out.println("size=" + result.getOutputSize());
		System.out.println(JsonUtils.objectToJson(result.getOutputKeys()));
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
		StockAnalysis longestRange
			= sa.filterByPercentCloseOverRange(new BigDecimal("0.5"));
		
		System.out.println("longest range size=" + longestRange.getOutputSize());
		System.out.println(JsonUtils.objectToJson(longestRange.getOutputKeys()));
	}
	
	@Test
	public void testVolumeSpikeAndLongestRange() {
		// gain only
		StockAnalysis sa1 = new StockAnalysis(StockLoader.loadAllStockRecord(40));
		StockAnalysis gainOnly
			= sa1.filterByInfo("%Change", new BigDecimal("3"));
		
		// volume spike
		StockAnalysis sa2 = new StockAnalysis(gainOnly);
		StockAnalysis volumeSpike
			= sa2.filterByVolumeSpike(new BigDecimal("1.25"));
		
		// longest range
		StockAnalysis sa3 = new StockAnalysis(volumeSpike);
		StockAnalysis longestRange = sa3.filterByLongestRange();
		
		
		
		// print
		System.out.println("gain only size=" + gainOnly.getOutputSize());
		System.out.println("volume spike size=" + volumeSpike.getOutputSize());
		System.out.println("longest range size=" + longestRange.getOutputSize());
		
		System.out.println(JsonUtils.objectToJson(longestRange.getOutputKeys()));
	}
	
	@Test
	public void testLongestRange() {
		StockAnalysis longestRange
			= new StockAnalysis(StockLoader.loadAllStockRecord(10));
		
		StockAnalysis filtered
			= longestRange.filterByLongestRange();

		
		System.out.println(JsonUtils.objectToJson(filtered.getOutputKeys()));
		System.out.println("size=" + filtered.getOutputSize());
	}
	
	
	@Test
	public void testVolumeSpike() {
		StockAnalysis volumeSpike
			= new StockAnalysis(StockLoader.loadAllStockRecord(40));
		
		StockAnalysis filtered
			= volumeSpike.filterByVolumeSpike(new BigDecimal("1.5"));

		System.out.println(JsonUtils.objectToJson(filtered.getOutputKeys()));
		System.out.println("size=" + filtered.getOutputSize());
	}
	
	@Test
	public void test() {
		StockAnalysis stockAnalysis = new StockAnalysis(StockLoader.loadAllStockRecord(1));
		StockAnalysis filteredStocks
			= stockAnalysis.filterByInfo("%Change", new BigDecimal("10"));
		
		System.out.println(JsonUtils.objectToJson(filteredStocks));
		System.out.println(JsonUtils.objectToJson(filteredStocks.getOutputKeys()));
		
	}

}
