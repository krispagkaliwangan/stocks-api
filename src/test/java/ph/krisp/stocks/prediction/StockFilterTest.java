package ph.krisp.stocks.prediction;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.model.StockPrediction;
import ph.krisp.stocks.model.StockRecord;
import ph.krisp.stocks.prediction.StockFilter;
import ph.krisp.stocks.utils.JsonUtils;

public class StockFilterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWalking() {
		String[] stockCodes = {"MEG"};
		// start analysis from 2 months of data (40=5days in 8 weeks)
		int depth = 40;
		StockFilter analysis = new StockFilter(StockLoader.loadStockRecords(stockCodes, depth));

		List<StockPrediction> predictions = new ArrayList<>();
		for (int i = 1; i < depth; i++) {
			// filter
			StockFilter sa = new StockFilter(analysis);
			sa.filterByDepth(depth, depth - i);
			List<StockRecord> records = sa.getOutput().get(stockCodes[0]);
			
			// create new prediction
			if(!records.isEmpty()) {
				StockRecord latest = records.get(records.size()-1);
				String action = "U";
				StockPrediction prediction = new StockPrediction(latest.getCode(), latest.getDate(), action);
				predictions.add(prediction);
				
				// update prev prediction
				int prevIndex = predictions.size()-1;
				if(predictions.get(prevIndex) != null) {
					StockPrediction prevPrediction = predictions.get(prevIndex);
					BigDecimal change = (BigDecimal) latest.getInfo("%Change");
					String nextDay = change.compareTo(BigDecimal.ZERO) >= 0 ? "U" : "D";
					prevPrediction.setNextDay(nextDay);
				}
			}
			
		}
		
