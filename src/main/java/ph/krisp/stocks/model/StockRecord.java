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
	
	public StockRecord(String code, Date date) {
		this.code = code;
		this.date = date;
		this.info = new LinkedHashMap<>();
	}
	
	public String getCode() {
		return code;
	}

	public Date getDate() {
		return date;
	}

	public void putInfo(String key, BigDecimal value) {
		this.info.put(key, value);
	}
	
	public Object getInfo(String key) {
		return this.info.get(key);
	}
	
	
}
