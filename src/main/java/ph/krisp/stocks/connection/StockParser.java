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
	 * Parses the stock information. Selects all tr within the appropriate
	 * table, all contains a label and the data to be saved
	 * 
	 * @param doc
	 *            the webpage to be parsed for stock information
	 * @return the raw stock information object
	 */
	public static Map<String, String> parseStockInfo(Document doc) {
		Elements table = doc.body().select(".col-xs-4 > table > tbody > tr");
		
		Map<String, String> info = new HashMap<>(); 
		for(Element e : table) {
			Element label = e.select("td").first().child(0);
			Element data = e.select(".stock-price").first();
			
			String key = label.ownText().replace(":", "");
			String value = data.ownText();
			info.put(key, value);
		}
		return info;
	}
	
	/**
	 * Parses the FundamentalAnalysisContent for all data
	 * 
	 * @param doc
	 * @return the key-value pairs parsed from FundamentalAnalysisContent
	 */
	public static Map<String, String> parseFundamentalAnalysis(Document doc) {
		
		return parseAnalysis(doc, "#FundamentalAnalysisContent > div > div > div > table > tbody > tr > td");
	}
	
	/**
	 * Parses the TechnicalAnalysisContent for all data
	 * 
	 * @param doc
	 * @return the key-value pairs parsed from TechnicalAnalysisContent
	 */
	public static Map<String, String> parseTechnicalAnalysis(Document doc) {

		return parseAnalysis(doc, "#TechnicalAnalysisContent > div > .col-xs-12 > div > table > tbody > tr > td");
	}

	private static Map<String, String> parseAnalysis(Document doc, String cssQuery) {
		Elements table = doc.body().select(cssQuery);
		
		Map<String, String> info = new HashMap<>(); 
		for(int i=0; i<table.size()-2; i+=2){
			String key = table.get(i).child(0).ownText().replace(":", "");
			Element data;
			String value;
			try {
				data = table.get(i+1).select(".stock-price").first();
				value = data.ownText();
			} catch(NullPointerException e) {
				value = table.get(i+1).ownText();
			}
			info.put(key, value);
		}
		return info;
	}
	
	public static StockTechnicalAnalysis parseStockTechnicalAnalysis() {
		
		return null;
	}
	
}
