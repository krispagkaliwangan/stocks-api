package ph.krisp.stocks.utils;

import java.math.BigDecimal;

/**
 * Various calculation methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class CalcUtils {

	private CalcUtils() {}
	
	/**
	 * Removes % and comma in the String given
	 * 
	 * @param text
	 * @return text with % and comma replaced by empty character
	 */
	public static BigDecimal parseNumber(String text) {
		return new BigDecimal(text.replaceAll("[\\,%]", ""));
	}
	
}
