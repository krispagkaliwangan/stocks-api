package ph.krisp.stocks.loader;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private StockLoader() {
	}

	/**
	 * 
	 * @return all keySet of the stock data
	 */
	public static Set<String> getAllKeySet() {
		String keyset = "date,Last Price,Change,%Change,Previous Close,Open,Low,High,Average Price,Volume,Value,Net Foreign,52-Week High,Earnings Per Share TTM (EPS),Price to Book Value (P/BV),52-Week Low,Price-Earnings Ratio TTM (P/E),Return on Equity (ROE),Fair Value,Dividends Per Share (DPS),Recommendation,Support 1,Resistance 1,Short-Term Trend,Support 2,Resistance 2,Recommendation-2,Last Price-2,Year to Date %,Month to Date %,MA 20 Simple,MA 20 Simple Action,MA 20 Exp,MA 20 Exp Action,MA 50 Simple,MA 50 Simple Action,MA 50 Exp,MA 50 Exp Action,MA 100 Simple,MA 100 Simple Action,MA 100 Exp,MA 100 Exp Action,MA 200 Simple,MA 200 Simple Action,MA 200 Exp,MA 200 Exp Action,RSI(14),RSI(14) Action,MACD(12269),MACD(12269) Action,ATR(14),ATR(14) Action,CCI(20),CCI(20) Action,STS(1433),STS(1433) Action,Williams %R(14),Williams %R(14) Action,VolumeSMA(15),VolumeSMA(15) Action,CandleStick(1),CandleStick(1) Action";
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
	}

	/**
	 * 
	 * @return all keySet for stock information, not including f-analysis and
	 *         t-analysis
	 */
	public static Set<String> getInfoKeySet() {
		String keyset = "Last Price,Change,%Change,Previous Close,Open,Low,High,Average Price,Volume,Value,Net Foreign";
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
	}

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

		for (StockRaw raw : stockRaws) {
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

		for(String property : StockLoader.getInfoKeySet()) {
			stock.putInfo(property, CalcUtils.parseNumber(raw.getProperty(property)));
		}
		
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
