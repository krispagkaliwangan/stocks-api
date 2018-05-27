package ph.krisp.stocks.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ph.krisp.stocks.model.StockRaw;
import ph.krisp.stocks.model.StockRecord;
import ph.krisp.stocks.utils.CalcUtils;
import ph.krisp.stocks.utils.CsvUtils;
import ph.krisp.stocks.utils.WebUtils;

/**
 * Class containing methods for loading Stock information from saved files
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockLoader {

	private static final Logger logger = Logger.getLogger("StockLoader");
	
	/* Latest date to use for filters */
	private static Date latest;
	private static Map<Date, Integer> latestHelper = new HashMap<>();
	
	private StockLoader() {}

	public static Date getLatestDate() {
		return latest;
	}
	
	public static Map<Date, Integer> getLatestHelper() {
		return latestHelper;
	}
	
	/**
	 * 
	 * @return all keySet of the stock data
	 */
	public static Set<String> getAllKeySet() {
		String keyset = WebUtils.getAllKeySet();
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
	}

	/**
	 * 
	 * @return all keySet for stock information with the number data type
	 */
	public static Set<String> getAmountKeySet() {
		String keyset = WebUtils.getNumberKeySet();
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
	}

	/**
	 * Retrieves the set of stockCodes currently in the directory
	 * 
	 * @return the set of stockCodes
	 */
	public static Set<String> getStockKeySet() {
		return CsvUtils.getFileNames();
	}

	/**
	 * Loads all stock records from the storage file with the given depth
	 * 
	 * @param depth
	 *            the number of records to be retrieved for each stock
	 * @return the Map of key-value pairs representing stockCode and its
	 *         corresponding StockRecord with the given depth
	 */
	public static Map<String, List<StockRecord>> loadAllStockRecord(int depth) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> stockRecords = new HashMap<>();
		latestHelper.clear();
		for(String stockCode : StockLoader.getStockKeySet()) {
			stockRecords.put(stockCode, StockLoader.loadStockRecord(stockCode, depth));
		}
    	logger.info("Stock data (" + stockRecords.size()
			+ ") loaded. Elapsed: " + (System.nanoTime()-startTime)/1000000.00 + "ms");
	
		return stockRecords;
	}
	
	/**
	 * Loads the stock records from the storage file. Parses the csv file for
	 * the stock raw information. Converts each stock raw object to a stock
	 * record object. The number of records loaded is determined by depth.
	 * 
	 * @param stockCode
	 *            the stock to be loaded
	 * @param depth
	 *            the number of records to be loaded
	 * @return the stock records equal to the specified depth or all records if
	 *         depth is greater than number of records saved in the file
	 */
	public static List<StockRecord> loadStockRecord(String stockCode, int depth) {
		List<StockRecord> stockRecords = new ArrayList<>();
		List<StockRaw> stockRaws = CsvUtils.loadStock(stockCode, depth);

		for (StockRaw raw : stockRaws) {
			stockRecords.add(StockLoader.convertStockRawToRecord(raw));
		}
		
		// update the latest date
		if(stockRecords.size() > 0) {
			updateLatest(stockRecords.get(stockRecords.size()-1).getDate());
		}
		
		return stockRecords;
	}

	/**
	 * Converts the raw stock object to a stock record object
	 * 
	 * @param raw
	 *            the raw stock object
	 * @return the stock record object with data from the raw stock object
	 */
	private static StockRecord convertStockRawToRecord(StockRaw raw) {
		StockRecord stock = new StockRecord(raw.getCode(),
				CalcUtils.parseDate(raw.getProperty("date")));

		for(String property : StockLoader.getAllKeySet()) {
			// if property is a number
			if(getAmountKeySet().contains(property)) {
				stock.putInfo(property, CalcUtils.parseNumber(raw.getProperty(property)));
			}
			// default
			else {
				stock.putInfo(property, raw.getProperty(property));
			}
			
		}
		
		return stock;
	}
	
	/**
	 * Update the latest date
	 * 
	 * @param date
	 */
	private static void updateLatest(Date date) {
		if(latestHelper.containsKey(date)) {
			latestHelper.put(date, latestHelper.get(date)+1);
		} else {
			latestHelper.put(date, 1);
		}
		if(latest == null) {
			latest = date;
		}
		if(getLatestDate().compareTo(date) < 0) {
			latest = date;
		}
	}

}
