package ph.krisp.stocks.connection;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	 * @param properties
	 *            the properties data structure to be populated
	 * @return the raw stock information object
	 */
	public static Map<String, String> parseStockInfo(Document doc,
			Map<String, String> properties) {
		Elements table = doc.body().select(".col-xs-4 > table > tbody > tr");

		for(Element e : table) {
			Element label = e.select("td").first().child(0);
			Element data = e.select(".stock-price").first();
			
			String key = label.ownText()
					.replace(":", "")
					.replace(",", "");
			String value = data.ownText();
			properties.put(key, value);
		}
		return properties;
	}
	
	/**
	 * Parses the FundamentalAnalysisContent for all data
	 * 
	 * @param doc
	 * @return the key-value pairs parsed from FundamentalAnalysisContent
	 */
	public static Map<String, String> parseFundamentalAnalysis(Document doc,
			Map<String, String> properties) {
		
		return parseAnalysis(doc,
				"#FundamentalAnalysisContent > div > div > div > table > tbody > tr > td",
				properties);
	}
	
	/**
	 * Parses the TechnicalAnalysisContent for all data
	 * 
	 * @param doc
	 * @return the key-value pairs parsed from TechnicalAnalysisContent
	 */
	public static Map<String, String> parseTechnicalAnalysis(Document doc,
			Map<String, String> properties) {
		Map<String, String> analysis = parseAnalysis(doc,
				"#TechnicalAnalysisContent > div > .col-xs-12 > div > table > tbody > tr > td",
				properties); 
		
		// indicators
		analysis = parseIndicators(analysis, doc);
		
		
		return analysis;
	}

	/**
	 * Parses the indicators under technical analysis content
	 * 
	 * TODO: replace all String parsers with regex
	 * 
	 * @param analysis
	 *            the initial data structure containing the technical analysis
	 * @param doc
	 * @return the updated data structure containing the indicators
	 */
	private static Map<String, String> parseIndicators(Map<String, String> analysis, Document doc) {
		Elements table = doc.body()
				.select("#TechnicalAnalysisContent > div > .col-xs-6 > div > table > tbody > tr > td");
		
		for(int i=0; i<table.size(); i+=3){
			String key = table.get(i).child(0).ownText().replace(",", "");
			// moving average
			if(key.startsWith("MA ")) {
				String key1 = key+" Simple";
				String value1 = table.get(i+1).ownText()
						.replace(")", "")
						.replace("(", "")
						.replace(",", "")
						.trim();
				analysis.put(key1, value1);
				
				String key1a = key+" Simple Action";
				String value1a = table.get(i+1).child(0).ownText();
				analysis.put(key1a, value1a);
				
				String key2 = key+" Exp";
				String value2 = table.get(i+2).ownText()
						.replace(")", "")
						.replace("(", "")
						.replace(",", "")
						.trim();
				analysis.put(key2, value2);
				
				String key2a = key+" Exp Action";
				String value2a = table.get(i+2).child(0).ownText();
				analysis.put(key2a, value2a);
			}
			
			// other indicators
			else {
				String value = table.get(i+1).ownText();
				analysis.put(key, value);	
				
				String keya = key + " Action";
				String action;
				try {
					action = table.get(i+2).child(0).ownText();
				} catch(IndexOutOfBoundsException e) {
					action = table.get(i+2).ownText();
				}
				analysis.put(keya, action);	
			}

//			System.out.println(table.get(i).html());
//			System.out.println("\t" + table.get(i+1).html());
//			System.out.println("\t" + table.get(i+2).html());

		}
		
		return analysis;
	}
	
	/**
	 * Extracts all elements from the document using the cssQuery. Loops through
	 * the extracted elements sequentially. The odd numbered elements are the key
	 * or label, and the even numbered elements are the data. The data can be
	 * inside a span or the text of the element. If the cssQuery for the span
	 * (stock-price) is null, the data is usually already in the text of the
	 * element.
	 * 
	 * @param doc
	 * @param cssQuery
	 * @return a key-value pairs containing the label and the data for that
	 *         label
	 */
	private static Map<String, String> parseAnalysis(Document doc, String cssQuery,
			Map<String, String> properties) {
		Elements table = doc.body().select(cssQuery);

		for(int i=0; i<table.size()-2; i+=2){
			String key = table.get(i).child(0).ownText()
					.replace(":", "")
					.replace(",", "");
			Element data;
			String value;
			try {
				data = table.get(i+1).select(".stock-price").first();
				value = data.ownText();
			} catch(NullPointerException e) {
				value = table.get(i+1).ownText();
			}
			if(properties.containsKey(key)) {
				properties.put(key+"-2", value);
			} else {
				properties.put(key, value);
			}
			
		}
		return properties;
	}
	
}
