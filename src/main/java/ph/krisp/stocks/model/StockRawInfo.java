package ph.krisp.stocks.model;

import java.util.Map;

/**
 * Class representation of a stock with information
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockRawInfo {
	
	private Map<String, String> info;
	
	/*
		Last Price - 15.78
		Change - 0.08
		%Change - 0.51%
		Previous Close - 15.70
		Open - 15.80
		Low - 15.54
		High - 15.80
		Average Price - 15.70
		Volume - 15.4K
		Value - 241.82K
		Net Foreign - 6.22K
	 */
	
	public StockRawInfo(Map<String, String> info) {
		this.info = info;
	}

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
