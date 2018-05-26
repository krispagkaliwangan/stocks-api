package ph.krisp.stocks.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Various calculation methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class CalcUtils {

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
	 * Removes % and comma in the String given
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
			return null;
		}

		
	}
	
}
