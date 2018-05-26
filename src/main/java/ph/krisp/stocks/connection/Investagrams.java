package ph.krisp.stocks.connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ph.krisp.stocks.model.Stock;
import ph.krisp.stocks.utils.WebUtils;

/**
 * Class for connecting to the web app for scraping
 * 
 * @author kris.pagkaliwangan
 *
 */
public class Investagrams {

	private static final Logger logger = Logger.getLogger("Investagrams");
	
	private static final String LOGIN_URL = "https://www.investagrams.com";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";  
	private static final String REAL_TIME_MON_URL = "https://www.investagrams.com/Stock/RealTimeMonitoring";
	private static final String STOCK_BASE_URL = "https://www.investagrams.com/Stock/";
	
	/**
	 * Authorized cookies updated after logging in
	 */
	private static Map<String, String> cookies;
	
	private Investagrams() {}
	
	/**
	 * Logins to the given LOGIN_URL and updates the cookies to be used for
	 * authorized transactions
	 * 
	 */
	public static void login() {
		logger.info("Logging in...");
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
    		
    		String username = WebUtils.getUsername();
    		String password = WebUtils.getPassword();
    		
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
    		
    		if(homePage.body().contains("pageRedirect")) {
    			// update cookies
    			cookies = homePage.cookies();
        		logger.info("Login successful!");
    		}
    		
		} catch (IOException e) {
			logger.info("Login unsuccessful!");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Retrieves all the stock codes in the real time monitoring page of the
	 * Investagrams with their corresponding information
	 * 
	 * @return a map containing all the stock codes paired to its stock
	 *         information
	 */
	public static Map<String, Stock> getAllStockInfo() {
		logger.info("Downloading stock data...");
		long startTime = System.nanoTime();
		
		Map<String, Stock> stockInfo = new HashMap<>();
		
		// check if cookies are valid
		if(cookies.size() == 0 || cookies == null) {
			return stockInfo;
		}
		
		try {
			
			Set<String> stockCodes = getStockList();
			// download data
			StockPageManager stockPageManager = new StockPageManager(stockCodes);
			stockPageManager.run();
			stockInfo = stockPageManager.getStockInfo();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} 
		logger.info("Stock data (" + stockInfo.size() + 
				") downloaded. Elapsed: " + (System.nanoTime()-startTime)/1000000000.00 + "s");
		return stockInfo;
	}
	
	/**
	 * Downloads the list of stock codes
	 * 
	 * @return the list of stock codes
	 * @throws IOException
	 */
	private static Set<String> getStockList() throws IOException {
		Document stockListDoc = downloadDocument(REAL_TIME_MON_URL);
		Elements table = stockListDoc.body()
				.select("#StockQuoteTable > tbody > tr");

		Set<String> stockCodes = new LinkedHashSet<>();
		for (Element row : table) {
			stockCodes.add(StockParser.parseStockCode(row));	
		}
		return stockCodes;
	}
	
	/**
	 * Retrieves the stock object with data of the given stockCode
	 * 
	 * @param stockCode
	 * @return the stock object with data
	 * @throws IOException
	 */
	public static Stock getStock(String stockCode) throws IOException {
		
		String stockUrl = STOCK_BASE_URL + stockCode;

		logger.info("Downloading " + stockUrl);
		Document stockDoc = downloadDocument(stockUrl);
		
		String date = stockDoc.select("p > #lblPriceLastUpdateDate").first().ownText();
		Map<String, String> properties = new LinkedHashMap<>();
		properties.put("date", date);
		properties = StockParser.parseStockInfo(stockDoc, properties);
		properties = StockParser.parseFundamentalAnalysis(stockDoc, properties);
		properties = StockParser.parseTechnicalAnalysis(stockDoc, properties);
		
		return new Stock(stockCode, properties);
	}
	
	/**
	 * Gets the document using the authorized cookies
	 * 
	 * @param url
	 *            the website url
	 * @return the document
	 * @throws IOException
	 */
	private static Document downloadDocument(String url) throws IOException {
		return Jsoup.connect(url)  
		         .cookies(cookies)  
		         .userAgent(USER_AGENT)  
		         .get();
	}
}
