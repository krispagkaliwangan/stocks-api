package ph.krisp.stocks.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Various utility methods
 * 
 * @author kris.pagkaliwangan
 *
 */
public class Utils {

	private static Properties loginProperties = loadLoginProperties();
	
	private Utils() {}
	
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
		
		try (InputStream input = Utils.class.getClassLoader().getResourceAsStream("config.properties")){
			
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
