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

import ph.krisp.stocks.model.Stock;

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
	
	public static void updateAllStockFiles(Map<String, Stock> stocks) {
		logger.info("Writing to " + DIR);
		long startTime = System.nanoTime();
    	// writes updated stock information
    	for(Stock stock : stocks.values()) {
    		CsvUtils.updateStockFile(stock);
    	}
    	logger.info("Stock data updated. Elapsed: " + (System.nanoTime()-startTime)/1000000.00 + "ms");
		
	}
	
	/**
	 * Appends the stock data to the existing file. Creates a new file if not
	 * found
	 * 
	 * @param stock
	 */
	public static void updateStockFile(Stock stock) {
		
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
