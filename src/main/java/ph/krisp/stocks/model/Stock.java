package ph.krisp.stocks.model;

import java.math.BigDecimal;

/**
 * Class representation of a stock with the basic information
 * 
 * @author kris.pagkaliwangan
 *
 */
public class Stock {

	private String code;
	private BigDecimal open;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal close;
	private BigDecimal previousClose;
	private BigDecimal volume;
	private BigDecimal value;
	
	public Stock(String code, BigDecimal open, BigDecimal low,
			BigDecimal high, BigDecimal close, BigDecimal previousClose,
			BigDecimal volume, BigDecimal value) {
		this.code = code;
		this.open = open;
		this.low = low;
		this.high = high;
		this.close = close;
		this.previousClose = previousClose;
		this.volume = volume;
		this.value = value;
	}
	
	public String getCode() {
		return code;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public BigDecimal getLow() {
		return low;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public BigDecimal getClose() {
		return close;
	}
	public BigDecimal getPreviousClose() {
		return previousClose;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public BigDecimal getValue() {
		return value;
	}
	public BigDecimal getChange() {
		return getClose().subtract(getPreviousClose());
	}
	public BigDecimal getPercentChange() {
		return getChange().divide(getPreviousClose());
	}
	
}
