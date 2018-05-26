package ph.krisp.stocks.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ph.krisp.stocks.model.Stock;

/**
 * Manages the stock page task downloading and parsing of information
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockPageManager {
	public static final int THREAD_COUNT = 5;
	private static final long PAUSE_TIME = 1000;
	
	private Set<String> stockSet;
	private List<Future<StockPage>> futures = new ArrayList<>();
	private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
	private Map<String, Stock> stockInfo;

	public StockPageManager(Set<String> stockSet) {
		super();
		this.stockSet = stockSet;
		this.stockInfo = new HashMap<>();
	}
	
	/**
	 * Places the stock set in the executor for submission of tasks
	 * 
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException {
		for(String stockCode : this.stockSet) {
			StockPage stockPage = new StockPage(stockCode);
			Future<StockPage> future = executorService.submit(stockPage);
			futures.add(future);
		}
		while(checkStockDownload());
		
	}
	
	/**
	 * Checks if there are more to download. Executes task if there are more future 
	 * tasks remaining
	 * 
	 * @return true if there are more to download
	 * @throws InterruptedException
	 */
	private boolean checkStockDownload() throws InterruptedException {
		Thread.sleep(PAUSE_TIME);
		Iterator<Future<StockPage>> iterator = futures.iterator();
		
		while (iterator.hasNext()) {
			Future<StockPage> future = iterator.next();
			if (future.isDone()) {
				iterator.remove();
				try {
					StockPage stockPage = future.get();
					stockInfo.put(stockPage.getStockCode(), stockPage.getStock());
//				} catch (InterruptedException e) {  // skip pages that load too slow
				} catch (ExecutionException e) {
				}
				
			}
		}
		return (futures.size() > 0);
	}

	public Map<String, Stock> getStockInfo() {
		return stockInfo;
	}
	
	
}
