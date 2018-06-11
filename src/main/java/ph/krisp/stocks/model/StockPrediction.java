package ph.krisp.stocks.model;

import java.util.Date;

/**
 * Class containing the prediction for a stock
 * 
 * @author kris.pagkaliwangan
 *
 */
public class StockPrediction {

	private String code;
	private Date date;
	private String action;
	private String nextDay;
	private boolean isMatch;
	
	/**
	 * Constructor for making a prediction
	 * 
	 * @param code
	 * @param date
	 * @param action
	 */
	public StockPrediction(String code, Date date, String action) {
		this.code = code;
		this.date = date;
		this.action = action;
	}

	/**
	 * Constructor for comparing a prediction to the actual next day
	 * 
	 * @param code
	 * @param date
	 * @param action
	 * @param nextDay
	 */
	public StockPrediction(String code, Date date, String action,
			String nextDay) {
		this.code = code;
		this.date = date;
		this.action = action;
		this.nextDay = nextDay;
		this.isMatch = action.equals(nextDay);
	}

	public String getCode() {
		return code;
	}

	public Date getDate() {
		return date;
	}

	public String getAction() {
		return action;
	}

	public String getNextDay() {
		return nextDay;
	}

	public boolean isMatch() {
		return isMatch;
	}
	
	
	
}
