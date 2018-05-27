package ph.krisp.stocks.analysis;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.model.StockRecord;

/**
 * Performs analysis against the stock records
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockAnalysis {

	private static final Logger logger = Logger.getLogger(StockAnalysis.class);
	
	private Map<String, List<StockRecord>> input;

	public StockAnalysis(Map<String, List<StockRecord>> input) {
		this.input = input;
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
	
}
