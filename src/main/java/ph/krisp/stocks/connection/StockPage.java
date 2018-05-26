package ph.krisp.stocks.connection;

import java.util.concurrent.Callable;

import ph.krisp.stocks.model.StockRaw;

/**
 * Downloads a single page of Stock and parses all required stock information
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockPage implements Callable<StockPage>{

	private String stockCode;
	private StockRaw stock;
	
	public StockPage(String stockCode) {
		this.stockCode = stockCode;
	}

	/**
	 * Downloads the stock page and parses the stock information
	 */
	@Override
	public StockPage call() throws Exception {
		this.stock = Investagrams.getStock(this.stockCode);
		return this;
	}

	public StockRaw getStock() {
		return stock;
	}

	public String getStockCode() {
		return stockCode;
	}

}
