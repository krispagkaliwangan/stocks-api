package ph.krisp.stocks.connection;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ph.krisp.stocks.model.StockFundamentalAnalysis;
import ph.krisp.stocks.model.StockRawInfo;
import ph.krisp.stocks.model.StockTechnicalAnalysis;

/**
 * Class containing parsers for various information for the stock
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockParser {

	private StockParser() {}
	
	/**
	 * Parses the stock code stored in the element
	 * 
	 * @param element
	 * @return the stock code parsed
	 */
	public static String parseStockCode(Element element) {
		Elements rowData = element.select("td");
		return rowData.get(1).select("a").first().ownText();
	}
	
	/**
	 * Parses the stock information
	 * 
	 * @param doc
	 *            the webpage to be parsed for stock information
	 * @param cookies
	 *            the authorized cookies
	 * @return the raw stock information object
	 */
	public static Map<String, String> parseStockInfo(Document doc, Map<String, String> cookies) {
		Map<String, String> info = new HashMap<>();
		Elements table = doc.body().select(".col-xs-4 > table > tbody > tr");
		for(Element e : table) {
			Element label = e.select("td").first().child(0);
			Element data = e.select(".stock-price").first();
			
			String key = label.ownText().replace(":", "");
			String value = data.ownText();
			info.put(key, value);
		}
		
		return info;
	}
	
	public static StockFundamentalAnalysis parseStockFundamentalAnalyis() {
		
		
		return null;
	}
	
	public static StockTechnicalAnalysis parseStockTechnicalAnalysis() {
		
		return null;
	}
	
}
