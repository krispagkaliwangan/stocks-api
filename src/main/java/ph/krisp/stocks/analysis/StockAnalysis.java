package ph.krisp.stocks.analysis;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * TODO: convert to a processor class
 * TODO: create a new StockAnalysis class as model
 * 
 * -input
 * -output
 * -result
 * -analysis made
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockAnalysis {

	private static final Logger logger = Logger.getLogger(StockAnalysis.class);
	
	private Map<String, List<StockRecord>> input;

	public StockAnalysis(Map<String, List<StockRecord>> input) {
		this.input = input;
		// TODO: input should only contain latest data
	}
	
	public Set<String> inputKeySet() {
		return this.input.keySet();
	}
	
	public Collection<List<StockRecord>> values() {
		return this.input.values();
	}
	
	public List<StockRecord> get(String key) {
		return this.input.get(key);
	}
	
	/**
	 * Filters the input stocks by the info and the corresponding value.
	 * Outdated records are ignored.
	 * 
	 * @param info
	 * @param value
	 * @return the filtered set of stock codes
	 */
	public Map<String, List<StockRecord>> filterByInfo(String info, BigDecimal value) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filteredStocks = new HashMap<>();
		
		// if given info key does not exist
		if(!StockLoader.getAllKeySet().contains(info)) {
	    	logger.info("Stock data (" + input.size() + ") processed. Elapsed: "
    				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
			return filteredStocks;
		}
		
		// loop thru all
		for(List<StockRecord> records : this.input.values()) {
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
    	logger.info("Stock data (" + input.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		return filteredStocks;
	}
	
	/**
	 * Filters all stocks that met the volume spike criteria
	 * 
	 * @return all stocks that met the volume spike criteria
	 */
	public Map<String, List<StockRecord>> filterByVolumeSpike(BigDecimal threshold) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filtered = new HashMap<>();
		String volume = "Volume";
		// loop thru all
		for(List<StockRecord> records : this.input.values()) {
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
		
    	logger.info("Stock data (" + input.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		return filtered;
	}
	
	/**
	 * Filters all stocks that meet the longest range criteria
	 * 
	 * @return all stocks that met the longest range criteria
	 */
	public Map<String, List<StockRecord>> filterByLongestRange() {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> filtered = new HashMap<>();
		
		// loop thru all
		for(List<StockRecord> records : this.input.values()) {
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
			if(records.get(lastIndex).getRange().compareTo(maxRange) > 0) {
				// add if it is
				filtered.put(records.get(0).getCode(), records);
			}
			
		}
		
    	logger.info("Stock data (" + input.size() + ") processed. Elapsed: "
				+ (System.nanoTime()-startTime)/1000000.00 + "ms");
		return filtered;
	}
	
}
