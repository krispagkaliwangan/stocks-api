package ph.krisp.stocks.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class containing one record of stock information ready for analysis
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockRecord {

	private String code;
	private Date date;
	
	private Map<String, Object> info;
	
	public StockRecord(String code) {
		this.code = code;
		this.info = new LinkedHashMap<>();
	}

	public void putInfo(String key, BigDecimal value) {
		this.info.put(key, value);
	}
	
	public Object getInfo(String key) {
		return this.info.get(key);
	}
	
	public String getCode() {
		return code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
}
