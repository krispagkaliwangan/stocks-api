package ph.krisp.stocks.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import ph.krisp.stocks.loader.StockLoader;
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
	 * Loads the raw stock data starting from the most recent up to a certain
	 * number of records
	 * 
	 * @param stockCode
	 * @param depth
	 *            the number of records to be retrieved
	 * @return the list of StockRaw objects with size equal to the specified
	 *         depth or all records if depth is greater than number of records
	 *         saved in the file
	 * 
	 */
	public static List<StockRaw> loadStock(String stockCode, int depth) {
		List<StockRaw> stocks = new ArrayList<>();
		try (
			 Reader reader = Files.newBufferedReader(Paths.get(DIR + stockCode + ".csv"));
	         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader()); ){
			
			List<CSVRecord> csvRecords = csvParser.getRecords();
			
			int start = csvRecords.size()-depth;
			if(depth >= csvRecords.size()) {
				start = 0;
			}
			
			for(int i=start; i<csvRecords.size(); i++) {
				StockRaw stock = new StockRaw(stockCode, csvRecords.get(i).toMap());
				stocks.add(stock);
			}
			
		} catch(IOException e) {
			logger.error(DIR + stockCode + ".csv not found.");
		}
		 
		return stocks;
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
				csvPrinter.printRecord(StockLoader.getAllKeySet());
			}
			// else update
			List<String> values = new ArrayList<>();
			for(String key : StockLoader.getAllKeySet()) {
				values.add(stock.getProperty(key));
			}
			csvPrinter.printRecord(values);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
