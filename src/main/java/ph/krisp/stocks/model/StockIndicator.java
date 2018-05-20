package ph.krisp.stocks.model;

import java.math.BigDecimal;

/**
 * Class for Stock indicators
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockIndicator {
	
	private BigDecimal value1;
	private BigDecimal value2;
	private String text1;
	private String action;
	public StockIndicator(BigDecimal value1, BigDecimal value2,
			String text1, String action) {
		super();
		this.value1 = value1;
		this.value2 = value2;
		this.text1 = text1;
		this.action = action;
	}
	public BigDecimal getValue1() {
		return value1;
	}
	public BigDecimal getValue2() {
		return value2;
	}
	public String getText1() {
		return text1;
	}
	public String getAction() {
		return action;
	}
	
}
