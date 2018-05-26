package ph.krisp.stocks.loader;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private StockLoader() {}
	
	/**
	 * Loads the stock records from the storage file. Parses the csv file for
	 * the stock raw information. Converts each stock raw object ot a stock
	 * record object. The number of records loaded is determined by depth.
	 * 
	 * @param stockCode
	 *            the stock to be loaded
	 * @param depth
	 *            the number of records to be loaded
	 * @return the stock records equal to the specified depth or all records if
	 *         depth is greater than number of records saved in the file
	 */
	public static List<StockRecord> loadStockRecords(String stockCode, int depth) {
		List<StockRecord> stockRecords = new ArrayList<>();
		List<StockRaw> stockRaws = CsvUtils.loadStock(stockCode, depth);
		
		for(StockRaw raw : stockRaws) {
			stockRecords.add(StockLoader.convertStockRawToRecord(raw));
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
		StockRecord stock = new StockRecord(raw.getCode());
		
		Date date = CalcUtils.parseDate(raw.getProperty("date")); 
		stock.setDate(date);
		
		BigDecimal close = CalcUtils.parseNumber(raw.getProperty("Last Price"));
		stock.setChange(close);
		
		BigDecimal change = CalcUtils.parseNumber(raw.getProperty("Change"));
		stock.setChange(change);
		
		BigDecimal percentChange = CalcUtils.parseNumber(raw.getProperty("%Change"));
		stock.setPercentChange(percentChange);
		
		BigDecimal prevClose = CalcUtils.parseNumber(raw.getProperty("Previous Close"));
		stock.setPrevClose(prevClose);
		
		BigDecimal open = CalcUtils.parseNumber(raw.getProperty("Open"));
		stock.setOpen(open);
		
		BigDecimal low = CalcUtils.parseNumber(raw.getProperty("Low"));
		stock.setLow(low);
		
		BigDecimal high = CalcUtils.parseNumber(raw.getProperty("High"));
		stock.setHigh(high);
		
		BigDecimal avePrice = CalcUtils.parseNumber(raw.getProperty("Average Price"));
		stock.setAvePrice(avePrice);
		
		BigDecimal volume = CalcUtils.parseNumber(raw.getProperty("Volume"));
		stock.setVolume(volume);

		BigDecimal value = CalcUtils.parseNumber(raw.getProperty("Value"));
		stock.setValue(value);
		
		BigDecimal netForeign = CalcUtils.parseNumber(raw.getProperty("Net Foreign"));
		stock.setNetForeign(netForeign);

	

		
		
		return stock;
	}
	
}
