package ph.krisp.stocks.utils;

import java.math.BigDecimal;

/**
 * Various utility methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class CalcUtils {

	private CalcUtils() {}
	
	public static BigDecimal parseBigDecimal(String text) {
		System.out.println("\t"+text.replaceAll("[\\,%]", ""));
		return new BigDecimal(text.replaceAll("[\\,%]", ""));
	}
	
}
