package ph.krisp.stocks.analysis;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

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
	
	public Set<String> filterByInfo(String info, BigDecimal value) {
		long startTime = System.nanoTime();
		Set<String> filteredStocks = new HashSet<>();
		for(List<StockRecord> records : this.input.values()) {
			//get last record
			StockRecord rec = records.get(records.size()-1);
			BigDecimal infoValue = (BigDecimal) rec.getInfo(info);
			if(infoValue.compareTo(value) >= 0) {
				filteredStocks.add(rec.getCode());
			}
		}
    	logger.info("Stock data (" + input.size()
		+ ") processed. Elapsed: " + (System.nanoTime()-startTime)/1000000.00 + "ms");
	
		return filteredStocks;
	}
	
}
