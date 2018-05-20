package ph.krisp.stocks.model;

import java.math.BigDecimal;

/**
 * Stock Fundamental Analysis
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockFundamentalAnalysis {

	private BigDecimal weekHigh52;
	private BigDecimal weekLow52;
	private BigDecimal fairValue;
	private BigDecimal earningsPerShare;
	private BigDecimal priceEarningsRatio;
	private BigDecimal dividendsPerShare;
	private BigDecimal priceToBookValue;
	private BigDecimal returnOnEquity;
	private String recommendation;
	
	public StockFundamentalAnalysis(BigDecimal weekHigh52,
			BigDecimal weekLow52, BigDecimal fairValue,
			BigDecimal earningsPerShare, BigDecimal priceEarningsRatio,
			BigDecimal dividendsPerShare, BigDecimal priceToBookValue,
			BigDecimal returnOnEquity, String recommendation) {
		this.weekHigh52 = weekHigh52;
		this.weekLow52 = weekLow52;
		this.fairValue = fairValue;
		this.earningsPerShare = earningsPerShare;
		this.priceEarningsRatio = priceEarningsRatio;
		this.dividendsPerShare = dividendsPerShare;
		this.priceToBookValue = priceToBookValue;
		this.returnOnEquity = returnOnEquity;
		this.recommendation = recommendation;
	}
	public BigDecimal getWeekHigh52() {
		return weekHigh52;
	}
	public BigDecimal getWeekLow52() {
		return weekLow52;
	}
	public BigDecimal getFairValue() {
		return fairValue;
	}
	public BigDecimal getEarningsPerShare() {
		return earningsPerShare;
	}
	public BigDecimal getPriceEarningsRatio() {
		return priceEarningsRatio;
	}
	public BigDecimal getDividendsPerShare() {
		return dividendsPerShare;
	}
	public BigDecimal getPriceToBookValue() {
		return priceToBookValue;
	}
	public BigDecimal getReturnOnEquity() {
		return returnOnEquity;
	}
	public String getRecommendation() {
		return recommendation;
	}
	
}