		int tcounter = 0;
		for(StockPrediction prediction : predictions) {
			System.out.println(prediction.getDate() + " : " + prediction.getAction()
					+ " : " + prediction.getNextDay() + " : " + prediction.isMatch());
			if(prediction.isMatch()) {
				tcounter++;
			}
		}
		System.out.println("Result = " + tcounter + "/" + predictions.size());
		
	}
	
	@Test
	public void testExtractRange() {
		String[] stockCodes = {"NOW"};
		StockFilter analysis = new StockFilter(StockLoader.loadStockRecords(stockCodes, 40));
		
		analysis.filterByDepth(15, 5);
		
		assertEquals(10, analysis.getOutput().get("NOW").size());
		
		System.out.println(JsonUtils.objectToJson(analysis.getOutput().get("NOW")));
	}
	
	@Test
	public void testExperiment() {
		// set analysis parameters
		BigDecimal gainParam = new BigDecimal("1"); // in percent
		BigDecimal volumeSpikeParam = new BigDecimal("1"); // value * 100
		BigDecimal percentRangeParam = new BigDecimal("0.75"); // value * 100
		BigDecimal longestRangeParam = new BigDecimal("1"); // value * 10
		BigDecimal nearResistanceParam = new BigDecimal("-0.0005"); // 1+value * 100
		
		// loads 2 months of data
		StockFilter analysis = new StockFilter(StockLoader.loadStockRecords(40));
		
		analysis
				.filterByInfo("%Change", gainParam)
				.filterByVolumeSpike(volumeSpikeParam)
				.filterByLongestRange(longestRangeParam)
				.filterByPercentCloseOverRange(percentRangeParam)
				.filterByResistance1(nearResistanceParam)
				.getOutput();
		
		System.out.println("output size=" + analysis.getOutputSize());
		System.out.println(JsonUtils.objectToJson(analysis.getOutputKeys()));
	}
	
	@Test
	public void testRefactoredAllFilters() {
		// set analysis parameters
		BigDecimal gainParam = new BigDecimal("1.5"); // in percent
		BigDecimal volumeSpikeParam = new BigDecimal("1.25"); // value * 100
		BigDecimal percentRangeParam = new BigDecimal("0.75"); // value * 100
		BigDecimal longestRangeParam = new BigDecimal("1"); // value * 100
		BigDecimal nearResistanceParam = new BigDecimal("-0.0005"); // 1+value * 100
		
		// loads 2 months of data
		StockFilter analysis = new StockFilter(StockLoader.loadStockRecords(40));
		
		analysis
				.filterByInfo("%Change", gainParam)
				.filterByVolumeSpike(volumeSpikeParam)
				.filterByLongestRange(longestRangeParam)
				.filterByPercentCloseOverRange(percentRangeParam)
				.filterByResistance1(nearResistanceParam)
				.getOutput();
		
		System.out.println("output size=" + analysis.getOutputSize());
		System.out.println(JsonUtils.objectToJson(analysis.getOutputKeys()));
	}
	
	@Test
	public void testAllFilters() {
		BigDecimal gainParam = new BigDecimal("3"); // in percent
		BigDecimal volumeSpikeParam = new BigDecimal("1.25"); // value * 100
		BigDecimal percentRangeParam = new BigDecimal("0.75"); // value * 100
		BigDecimal nearResistanceParam = new BigDecimal("-0.0005"); // 1+value * 100
		
		// gain only
		StockFilter go = new StockFilter(StockLoader.loadStockRecords(40));
		StockFilter gainOnly = go.filterByInfo("%Change", gainParam);
		
		// volume spike
		StockFilter vs = new StockFilter(gainOnly);
		StockFilter volumeSpike
			= vs.filterByVolumeSpike(volumeSpikeParam);
		
		// longest range
		StockFilter lr = new StockFilter(volumeSpike);
		StockFilter longestRange = lr.filterByLongestRange();
		
		// percent close range
		StockFilter cr = new StockFilter(longestRange);
		StockFilter percentRange
			= cr.filterByPercentCloseOverRange(percentRangeParam);
		
		// near resistance
		StockFilter nr = new StockFilter(percentRange);
		StockFilter nearResistance
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
		StockFilter go = new StockFilter(StockLoader.loadStockRecords(40));
		StockFilter gainOnly
			= go.filterByInfo("%Change", new BigDecimal("0.005"));
		
		// volume spike
		StockFilter vs = new StockFilter(gainOnly);
		StockFilter volumeSpike
			= vs.filterByVolumeSpike(new BigDecimal("1.25"));
		
		// near resistance
		StockFilter nr = new StockFilter(volumeSpike);
		StockFilter nearResistance
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
		StockFilter sa = new StockFilter(StockLoader.loadStockRecords(40));
		StockFilter nearResistance
			= sa.filterByResistance1(new BigDecimal("-0.005"));
		
		System.out.println("Near Resistance");
		System.out.println(JsonUtils.objectToJson(nearResistance.getOutputKeys()));
	}
	
	@Test
	public void testFilterByPercentCloseOverRange() {
		StockFilter sa = new StockFilter(StockLoader.loadStockRecords(40));
		
		StockFilter result
			= sa.filterByPercentCloseOverRange(new BigDecimal("0.9"));
		
		System.out.println("size=" + result.getOutputSize());
		System.out.println(JsonUtils.objectToJson(result.getOutputKeys()));
	}
	
	@Test
	public void testSingleStockAnalysis() {
		// load single stock

		String key = "IDC";
		List<StockRecord> value = StockLoader.loadSingleStockRecord(key, 40);
		Map<String, List<StockRecord>> input = new HashMap<>();
		input.put(key, value);
		
		// start analysis
		StockFilter sa = new StockFilter(input);
		StockFilter longestRange
			= sa.filterByPercentCloseOverRange(new BigDecimal("0.5"));
		
		System.out.println("longest range size=" + longestRange.getOutputSize());
		System.out.println(JsonUtils.objectToJson(longestRange.getOutputKeys()));
	}
	
	@Test
	public void testVolumeSpikeAndLongestRange() {
		// gain only
		StockFilter sa1 = new StockFilter(StockLoader.loadStockRecords(40));
		StockFilter gainOnly
			= sa1.filterByInfo("%Change", new BigDecimal("3"));
		
		// volume spike
		StockFilter sa2 = new StockFilter(gainOnly);
		StockFilter volumeSpike
			= sa2.filterByVolumeSpike(new BigDecimal("1.25"));
		
		// longest range
		StockFilter sa3 = new StockFilter(volumeSpike);
		StockFilter longestRange = sa3.filterByLongestRange();
		
		
		
		// print
		System.out.println("gain only size=" + gainOnly.getOutputSize());
		System.out.println("volume spike size=" + volumeSpike.getOutputSize());
		System.out.println("longest range size=" + longestRange.getOutputSize());
		
		System.out.println(JsonUtils.objectToJson(longestRange.getOutputKeys()));
	}
	
	@Test
	public void testLongestRange() {
		StockFilter longestRange
			= new StockFilter(StockLoader.loadStockRecords(10));
		
		StockFilter filtered
			= longestRange.filterByLongestRange();

		
		System.out.println(JsonUtils.objectToJson(filtered.getOutputKeys()));
		System.out.println("size=" + filtered.getOutputSize());
	}
	
	
	@Test
	public void testVolumeSpike() {
		StockFilter volumeSpike
			= new StockFilter(StockLoader.loadStockRecords(40));
		
		StockFilter filtered
			= volumeSpike.filterByVolumeSpike(new BigDecimal("1.5"));

		System.out.println(JsonUtils.objectToJson(filtered.getOutputKeys()));
		System.out.println("size=" + filtered.getOutputSize());
	}
	
	@Test
	public void test() {
		StockFilter stockAnalysis = new StockFilter(StockLoader.loadStockRecords(1));
		StockFilter filteredStocks
			= stockAnalysis.filterByInfo("%Change", new BigDecimal("10"));
		
		System.out.println(JsonUtils.objectToJson(filteredStocks));
		System.out.println(JsonUtils.objectToJson(filteredStocks.getOutputKeys()));
		
	}

}
