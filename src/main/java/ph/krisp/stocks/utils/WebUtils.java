package ph.krisp.stocks.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Various web utility methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class WebUtils {

	private static Properties properties = loadConfigProperties();
	
	private WebUtils() {}
	
	public static String getUsername() {
		return properties.getProperty("username");
	}
	
	public static String getPassword() {
		return properties.getProperty("password");
	}
	
	/**
	 * 
	 * @return the directory for output files
	 */
	public static String getOutputPath() {
		return properties.getProperty("output_path");
	}
	
	public static Set<String> getAllKeySet() {
		String keyset = "date,Last Price,Change,%Change,Previous Close,Open,Low,High,Average Price,Volume,Value,Net Foreign,52-Week High,Earnings Per Share TTM (EPS),Price to Book Value (P/BV),52-Week Low,Price-Earnings Ratio TTM (P/E),Return on Equity (ROE),Fair Value,Dividends Per Share (DPS),Recommendation,Support 1,Resistance 1,Short-Term Trend,Support 2,Resistance 2,Recommendation-2,Last Price-2,Year to Date %,Month to Date %,MA 20 Simple,MA 20 Simple Action,MA 20 Exp,MA 20 Exp Action,MA 50 Simple,MA 50 Simple Action,MA 50 Exp,MA 50 Exp Action,MA 100 Simple,MA 100 Simple Action,MA 100 Exp,MA 100 Exp Action,MA 200 Simple,MA 200 Simple Action,MA 200 Exp,MA 200 Exp Action,RSI(14),RSI(14) Action,MACD(12269),MACD(12269) Action,ATR(14),ATR(14) Action,CCI(20),CCI(20) Action,STS(1433),STS(1433) Action,Williams %R(14),Williams %R(14) Action,VolumeSMA(15),VolumeSMA(15) Action,CandleStick(1),CandleStick(1) Action";
				//properties.getProperty("keyset");
		return new LinkedHashSet<>(Arrays.asList(keyset.split("\\s*,\\s*")));
		
	}
	
	/**
	 * Load the login properties
	 * 
	 * @return the properties containing the login config
	 */
	private static Properties loadConfigProperties() {
		Properties prop = new Properties();
		
		try (InputStream input = WebUtils.class.getClassLoader()
				.getResourceAsStream("config.properties")){
			
			prop.load(input);
			prop.getProperty("username");
			prop.getProperty("password");
			prop.getProperty("output_path");
			prop.getProperty("keyset");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return prop;
	}
	
}
