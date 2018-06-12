package ph.krisp.stocks.prediction;

import java.util.List;

import ph.krisp.stocks.model.StockRecord;

/**
 * Interface class for predictions
 * 
 * @author kris.pagkaliwangan
 *
 */
public interface Prediction {

	/**
	 * Predicts an action based on the given list of StockRecords
	 * 
	 * @param records
	 * @return the predicted action
	 */
	String getAction(List<StockRecord> records);

}
