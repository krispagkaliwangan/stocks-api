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
	
	// calculated attributes
	private BigDecimal range;
	
	@Deprecated
	public StockRecord(String code, Date date) {
		this.code = code;
		this.date = date;
		this.info = new LinkedHashMap<>();
	}

	/**
	 * Constructor to calculate attributes that are expensive to calculate
	 * outside this class. The info parameter is needed in the constructor to
	 * ensure that all parameters are present before calculating the expensive
	 * attributes.
	 * 
	 * @param code
	 * @param date
	 * @param info
	 *            the key-value pairs of properties and values
	 */
	public StockRecord(String code, Date date, Map<String, Object> info) {
		this.code = code;
		this.date = date;
		this.info = info;
		
		this.calculateAttributes();
	}
	
	/**
	 * Calculate the required attributes that are expensive to calculate
	 * when looping through all values
	 */
	private void calculateAttributes() {
		// calculate range
		this.range = ((BigDecimal) this.info.get("High"))
				.subtract((BigDecimal) this.info.get("Low"));
		
	}

	public String getCode() {
		return code;
	}

	public Date getDate() {
		return date;
	}

	public void putInfo(String key, Object value) {
		this.info.put(key, value);
	}
	
	public Object getInfo(String key) {
		return this.info.get(key);
	}
	
	// calculated attributes methods
	public BigDecimal getRange() {
		return range;
	}
	
	
}
