package ph.krisp.stocks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ph.krisp.stocks.connection.WebConnection;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";  
	 
	
    public static void main( String[] args )
    {

    	Map<String, String> cookies = WebConnection.login();
    	WebConnection.getAllStockCodes(cookies);
        
    }
    
    private static void login() {
    	try {
            // get login page
    		String loginFormUrl = "https://www.investagrams.com";
			Response loginForm = Jsoup.connect(loginFormUrl)
									.method(Connection.Method.GET)
									.userAgent(USER_AGENT)
									.followRedirects(true)
									.execute();
			Document loginDoc = loginForm.parse();
    		
			// get cookies
			Map<String, String> cookies = loginForm.cookies();
			
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
    		
    		Response homePage = Jsoup.connect(loginFormUrl)  
    		         .cookies(cookies)  
    		         .data(formData)  
    		         .method(Connection.Method.POST)  
    		         .userAgent(USER_AGENT)
    		         .execute(); 

    		cookies = homePage.cookies();
    		
    		System.out.println("HTTP Status Code: " + homePage.statusCode());
    		
//    		Document red = Jsoup.connect(loginFormUrl)  
//   		         .cookies(cookies)  
//   		         .userAgent(USER_AGENT)  
//   		         .get(); 
    		
    		Document st = Jsoup.connect(loginFormUrl + "/Stock/RealTimeMonitoring")  
      		         .cookies(cookies)  
      		         .userAgent(USER_AGENT)  
      		         .get(); 
    		
    		System.out.println(st.html());
    		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static void test() {
        try {
			Document doc = Jsoup.connect("https://www.investagrams.com/Stock/SMPH")
								.userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
								.get();
			System.out.println(doc.title());
			
			// select stock information
			Elements tables = doc.body().select(".col-xs-4 > table > tbody > tr");
			
			for(Element table : tables) {
				
				Element label = table.select("td").first().child(0);
				Element value = table.select(".stock-price").first();
				System.out.println(label.ownText() + " " + value.ownText());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
