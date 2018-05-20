package ph.krisp.stocks.model;

import java.util.Map;

/**
 * Class containing stock information and analysis
 * @author kris.pagkaliwangan
 *
 */
public class Stock {

	private String code;
	private String date;
	private Map<String, String> info;
	private Map<String, String> fundamentalAnalyis;
	private Map<String, String> technicalAnalyis;

	
	
	
	public String getClose() {
		return this.info.get("Last Price");
	}
	public String getChange() {
		return this.info.get("Change");
	}
	public String getPercentChange() {
		return this.info.get("%Change");
	}
	public String getPreviousClose() {
		return this.info.get("Previous Close");
	}
	public String getOpen() {
		return this.info.get("Open");
	}
	public String getLow() {
		return this.info.get("Low");
	}
	public String getHigh() {
		return this.info.get("High");
	}
	public String getAveragePrice() {
		return this.info.get("Average Price");
	}
	public String getVolume() {
		return this.info.get("Volume");
	}
	public String getValue() {
		return this.info.get("Value");
	}
	public String getNetForeign() {
		return this.info.get("Net Foreign");
	}
	
}
