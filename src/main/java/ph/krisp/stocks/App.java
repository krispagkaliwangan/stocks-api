package ph.krisp.stocks;

import java.util.Map;

import ph.krisp.stocks.connection.Investagrams;
import ph.krisp.stocks.model.StockRaw;
import ph.krisp.stocks.utils.CsvUtils;

/**
 * Main Class for the stock-api
 * 
 * @author kris.pagkaliwangan
 *
 */
public class App 
{

    public static void main( String[] args )
    {
    	// logins and retrieves all stock information
    	Investagrams.login();
    	Map<String, StockRaw> stocks = Investagrams.getAllStockInfo();
    	
    	// writes updated stock information
		CsvUtils.updateAllStockFiles(stocks);
        
		System.exit(0);
    }
    
}
