package ph.krisp.stocks.model;

import java.math.BigDecimal;

/**
 * Class holding moving average information
 * @author kris.pagkaliwangan
 *
 */
public class StockMovingAverage {
	private int period;
	private BigDecimal simple;
	private String simpleAction;
	private BigDecimal exponential;
	private String exponentialAction;
	
	public StockMovingAverage(int period, BigDecimal simple, String simpleAction,
			BigDecimal exponential, String exponentialAction) {
		super();
		this.period = period;
		this.simple = simple;
		this.simpleAction = simpleAction;
		this.exponential = exponential;
		this.exponentialAction = exponentialAction;
	}
	public int getPeriod() {
		return period;
	}
	public BigDecimal getSimple() {
		return simple;
	}
	public String getSimpleAction() {
		return simpleAction;
	}
	public BigDecimal getExponential() {
		return exponential;
	}
	public String getExponentialAction() {
		return exponentialAction;
	}

	
}
