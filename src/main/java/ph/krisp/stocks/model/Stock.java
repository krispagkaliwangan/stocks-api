package ph.krisp.stocks.model;

import java.util.Date;

/**
 * Class containing stock information and analysis
 * @author kris.pagkaliwangan
 *
 */
public class Stock {

	private String code;
	private Date date;
	private StockInfo info;
	private StockFundamentalAnalysis fa;
	private StockTechnicalAnalysis ta;
	
	public Stock(String code, Date date,StockInfo info,
			StockFundamentalAnalysis fa, StockTechnicalAnalysis ta) {
		super();
		this.code = code;
		this.date = date;
		this.info = info;
		this.fa = fa;
		this.ta = ta;
	}
	
}
