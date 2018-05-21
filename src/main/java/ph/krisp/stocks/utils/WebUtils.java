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
		String keyset = properties.getProperty("keyset");
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
