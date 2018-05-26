package ph.krisp.stocks.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import ph.krisp.stocks.model.StockRaw;

/**
 * Various CSV file utility methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class CsvUtils {

	private static final Logger logger = Logger.getLogger("CsvUtils");
	
	private static final String DIR = WebUtils.getOutputPath();
	
	private CsvUtils() {}
	
	/**
	 * Loads the stock data starting from the most recent up to a certain
	 * number of records
	 * @param stockCode
	 * @param depth the number of records to be retrieved
	 * @return
	 */
	public static List<StockRaw> loadStock(String stockCode, int depth) {
		
		return null;
	}
	
	/**
	 * Writes all stock data to their corresponding files. Appends if the file
	 * is existing. Creates a new file otherwise.
	 * 
	 * @param stocks
	 *            the Collection of stock to be written
	 */
	public static void updateAllStockFiles(Map<String, StockRaw> stocks) {
		logger.info("Writing to " + DIR);
		long startTime = System.nanoTime();
    	// writes updated stock information
    	for(StockRaw stock : stocks.values()) {
    		CsvUtils.updateStockFile(stock);
    	}
    	logger.info("Stock data (" + stocks.size()
    		+ ") updated. Elapsed: " + (System.nanoTime()-startTime)/1000000.00 + "ms");
		
	}
	
	/**
	 * Appends the stock data to the existing file. Creates a new file if not
	 * found
	 * 
	 * @param stock
	 */
	public static void updateStockFile(StockRaw stock) {
		
		String file = DIR + stock.getCode() + ".csv";

		try (
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
				) {

			// insert header if empty
			File test = new File(file);
			if(test.length() == 0) {
				csvPrinter.printRecord(WebUtils.getAllKeySet());
			}
			// else update
			List<String> values = new ArrayList<>();
			for(String key : WebUtils.getAllKeySet()) {
				values.add(stock.getProperty(key));
			}
			csvPrinter.printRecord(values);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
