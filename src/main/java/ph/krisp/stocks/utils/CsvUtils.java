package ph.krisp.stocks.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import ph.krisp.stocks.model.StockInfo;

/**
 * Various CSV file utility methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class CsvUtils {

	private CsvUtils() {}
	
	/**
	 * Appends the stock data to the existing file. Creates a new file if not
	 * found
	 * 
	 * @param stock
	 */
	public static void updateStockFile(StockInfo stock) {
		
		String dir = WebUtils.getOutputPath();
		String file = dir + stock.getCode() + ".csv";
		
		System.out.println(file);
		

		try (
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT); ) {

			csvPrinter.printRecord(stock.getCode(), stock.getClose());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
