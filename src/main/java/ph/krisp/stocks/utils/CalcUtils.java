package ph.krisp.stocks.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ph.krisp.stocks.loader.StockLoader;
import ph.krisp.stocks.model.StockRecord;

/**
 * Various calculation methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class CalcUtils {
	
	private static final Logger logger = Logger.getLogger("CalcUtils");
	
	private CalcUtils() {}
	
	private static Map<String, BigDecimal> modifiers = loadModifiers();
	
	/**
	 * Creates a mapping of known amount modifiers to their equivalent value
	 * 
	 * @return the mapping of modifiers to value
	 */
	private static Map<String, BigDecimal> loadModifiers() {
		Map<String, BigDecimal> modifiers = new HashMap<>();
		modifiers.put("K", new BigDecimal("1000"));
		modifiers.put("M", new BigDecimal("1000000"));
		modifiers.put("B", new BigDecimal("1000000000"));
		return modifiers;
	}
	
	/**
	 * Performs linear interpolation using this formula:
	 * 	y = y1 + (x-x1)(y2-y1)/(x2-x1)
	 * 
	 * @param x 
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @return y or zero if x2=x1
	 */
	public static BigDecimal linearInterpolation(BigDecimal x, BigDecimal x1,
			BigDecimal x2, BigDecimal y1, BigDecimal y2) {
		
		// return zero if x2 == x1
		if(x2.compareTo(x1) == 0) {
			return BigDecimal.ZERO;
		}
		
		// y = y1 + (x-x1)(y2-y1)/(x2-x1)
		BigDecimal xMinusX1 = x.subtract(x1);
		BigDecimal y2MinusY1 = y2.subtract(y1);
		BigDecimal x2MinusX1 = x2.subtract(x1);
		
		return y1.add(
					xMinusX1.multiply(y2MinusY1)
					.divide(x2MinusX1, 5, RoundingMode.HALF_UP));
	}
	
	/**
	 * Calculates the average of the given property across the given list of
	 * StockRecord
	 * 
	 * @param records
	 *            the list of stock records
	 * @param property
	 *            the property to be averaged
	 * @return the average value of the property across the records
	 */
	public static BigDecimal calculateAverage(List<StockRecord> records, String property) {

		// if property is not a number
		if(!StockLoader.getAmountKeySet().contains(property)) {
			logger.info("Trying to average non-number property [" + property + "]."
					+ " Returning zero.");
			return BigDecimal.ZERO;
		}
		
		// if records is empty
		if(records.size() == 0) {
			logger.info("Trying to average zero sized records. Returning zero.");
			return BigDecimal.ZERO;
		}
		
		// summation and divide by size
		BigDecimal total = BigDecimal.ZERO;
		for(int i=0; i<records.size(); i++) {
			BigDecimal volume = (BigDecimal) records.get(i).getInfo("Volume");
			total = total.add(volume);
		}
		return total.divide(new BigDecimal(records.size()), 5, RoundingMode.HALF_UP);
	}
	
	/**
	 * Parses the text containing date into date object
	 * 
	 * @param text
	 * @return the date object
	 */
	public static Date parseDate(String text) {
		String toParse = text;//.substring(0, text.indexOf(" ")).trim();
		SimpleDateFormat f = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
		try {
			return f.parse(toParse);
		} catch (ParseException e) {
			logger.error("text=" + text + "; toParse="+toParse);
			return null;
		}
	}
	
	/**
	 * Removes % and comma in the String given, and applies any modifiers
	 * 
	 * @param text
	 * @return text with % and comma replaced by empty character
	 */
	public static BigDecimal parseNumber(String text) {
		String stripped = text.replaceAll("[\\,%]", "");
		
		return CalcUtils.applyAmountModifiers(stripped);
	}

	/**
	 * Applies the amount modifier (if any) in the String and creates a
	 * BigDecimal object amounting to the String representation of the amount
	 * 
	 * Note: valid amount modifier is only the last character in the text
	 * 
	 * @param text
	 * @return the equivalent BigDecimal value of the String
	 */
	private static BigDecimal applyAmountModifiers(String text) {
		String modifierKey = String.valueOf(text.charAt(text.length()-1)).toUpperCase();
		
		try {
			// if there is a modifier found
			if(CalcUtils.modifiers.containsKey(modifierKey)) {
				BigDecimal modifierVal = CalcUtils.modifiers.get(modifierKey);
				String stripped = text.substring(0, text.length()-1);
				return new BigDecimal(stripped).multiply(modifierVal).stripTrailingZeros();
			}
			// if no modifiers, convert to BigDecimal directly
			return new BigDecimal(text);
			
		} catch(NumberFormatException e) {
			logger.error(e);
			return null;
		}
		
	}
	
}
