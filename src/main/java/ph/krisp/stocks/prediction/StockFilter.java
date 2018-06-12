package ph.krisp.stocks.prediction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.model.StockRecord;
import ph.krisp.stocks.utils.CalcUtils;

/**
 * Performs analysis against the stock records
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockFilter {

	private static final Logger logger = Logger.getLogger(StockFilter.class);
	
	private Map<String, List<StockRecord>> input;
	private Map<String, List<StockRecord>> toProcess;

	public StockFilter(Map<String, List<StockRecord>> input) {
		this.input = input;
		this.toProcess = input;
	}
	
	public StockFilter(StockFilter prevAnalysis) {
		this.input = prevAnalysis.getInput();
		this.toProcess = prevAnalysis.getOutput();
	}
	
	public Map<String, List<StockRecord>> getInput() {
		return this.input;
	}
	
	public Map<String, List<StockRecord>> getOutput() {
		return this.toProcess;
	}
	
	public Set<String> getOutputKeys() {
		return this.toProcess.keySet();
	}
	
	public int getInputSize() {
		return this.input.size();
	}
	
	public int getOutputSize() {
		return this.toProcess.size();
	}
	
	/**
	 * Filters the input records of this analysis with the given range.
	 * E.g. startDepth = 10, endDepth = 1, with size = 40
	 * the records to be returned are:
	 * 
	 * end = (40-1)-1 = 38
	 * start = (40-1)-10 = 29
	 * 
	 * meaning, elements 29 to 38 will be saved in the toProcess variable
	 * 
	 * @param startDepth
	 * @param endDepth
	 * @return the analysis object with the filtered records
	 */
	public StockFilter filterByDepth(int startDepth, int endDepth) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filteredStocks = new HashMap<>();
		
		// check if range is invalid
		if(this.toProcess.size() == 0 || startDepth < endDepth
				|| startDepth < 0 || endDepth < 0) {
	    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
    				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
			return this;
		}

		// loop thru all
		for (List<StockRecord> records : this.toProcess.values()) {
			// ignore this record if empty
			if(records.size() == 0) {
				continue;
			}
			// get indices for records
			int startIndex = records.size()-startDepth;
			int endIndex = records.size()-endDepth;
			
			// start index should not greater than record size
			if(startIndex < 0) {
				startIndex = 0;
			}
			// end index should nto be greater than record size
			if(endIndex < 0) {
				endIndex = 0;
			}
			// get records
			List<StockRecord> filteredRecords = new ArrayList<>();
			for (int i = startIndex; i < endIndex; i++) {
				filteredRecords.add(records.get(i));
			}
			filteredStocks.put(records.get(0).getCode(), filteredRecords);
		}
		
    	logger.info("Filter by depth complete. "
    			+ "Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		this.toProcess = filteredStocks;
		return this;
	}
	
	
	/**
	 * Filters the input stocks by the info and the corresponding value.
	 * Outdated records are ignored.
	 * 
	 * @param info
	 * @param value
	 * @return the Stock Analysis object with filtered set of stock codes
	 */
	public StockFilter filterByInfo(String info, BigDecimal value) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filteredStocks = new HashMap<>();
		
		// if given info key does not exist
		if(!StockLoader.getAllKeySet().contains(info)) {
	    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
    				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
			return this;
		}
		
		// loop thru all
		for(List<StockRecord> records : this.toProcess.values()) {
			//get last record
			StockRecord rec = records.get(records.size()-1);
			
			// check if record is latest, ignore if not
			if(rec.getDate().compareTo(StockLoader.getLatestDate()) < 0 ) {
				continue;
			}
			
			// check if key contains a number value
			if(StockLoader.getAmountKeySet().contains(info)) {
				BigDecimal infoValue = (BigDecimal) rec.getInfo(info);
				if(infoValue.compareTo(value) >= 0) {
					filteredStocks.put(rec.getCode(), records);
				}
			}

		}
    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		this.toProcess = filteredStocks;
		return this;
	}
	
	/**
	 * Filters all stocks that met the volume spike criteria
	 * 
	 * @return the Analysis object with all stocks that met the volume spike
	 *         criteria
	 */
	public StockFilter filterByVolumeSpike(BigDecimal threshold) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filtered = new HashMap<>();
		String volume = "Volume";
		// loop thru all
		for(List<StockRecord> records : this.toProcess.values()) {
			// if records will result averaging to zero
			if(records.size()-1 == 0) {
				continue;
			}
			
			// calculate average up to last day,
			List<StockRecord> toAverage = records.subList(0, records.size()-1);
			BigDecimal average = CalcUtils.calculateAverage(toAverage, volume);
			BigDecimal lastVolume = (BigDecimal) records.get(records.size()-1).getInfo(volume);
			
			// compare value of last day to average
			BigDecimal diff = lastVolume.divide(average, 5, RoundingMode.HALF_UP);
			
			// value should be greater than threshold
			if(diff.compareTo(threshold) >= 0) {
				filtered.put(records.get(0).getCode(), records);
			}
		}
		
    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		this.toProcess = filtered;
		return this;
	}
	
	/**
	 * Convenient method for filtering the longest range
	 * @return the Analysis object with all stocks that met the longest range criteria
	 */
	public StockFilter filterByLongestRange() {
		return filterByLongestRange(BigDecimal.ONE);
	}
	
	/**
	 * Filters all stocks that have its latest range within the threshold 
	 * of the maximum range
	 * 
	 * @param threshold 1 for the longest
	 * @return the Analysis object with all stocks that met the longest range criteria
	 */
	public StockFilter filterByLongestRange(BigDecimal threshold) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filtered = new HashMap<>();
		
		// loop thru all
		for(List<StockRecord> records : this.toProcess.values()) {
			BigDecimal maxRange = BigDecimal.ZERO;
			int lastIndex = records.size()-1;
			// get range of each stockRecord and update max
			for(int i=0; i<lastIndex; i++) {
				BigDecimal range = records.get(i).getRange();
				if(range.compareTo(maxRange) > 0) {
					maxRange = range;
				}
			}
			// check if range of latest is the maximum
//			if(records.get(lastIndex).getRange().compareTo(maxRange) > 0) {
//				// add if it is
//				filtered.put(records.get(0).getCode(), records);
//			}
			
			// check if range of latest is within the percentage
			// threshold of maximum
			BigDecimal percentage = records.get(lastIndex).getRange()
					.divide(maxRange, 5, RoundingMode.HALF_UP);
			if(percentage.compareTo(threshold) >= 0) {
				filtered.put(records.get(0).getCode(), records);
			}
			
		}
		
    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		this.toProcess = filtered;
		return this;
	}
	
	
	/**
	 * Filters all stocks that has a closing price greater than the threshold
	 * percentage of its high
	 * 
	 * @param threshold
	 * @return the Analysis object with all stocks that met this criteria
	 */
	public StockFilter filterByPercentCloseOverRange(BigDecimal threshold) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filtered = new HashMap<>();
		
		// loop thru all
		for (List<StockRecord> records : this.toProcess.values()) {
			StockRecord latestRecord = records.get(records.size()-1);
			
			BigDecimal x = (BigDecimal) latestRecord.getInfo("Last Price");
			BigDecimal x1 = (BigDecimal) latestRecord.getInfo("High");
			BigDecimal x2 = (BigDecimal) latestRecord.getInfo("Low");
			BigDecimal y1 = BigDecimal.ONE;
			BigDecimal y2 = BigDecimal.ZERO;
			BigDecimal percentage = CalcUtils.linearInterpolation(x, x1, x2, y1, y2);
			
			// check if percentage is greater than or equal to threshold
			if(percentage.compareTo(threshold) >= 0) {
				// add if it is
				filtered.put(latestRecord.getCode(), records);
			}
		}
		
    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		this.toProcess = filtered;
		return this;
	}
	
	/**
	 * Filters all stocks that are within threshold or greater than the
	 * difference of the previous day Resistance 1
	 * 
	 * @param threshold
	 * @return the Analysis object with all stocks that met the resistance threshold
	 */
	public StockFilter filterByResistance1(BigDecimal threshold) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filtered = new HashMap<>();
		
		// loop thru all
		for (List<StockRecord> records : this.toProcess.values()) {
			
			// if size is less than 2, ignore
			if(records.size() < 2) {
				continue;
			}
			
			// get previous resistance 1
			StockRecord prevRecord = records.get(records.size()-2);
			BigDecimal prevResistance1 = (BigDecimal) prevRecord.getInfo("Resistance 1");
			
			// get latest last price 
			StockRecord curRecord = records.get(records.size()-1);
			BigDecimal lastPrice = (BigDecimal) curRecord.getInfo("Last Price");
			
			BigDecimal allowedPercentage = CalcUtils.calculatePercentage(prevResistance1, threshold);
			// if latest last price >= threshold, add to filtered
			if(lastPrice.compareTo(allowedPercentage) >= 0) {
				filtered.put(curRecord.getCode(), records);
			}
		}
		
    	logger.info("Stock data (" + this.toProcess.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
    	this.toProcess = filtered;
    	return this;
	}
	
}
