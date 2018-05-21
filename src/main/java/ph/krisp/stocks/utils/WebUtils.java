package ph.krisp.stocks.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return prop;
	}
	
	/**
	 * Gets the document using the authorized cookies
	 * 
	 * @param url
	 *            the website url
	 * @param cookies
	 *            the authorized cookies
	 * @return the document
	 * @throws IOException
	 */
	private static Document getDocument(String url, Map<String, String> cookies) throws IOException {
		return Jsoup.connect(url)  
		         .cookies(cookies)  
		         .userAgent(USER_AGENT)  
		         .get();
	}
	
}
