package ph.krisp.stocks;

import java.util.Map;

import ph.krisp.stocks.connection.WebCon;
import ph.krisp.stocks.model.Stock;

/**
 * Main Class for the stock-api
 * @author kris.pagkaliwangan
 *
 */
public class App 
{

    public static void main( String[] args )
    {
    	// logins and retrieves all stock information
    	Map<String, String> cookies = WebCon.login();
    	Map<String, Stock> stockInfo = WebCon.getAllStockInfo(cookies);
        
    }
    
}
