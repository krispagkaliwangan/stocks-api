package ph.krisp.stocks.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Various web utility methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class WebUtils {

	private static Properties loginProperties = loadLoginProperties();
	
	private WebUtils() {}
	
	public static String getUsername() {
		return loginProperties.getProperty("username");
	}
	
	public static String getPassword() {
		return loginProperties.getProperty("password");
	}
	
	/**
	 * Load the login properties
	 * 
	 * @return the properties containing the login config
	 */
	private static Properties loadLoginProperties() {
		Properties prop = new Properties();
		
		try (InputStream input = WebUtils.class.getClassLoader().getResourceAsStream("config.properties")){
			
			prop.load(input);
			prop.getProperty("username");
			prop.getProperty("password");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return prop;
	}
	
}
