package ph.krisp.stocks.model;

import java.math.BigDecimal;

/**
 * Stock Technical Analysis
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockTechnicalAnalysis {

	private BigDecimal support1;
	private BigDecimal support2;
	private BigDecimal resistance1;
	private BigDecimal resistance2;
	private BigDecimal yearToDatePercent;
	private BigDecimal monthToDatePercent;
	private String shortTermTrend;
	private String recommendation;
	
	// moving averages for 20, 50, 100, 200
	private StockMovingAverage[] movingAverages;
	// indicators
	private StockIndicator rsi14;
	private StockIndicator macd122609;
	private StockIndicator atr14;
	private StockIndicator cci20;
	private StockIndicator sts140303;
	private StockIndicator williamsR14;
	private StockIndicator volumeSma15;
	private StockIndicator candleStick1;
	
	public StockTechnicalAnalysis() {
		super();
	}

	public void setMovingAverages(StockMovingAverage[] movingAverages) {
		this.movingAverages = movingAverages;
	}

	public void setRsi14(StockIndicator rsi14) {
		this.rsi14 = rsi14;
	}

	public void setMacd122609(StockIndicator macd122609) {
		this.macd122609 = macd122609;
	}

	public void setAtr14(StockIndicator atr14) {
		this.atr14 = atr14;
	}

	public void setCci20(StockIndicator cci20) {
		this.cci20 = cci20;
	}

	public void setSts140303(StockIndicator sts140303) {
		this.sts140303 = sts140303;
	}

	public void setWilliamsR14(StockIndicator williamsR14) {
		this.williamsR14 = williamsR14;
	}

	public void setVolumeSma15(StockIndicator volumeSma15) {
		this.volumeSma15 = volumeSma15;
	}

	public void setCandleStick1(StockIndicator candleStick1) {
		this.candleStick1 = candleStick1;
	}

	public String getCandleStickvalue() {
		return this.candleStick1.getText1();
	}
	
	public String getCandleStickAction() {
		return this.candleStick1.getAction();
	}
	
	public BigDecimal getVolumeSmaValue() {
		return this.volumeSma15.getValue1();
	}
	
	public String getVolumeSmaAction() {
		return this.volumeSma15.getAction();
	}
	
	public BigDecimal getWilliamsRValue() {
		return this.williamsR14.getValue1();
	}
	
	public String getWilliamsRAction() {
		return this.williamsR14.getAction();
	}
	
	public BigDecimal getStsValue() {
		return this.sts140303.getValue1();
	}
	
	public String getStsAction() {
		return this.sts140303.getAction();
	}
	
	public BigDecimal getCciValue() {
		return this.cci20.getValue1();
	}
	
	public String getCciAction() {
		return this.cci20.getAction();
	}
	
	public BigDecimal getAtrValue1() {
		return this.atr14.getValue1();
	}
	
	public BigDecimal getAtrValue2() {
		return this.atr14.getValue2();
	}
	
	public String getAtrAction() {
		return this.atr14.getAction();
	}
	
	public BigDecimal getMACDValue1() {
		return this.macd122609.getValue1();
	}
	
	public BigDecimal getMACDValue2() {
		return this.macd122609.getValue2();
	}
	
	public String getMACDAction() {
		return this.macd122609.getAction();
	}
	
	public BigDecimal getRsiValue() {
		return this.rsi14.getValue1();
	}
	
	public String getRsiAction() {
		return this.rsi14.getAction();
	}
	
	public BigDecimal getSimpleMA20() {
		return this.movingAverages[0].getSimple();
	}
	
	public BigDecimal getSimpleMA50() {
		return this.movingAverages[1].getSimple();
	}
	
	public BigDecimal getSimpleMA100() {
		return this.movingAverages[2].getSimple();
	}
	
	public BigDecimal getSimpleMA200() {
		return this.movingAverages[3].getSimple();
	}

	public BigDecimal getExponentialMA20() {
		return this.movingAverages[0].getExponential();
	}
	
	public BigDecimal getExponentialMA50() {
		return this.movingAverages[1].getExponential();
	}
	
	public BigDecimal getExponentialMA100() {
		return this.movingAverages[2].getExponential();
	}
	
	public BigDecimal getExponentialMA200() {
		return this.movingAverages[3].getExponential();
	}
	
	public String getSimpleMaAction20() {
		return this.movingAverages[0].getSimpleAction();
	}
	
	public String getSimpleActionMaAction50() {
		return this.movingAverages[1].getSimpleAction();
	}
	
	public String getSimpleActionMaAction100() {
		return this.movingAverages[2].getSimpleAction();
	}
	
	public String getSimpleActionMaAction200() {
		return this.movingAverages[3].getSimpleAction();
	}

	public String getExponentialActionMaAction20() {
		return this.movingAverages[0].getExponentialAction();
	}
	
	public String getExponentialActionMaAction50() {
		return this.movingAverages[1].getExponentialAction();
	}
	
	public String getExponentialActionMaAction100() {
		return this.movingAverages[2].getExponentialAction();
	}
	
	public String getExponentialActionMaAction200() {
		return this.movingAverages[3].getExponentialAction();
	}
	
	public BigDecimal getSupport1() {
		return support1;
	}

	public void setSupport1(BigDecimal support1) {
		this.support1 = support1;
	}

	public BigDecimal getSupport2() {
		return support2;
	}

	public void setSupport2(BigDecimal support2) {
		this.support2 = support2;
	}

	public BigDecimal getResistance1() {
		return resistance1;
	}

	public void setResistance1(BigDecimal resistance1) {
		this.resistance1 = resistance1;
	}

	public BigDecimal getResistance2() {
		return resistance2;
	}

	public void setResistance2(BigDecimal resistance2) {
		this.resistance2 = resistance2;
	}

	public BigDecimal getYearToDatePercent() {
		return yearToDatePercent;
	}

	public void setYearToDatePercent(BigDecimal yearToDatePercent) {
		this.yearToDatePercent = yearToDatePercent;
	}

	public BigDecimal getMonthToDatePercent() {
		return monthToDatePercent;
	}

	public void setMonthToDatePercent(BigDecimal monthToDatePercent) {
		this.monthToDatePercent = monthToDatePercent;
	}

	public String getShortTermTrend() {
		return shortTermTrend;
	}

	public void setShortTermTrend(String shortTermTrend) {
		this.shortTermTrend = shortTermTrend;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
}
