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
	
	private BigDecimal close;
	private BigDecimal change;
	private BigDecimal percentChange;
	private BigDecimal prevClose;
	private BigDecimal open;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal avePrice;
	private BigDecimal volume;
	private BigDecimal value;
	private BigDecimal netForeign;
	
	
	public StockRecord(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getChange() {
		return change;
	}

	public void setChange(BigDecimal change) {
		this.change = change;
	}

	public BigDecimal getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(BigDecimal percentChange) {
		this.percentChange = percentChange;
	}

	public BigDecimal getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(BigDecimal prevClose) {
		this.prevClose = prevClose;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getAvePrice() {
		return avePrice;
	}

	public void setAvePrice(BigDecimal avePrice) {
		this.avePrice = avePrice;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getNetForeign() {
		return netForeign;
	}

	public void setNetForeign(BigDecimal netForeign) {
		this.netForeign = netForeign;
	}

	
	
}
