package ph.krisp.stocks.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Class containing one record of stock information ready for analysis
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockRecord {

	private String code;
	private Date date;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal volume;
	private BigDecimal netForeign;
	
	private BigDecimal lastPrice;
	private BigDecimal change;
	private BigDecimal percentChange;
	
}
