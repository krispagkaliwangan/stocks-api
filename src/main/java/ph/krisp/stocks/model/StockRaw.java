package ph.krisp.stocks.model;

import java.util.Map;
import java.util.Set;

/**
 * Class containing the raw stock information and analysis
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockRaw {

	private String code;
	private Map<String, String> properties;
	
	public StockRaw(String code, Map<String, String> properties) {
		super();
		this.code = code;
		this.properties = properties;
	}

	public String getCode() {
		return code;
	}
	public String getProperty(String key) {
		return this.properties.get(key);
	}
	public Set<String> keySet() {
		return this.properties.keySet();
	}
	public boolean containsKey(String key) {
		return this.properties.containsKey(key);
	}
	
	
}
