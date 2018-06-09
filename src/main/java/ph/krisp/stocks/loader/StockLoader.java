package ph.krisp.stocks.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	private static Set<String> stockKeySet = loadStockKeySet();
	private static Set<String> propertyKeySet= loadPropertyKeySet();
	private static Set<String> amountPropertyKeySet = loadAmountPropertyKeySet();
	
	private StockLoader() {}

	/**
	 * Retrieves the set of stockCodes currently in the directory
	 * 
	 * @return the set of stockCodes
	 */
	private static Set<String> loadStockKeySet() {
		return CsvUtils.getFileNames();
	}
	
	/**
	 * 
	 * @return all keySet of the stock data
	 */
	private static Set<String> loadPropertyKeySet() {
		String keyset = WebUtils.getAllKeySet();
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
	}
	
	/**
	 * 
	 * @return all keySet for stock information with the number data type
	 */
	private static Set<String> loadAmountPropertyKeySet() {
		String keyset = WebUtils.getNumberKeySet();
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
	}
	
	public static Date getLatestDate() {
		return latest;
	}
	
	public static Map<Date, Integer> getLatestHelper() {
		return latestHelper;
	}

	public static Set<String> getAllKeySet() {
		return propertyKeySet;
	}

	public static Set<String> getAmountKeySet() {
		return amountPropertyKeySet;
	}

	public static Set<String> getStockKeySet() {
		return stockKeySet;
	}
	
	public static int stockKeySize() {
		return stockKeySet.size();
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
		return loadStockRecords(StockLoader.getStockKeySet().toArray(
				new String[StockLoader.stockKeySize()]), depth);
	}
	
	/**
	 * Loads all stock records found in the String[] argument with the given
	 * depth
	 * 
	 * @param stockCodes
	 *            the array of StockCodes
	 * @param depth
	 *            the number of records to be loaded per stock record
	 * @return all stock records in the String[] argument
	 */
	public static Map<String, List<StockRecord>> loadStockRecords(String[] stockCodes, int depth) {
		long startTime = System.nanoTime();
		Map<String, List<StockRecord>> stockRecords = new HashMap<>();
		latestHelper.clear();
		for(String stockCode : stockCodes) {
			stockRecords.put(stockCode, StockLoader.loadSingleStockRecord(stockCode, depth));
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
	 *            the number of records to be loaded per stock record
	 * @return the stock records equal to the specified depth or all records if
	 *         depth is greater than number of records saved in the file
	 */
	public static List<StockRecord> loadSingleStockRecord(String stockCode, int depth) {
		List<StockRecord> stockRecords = new ArrayList<>();
		List<StockRaw> stockRaws = CsvUtils.loadStock(stockCode, depth);

		for (StockRaw raw : stockRaws) {
			stockRecords.add(StockLoader.convertStockRawToRecord(raw));
		}
		
		// update the latest date
		updateLatestDate(stockRecords);
		
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

		Map<String, Object> info = new LinkedHashMap<>();
		for(String property : StockLoader.getAllKeySet()) {
			// if property is a number
			if(getAmountKeySet().contains(property)) {
//				stockRecord.putInfo(property, CalcUtils.parseNumber(raw.getProperty(property)));
				info.put(property, CalcUtils.parseNumber(raw.getProperty(property)));
			}
			// default
			else {
//				stockRecord.putInfo(property, raw.getProperty(property));
				info.put(property, raw.getProperty(property));
			}
			
		}
		return new StockRecord(raw.getCode(),
				CalcUtils.parseDate(raw.getProperty("date")), info);
	}
	
	/**
	 * Update the latest date based from the stockRecord contents
	 * 
	 * @param stockRecords stockRecords
	 */
	private static void updateLatestDate(List<StockRecord> stockRecords) {
		
		// do not update if stockRecords is empty
		if(stockRecords.size() == 0) {
			return;
		}
		
		Date date = stockRecords.get(stockRecords.size()-1).getDate();
		if(getLatestHelper().containsKey(date)) {
			getLatestHelper().put(date, getLatestHelper().get(date)+1);
		} else {
			getLatestHelper().put(date, 1);
		}
		if(getLatestDate() == null) {
			latest = date;
		}
		if(getLatestDate().compareTo(date) < 0) {
			latest = date;
		}
	}

}
