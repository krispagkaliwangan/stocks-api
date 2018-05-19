package ph.krisp.stocks.connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

/**
 * Class for connecting to the web app for scraping
 * 
 * @author kris.pagkaliwangan
 *
 */
public class WebConnection {

	private static final String LOGIN_URL = "https://www.investagrams.com";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";  
	private static final String REAL_TIME_MON_URL = "https://www.investagrams.com/Stock/RealTimeMonitoring";
	
	private WebConnection() {}
	
	/**
	 * Logins to the given LOGIN_URL and returns the cookies to be used for
	 * authorized transactions
	 * 
	 * @return the cookies representing the login state
	 */
	public static Map<String, String> login() {
		Map<String, String> cookies = null;
		try {
			// get login page
			Response loginForm = Jsoup.connect(LOGIN_URL)
					.method(Connection.Method.GET)
					.userAgent(USER_AGENT)
					.followRedirects(true)
					.execute();
			Document loginDoc = loginForm.parse();
			// get cookies
			cookies = loginForm.cookies();
			
			// get formData variables
    		String scriptManager = "LoginUserControlPanel$LoginUpdatePanel|LoginUserControlPanel$LoginButton";
    		String eventTarget = loginDoc.select("#__EVENTTARGET").first().attr("value");
    		String eventArgument = loginDoc.select("#__EVENTARGUMENT").first().attr("value");
    		String viewState = loginDoc.select("#__VIEWSTATE").first().attr("value");
    		String viewStateGenerator = loginDoc.select("#__VIEWSTATEGENERATOR").first().attr("value");
    		String eventValidation = loginDoc.select("#__EVENTVALIDATION").first().attr("value");
    		String rememberMe = "on";
    		String asyncPost = "true";
    		String loginButton = "Login";
    		
    		String username = "";
    		String password = "";
    		
    		Map<String, String> formData = new HashMap<String, String>();
    		formData.put("ScriptManager", scriptManager);
    		formData.put("__EVENTTARGET", eventTarget);
    		formData.put("__EVENTARGUMENT", eventArgument);
    		formData.put("__VIEWSTATE", viewState);
    		formData.put("__VIEWSTATEGENERATOR", viewStateGenerator);
    		formData.put("__EVENTVALIDATION", eventValidation);
    		formData.put("LoginUserControlPanel$RememberMe", rememberMe);
    		formData.put("__ASYNCPOST", asyncPost);
    		formData.put("LoginUserControlPanel$LoginButton", loginButton);
    		
    		formData.put("LoginUserControlPanel$Username", username);
    		formData.put("LoginUserControlPanel$Password", password);
			
    		Response homePage = Jsoup.connect(LOGIN_URL)  
   		         .cookies(cookies)  
   		         .data(formData)  
   		         .method(Connection.Method.POST)  
   		         .userAgent(USER_AGENT)
   		         .execute(); 
    		
    		// update cookies
    		return homePage.cookies();
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cookies;
	}
	
	/**
	 * Retrieves all the stock codes in the real time monitoring page of the Investagrams
	 * 
	 * 
	 * @param cookies
	 * @return
	 */
	public static Set<String> getAllStockCodes(Map<String, String> cookies) {
		Set<String> stockCodes = new HashSet<>();
		
		// check if cookies are valid
		if(cookies.size() == 0 || cookies == null) {
			return stockCodes;
		}
		
		try {
			Document st = Jsoup.connect(REAL_TIME_MON_URL)  
			         .cookies(cookies)  
			         .userAgent(USER_AGENT)  
			         .get();
			
			System.out.println(st.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return stockCodes;
	}
}
